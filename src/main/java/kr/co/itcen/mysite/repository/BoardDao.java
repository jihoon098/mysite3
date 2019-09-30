package kr.co.itcen.mysite.repository;


import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	/* board list가져오기 */
	public List<BoardVo> getList(String kwd) {
		kwd = "%" +kwd +"%";
		List<BoardVo> result = sqlSession.selectList("board.getList", kwd);
		return result;
	}
	/* board list가져오기 */
	
	
	/* 게시글 insert */
	public int updateOnoDepth(BoardVo vo) {
		int count = sqlSession.update("board.updateOnoDepth", vo);
		return count;
	}
	public int insertBoard(BoardVo vo) {
		int count = sqlSession.insert("board.insertBoard", vo);
		return count;
	}
	/* 게시글 insert */

	
	/* 게시글 보기 */
	public int updateHitForView(Long no) {
		int count = sqlSession.update("board.updateHitForView", no);
		return count;
	}
	public BoardVo viewBoard(Long no) {
		BoardVo vo = sqlSession.selectOne("board.viewBoard", no);
		return vo;
	}
	/* 게시글 보기 */
	

	public Boolean deleteBoard(Long no){
		//게시물 삭제여부를 알려주는 status를 1(삭제)로 변경
		int count = sqlSession.update("board.deleteBoard", no);
		return count == 1;
	}
	
	public Boolean modifyContents(BoardVo vo) {
		int count = sqlSession.update("board.modifyContents", vo);
		return count == 1;
	}

}
