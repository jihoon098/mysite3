package kr.co.itcen.mysite.controller;


import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.security.Auth.Role;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			
			model.addAllAttributes(result.getModel()); //Map값이 반환됨. key값이 아래 있는 ""값으로 자동으로 넣어줌 for문처럼 
			
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	//@Auth(role=Auth.Role.ADMIN)
//	//@Auth("ADMIN")
//	@Auth("USER")
//	@RequestMapping(value="/update", method=RequestMethod.GET)
//	public String update(
//		@ModelAttribute UserVo vo, HttpSession session ) {
//		
////		if(session.getAttribute("authUser") == null) {
////			return "redirect:/";
////		}
//		
//		return "user/update";
//	}

	@Auth(role = Role.USER)
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		
		System.out.println(authUser);
		
		//session쓸때 코드
		//UserVo authUser = (UserVo)session.getAttribute("authUser");
		//Long no = authUser.getNo();
		
		//오류 있는 이유는..?
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("user", userVo);
		
		//authUser = userService.getUser(authUser.getNo());
		
		return "user/update";
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
		@ModelAttribute @Valid UserVo vo,
		BindingResult result) {
		return "user/update";
	}
	
	
//인터셉터로 어플리케이션 바깥으로 뺌. 
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(UserVo vo, HttpSession session, Model model) {
//		UserVo userVo = userService.getUser(vo);
//		if(userVo == null) {
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		// 로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}

//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		//접근 제어(ACL)
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser != null) {
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//
//		return "redirect:/";
//	}

//  ------------------------------------------------------------------------------------
	
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		return "error/exception";
//	}
	
}
