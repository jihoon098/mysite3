package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//어노테이션 인터페이스
@Target({ElementType.TYPE, ElementType.METHOD}) //이 어노테이션 어디에다가 붙일것이야? 타켓이 어디냐?
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
	public enum Role{USER, ADMIN};
	
	public Role role() default Role.USER;
	
	//public String value() default "USER";
	
	public int test() default 1;
}



//이걸 Auth인터페이스 내부에 만든거.
//public enum Role {
//	USER,
//	ADMIN
//}
