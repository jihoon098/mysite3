package kr.co.itcen.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mystie.dto.JSONResult;

@Controller("userApiController")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@ResponseBody
//	@RequestMapping("/checkemail")
//	public Map checkEmail(@RequestParam(value="email", required=true, defaultValue = "") String email) {
//		Boolean exist = userService.existUser(email);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("result", "success");
//		map.put("data", exist);
//		//http://localhost:8088/mysite3/api/user/checkemail?email=jihoon@jihoon.com 로 확인
//		return map;
//	}
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(@RequestParam(value="email", required=true, defaultValue = "") String email) {
		Boolean exist = userService.existUser(email);
		
		//JSONResult result = new JSONResult();
		//result.setResult("ok");
		//result.setResult(exist ? "있음 " : "없음" );		
		//return result;
		
		//setter를 다 지움
		
		return JSONResult.success(exist);
		
		
	}
	
}
