package com.itbank.common.auth;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.itbank.model.domain.Admin;

//�Ź� �α����� �ʿ����ڵ帶�� ������ �ۼ����� ����, 
//�����ڵ�� �и����� AOP �� �����غ���!!
@Aspect
public class LoginCheck {	
	
	@Pointcut("execution(public * com.itbank.controller.AdminController..main*(..))")
	public void loginCut() {}
	
	@Around("loginCut()")
	public String checkSession(ProceedingJoinPoint joinPoint) {
		String viewName=null;
		HttpServletRequest request=null;
		
		//���� �ִ��� ���� üũ!!
		Object[] args=joinPoint.getArgs();
		for(int i=0;i<args.length;i++) {
			System.out.println(args[i]);
			//������Ʈ�� �ڷ����� HttpServletRequest �� ���� �˾ƺ��� 
			if(args[i] instanceof HttpServletRequest ) {
				request=(HttpServletRequest)args[i];//��û ��ü�� ��� ���!!
			}	
		}
		
		//���ǿ� ����ִ� ���𰡰� �ִ��� üũ!!
		if(request.getSession().getAttribute("admin")==null) {
			System.out.println("�α��� ����§�ƿ�!!�������� ������!!");
			viewName="redirect:/admin/error/accessdeny.jsp";
		}else {
			Admin admin=(Admin)request.getSession().getAttribute("admin");
			System.out.println(admin.getId());
			viewName="admin/index";
		}
		return viewName;
	}
	
}




