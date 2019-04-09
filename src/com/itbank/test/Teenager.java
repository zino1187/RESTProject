package com.itbank.test;
import org.springframework.stereotype.Component;

public class Teenager implements Student{
	public void getUp() {
		System.out.println("기상합니다");
	}
	public void goSchool() {
		System.out.println("등교합니다");
	}
	public void study() {
		System.out.println("공부합니다");
	}
}





