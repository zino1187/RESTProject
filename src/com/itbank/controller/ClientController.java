package com.itbank.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itbank.test.Member;

@Controller
public class ClientController {
	
	@RequestMapping(value="/rest/members", method=RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List selectAll() {
		
		Member member = new Member();
		member.setId("scott");
		member.setName("��ı");
		member.setSal(300);
		List list= new ArrayList();
		list.add(member);
		return list;
	}
	
	/*
	 * @RequestMapping(value="/rest/members/{id}", method=RequestMethod.GET)
	 * 
	 * @ResponseBody public String select(@PathVariable("id") String id) {
	 * System.out.println("����� ���ϴ±���"); JSONObject json = new JSONObject();
	 * json.put("id", "scott"); json.put("name", "��ı"); json.put("sal", "4000");
	 * 
	 * return json.toString(); }
	 * 
	 */	
}
