package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.get();
		model.addAttribute("list", list);
		return "/guestbook/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String list(@ModelAttribute GuestbookVo vo, Model model) {
		guestbookService.insert(vo);
		
		List<GuestbookVo> list = guestbookService.get();
		model.addAttribute("list", list);
		
		return "/guestbook/list";
	}

	
}
