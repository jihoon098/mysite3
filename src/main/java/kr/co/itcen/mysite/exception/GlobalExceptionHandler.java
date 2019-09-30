package kr.co.itcen.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.itcen.mystie.dto.JSONResult;

//컨트롤러가 아님.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	//@ExceptionHandler(UserDaoException.class)
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
	throws Exception{
		
		//1. 로깅 //파일에 로그를 남길수있음.일단은 화면에 출력.
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
		
		//2.요청 구분
		// 만약 JSON요청인 경우는 application/json
		// 만약 HTML요청인 경우는 text/html
		// 만약 img(jpeg)요청인 경우 image/jpeg
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {
			//3. json응답
			response.setStatus(HttpServletResponse.SC_OK);
			
			//아래 작업들을 메세지 컴버터가 하고있다...라고 함..ㅠㅠ
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("utf-8"));
			os.close();
			
		}else {
			//3. 안내 페이지
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			//Logger.error(erros.toString());
			
			request
			.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
			.forward(request, response);			
		}
		
		
	}
}
