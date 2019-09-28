package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="")
	public String list(
			@RequestParam(value="kwd", required = false, defaultValue = "") String kwd, 
			@RequestParam(value="page", required = false, defaultValue = "1") int page,
			Model model) {
			
		List<BoardVo> list = boardService.get(kwd);
		model.addAttribute("list",list);
		model.addAttribute("page", page);
		return "/board/list";
	}
	
	@RequestMapping(value="/writeform", method=RequestMethod.GET)
	public String write(@RequestParam(value="gNo", required= false, defaultValue = "0") Long gNo,
			@RequestParam(value="oNo", required= false, defaultValue = "1") Long oNo,
			@RequestParam(value="depth", required= false, defaultValue = "0") Long depth, Model model) {
		
		BoardVo vo = new BoardVo();
		vo.setgNo(gNo);
		vo.setoNo(oNo);
		vo.setDepth(depth);
		model.addAttribute("vo", vo); //파라미터1개를 쓰면 안넘어가네..? 꼭이름을 지정해줘야 view쪽에서 사용가능한건가..??
		return "/board/write";
	}
	
	@RequestMapping(value="/writeform", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, Model model) {
		
		model.addAttribute("vo", vo);
		return "/board/write";
	}
	
	
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String list(@ModelAttribute BoardVo vo ,HttpSession session) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long userNo = authUser.getNo();
		vo.setUserNo(userNo);
		
		System.out.println(vo);
		
		boardService.write(vo);
		return "redirect:/board";
	}
	
	

}
