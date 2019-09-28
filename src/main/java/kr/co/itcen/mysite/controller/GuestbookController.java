package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value="/deleteform/{no}", method=RequestMethod.GET)
	public String list(@PathVariable Long no, Model model) {
		model.addAttribute("no", no);
		return "/guestbook/deleteform";
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {

		guestbookService.delete(vo);
		return "redirect:/guestbook";
	}
	
}
