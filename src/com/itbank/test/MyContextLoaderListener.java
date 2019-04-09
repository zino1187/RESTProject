package com.itbank.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*톰켓이 가동될때의 이벤트를 감지하여, 원하는 작업을 할 수 있는 클래스 정의*/
public class MyContextLoaderListener implements ServletContextListener{
	//톰켓이 가동될때 호출되는 메서드!!
	public void contextInitialized(ServletContextEvent e) {
		System.out.println("고양이 가동!!!");
		String msg=e.getServletContext().getInitParameter("contextConfigLocation");
		System.out.println(msg);
	}
	
	//톰켓이 종료될때 호출되는 메서드
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("고양이 종료!!!");
	}
	
}
