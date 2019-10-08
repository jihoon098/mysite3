package kr.co.itcen.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.mysite.security.Auth.Role;
import kr.co.itcen.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. handler 종류(DefaultServletHttpRequestHandeler,  HandlerMethod)를 분류해야함
		//DefaultServletHttpRequestHandeler로 오는것들은 이미지등 정적파일들이니 그냥  띄워주도록 끝내면 됨.
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. @Auth받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. @Auth가 없으면 class type에 있을 수 있으므로...
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		//5. @Auth가 없으면
		if(auth == null) {
			return true;
		}
		
		//6. @Auth가 class나 method에 붙어있기 때문에 인증 여부를 체크한다.
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("authUser") == null) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}
		
		
		//7. Method의 @Auth의 Role 가져오기
		String role = auth.role().toString(); 
				
		//8. 메소드의 @Auth의 Role이 "USER"인 경우.
				//   인증만 되어 있으면 모두 통과
		if("USER".equals(role)) { // == if(Role.USER.toString().equals(role)) {}
			return true;
		}
		
		//9. 메소드의 @Auth의 Role이 "ADMIN"
		if("ADMIN".equals(role)) {
			UserVo vo = (UserVo)session.getAttribute("authUser");
			if("USER".equals(vo.getRole())) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
