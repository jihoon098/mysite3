package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(String kwd) {
		kwd = "%" +kwd +"%";
		
		System.out.println(kwd);
		
		List<BoardVo> result = sqlSession.selectList("board.getList", kwd);
		return result;
	}
	

	
	public void insertBoard(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			connection = getConnection();
			long oNoVal = vo.getoNo()+1;
			long depVal = vo.getDepth()+1;
			
			sql = "update board set o_no=o_no+1 where g_no = ? and o_no >= ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, oNoVal);
			pstmt.executeUpdate();
			pstmt.close();
			
			if(vo.getgNo() == 0) {
				sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull (max(g_no+1), 1) from board as a), 1, 0, ?, 0)";
			}else {
				sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, 0)";
			}
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			if(vo.getgNo() != 0) {
				pstmt.setLong(3, vo.getgNo());
				pstmt.setLong(4, oNoVal);
				pstmt.setLong(5, depVal);				
				pstmt.setLong(6, vo.getUserNo());
			}else {
				pstmt.setLong(3, vo.getUserNo());
				
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	public BoardVo getBoard(long no) {
		BoardVo vo = null;
		String sql = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			sql = "update board set hit=hit+1 where no = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = 
				"   select no, title, contents, user_no, g_no, o_no, depth" +
				"     from board" +
				" where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no1 = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long user_no = rs.getLong(4);
				Long g_no = rs.getLong(5);
				Long o_no = rs.getLong(6);
				Long depth = rs.getLong(7);
				
				vo = new BoardVo();
				vo.setNo(no1);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(user_no);
				vo.setgNo(g_no);
				vo.setoNo(o_no);
				vo.setDepth(depth);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}

	public Boolean deleteBoard(Long no){
		//게시물 삭제여부를 알려주는 status를 1(삭제)로 변경
		int count = sqlSession.update("board.deleteBoard", no);
		return count == 1;
	}
	
	
	public Boolean modifyContents(BoardVo vo) {
		int count = sqlSession.update("board.modifyContents", vo);
		return count == 1;
	}
	
	
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.1.124:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "000000");
		
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}
		
		return connection;
	}


	

}
