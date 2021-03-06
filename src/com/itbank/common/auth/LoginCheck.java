package com.itbank.common.auth;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.itbank.model.domain.Admin;

//매번 로그인이 필요한코드마다 로직을 작성하지 말고, 
//공통코드로 분리시켜 AOP 를 적용해본다!!
@Aspect
public class LoginCheck {	
	
	@Pointcut("execution(public * com.itbank.controller.AdminController..main*(..))")
	public void loginCut() {}
	
	@Around("loginCut()")
	public String checkSession(ProceedingJoinPoint joinPoint) {
		String viewName=null;
		HttpServletRequest request=null;
		
		//세션 있는지 여부 체크!!
		Object[] args=joinPoint.getArgs();
		for(int i=0;i<args.length;i++) {
			System.out.println(args[i]);
			//오브젝트의 자료형이 HttpServletRequest 형 인지 알아보기 
			if(args[i] instanceof HttpServletRequest ) {
				request=(HttpServletRequest)args[i];//요청 객체만 골라서 담기!!
			}	
		}
		
		//세션에 담겨있는 무언가가 있는지 체크!!
		if(request.getSession().getAttribute("admin")==null) {
			System.out.println("로그인 안했짠아요!!인증받지 못한자!!");
			viewName="redirect:/admin/error/accessdeny.jsp";
		}else {
			Admin admin=(Admin)request.getSession().getAttribute("admin");
			System.out.println(admin.getId());
			viewName="admin/index";
		}
		return viewName;
	}
	
}




