package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> get(String kwd) {
		return boardDao.getList(kwd);
	}
	
	public void write(BoardVo vo) {
		boardDao.insertBoard(vo);
	}

	public BoardVo get(Long no) {
		return boardDao.getBoard(no);
	}
	
	public Boolean delete(Long no) {
		return boardDao.deleteBoard(no);
	}
	
	public Boolean modify(BoardVo vo) {
		return boardDao.modifyContents(vo);
	}
	
}
