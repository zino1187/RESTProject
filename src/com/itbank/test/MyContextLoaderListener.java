package com.itbank.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*������ �����ɶ��� �̺�Ʈ�� �����Ͽ�, ���ϴ� �۾��� �� �� �ִ� Ŭ���� ����*/
public class MyContextLoaderListener implements ServletContextListener{
	//������ �����ɶ� ȣ��Ǵ� �޼���!!
	public void contextInitialized(ServletContextEvent e) {
		System.out.println("����� ����!!!");
		String msg=e.getServletContext().getInitParameter("contextConfigLocation");
		System.out.println(msg);
	}
	
	//������ ����ɶ� ȣ��Ǵ� �޼���
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("����� ����!!!");
	}
	
}
