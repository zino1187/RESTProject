package com.itbank.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.common.exception.AccountNotFoundException;
import com.itbank.common.exception.DeleteFailException;
import com.itbank.common.exception.EditFailException;
import com.itbank.common.exception.RegistFailException;
import com.itbank.model.domain.Admin;
import com.itbank.model.domain.Member;
import com.itbank.model.service.AdminService;
import com.itbank.model.service.MemberService;
import com.itbank.test.Dog;

@Controller
public class AdminController{
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Dog dog;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/admin/test", method=RequestMethod.GET)
	public String test() {
		//System.out.println("나의 웹 컨테이너 영역"+member.getName());
		//System.out.println("다른 웹 컨테이너 영역"+dog.getName());
		return null;
	}
	
	//관리자 로그인 요청 
	@RequestMapping(value="/admin/login", method=RequestMethod.POST)
	public String login(Admin admin, HttpServletRequest request) {
		Admin obj=adminService.loginCheck(admin);
		
		System.out.println("obj is "+obj);
		
		//세션에 담기!!!
		request.getSession().setAttribute("admin", obj);
		
		return "redirect:/admin/main";
	}
	
	//메인페이지 요청
	@RequestMapping(value="/admin/main", method=RequestMethod.GET)
	public String main(HttpServletRequest request, String msg) {
		
		return "admin/index";
	}
	
	//회원정보 페이지 요청
	@RequestMapping(value="/admin/member", method=RequestMethod.GET)
	public String member() {
		//로그인 확인
		return "admin/member/index";
	}
	
	//회원등록 요청 (아직 REST API 쓰는거 아님!!)
	@RequestMapping(value="/admin/member/regist", method=RequestMethod.POST)
	@ResponseBody //브라우저만이 클라이언트는 아니다...(스마트폰..각종 단말기..)
	public String registMember(Member member) {
		System.out.println(member.getId());
		System.out.println(member.getPass());
		System.out.println(member.getName());
		
		memberService.insert(member);
		
		//json 구성하기!!
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"result\":1");
		sb.append("}");
		return sb.toString();
	}  
	
	@RequestMapping(value="/admin/member/list", method=RequestMethod.GET)
	@ResponseBody
	public String getList() {
		List<Member> memberList=memberService.selectAll();
		
		StringBuilder sb=new StringBuilder();
		
		JSONArray array = new JSONArray();
		for(int i=0; i<memberList.size();i++) {
			Member member=memberList.get(i);
			
			String name = null;
			try {
				name=URLDecoder.decode(member.getName(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			JSONObject obj = new JSONObject();
			obj.put("member_id", member.getMember_id());
			obj.put("id", member.getId());
			obj.put("pass", member.getPass());
			obj.put("name", name);
			System.out.println(member.getName());
			array.add(obj);
		}

		return array.toString();
	}
	
	//회원 상세 정보
	@RequestMapping(value="/admin/member/detail", method=RequestMethod.GET)
	@ResponseBody
	public String getDetail(int member_id) {
		
		System.out.println(" 넘겨받은  member_id "+member_id);
		
		Member member=memberService.select(member_id);
		
		JSONObject obj = new JSONObject();
		obj.put("member_id", member.getMember_id());
		obj.put("id", member.getId());
		obj.put("pass", member.getPass());
		obj.put("name", member.getName());
		
		return obj.toString();
	} 
	
	@RequestMapping(value="/admin/member/edit", method=RequestMethod.POST)
	@ResponseBody
	public String edit(Member member) {
		
		memberService.update(member);
		
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"result\":1");
		sb.append("}");
		return sb.toString();
	}
	
	@RequestMapping(value="/admin/member/del", method=RequestMethod.POST)
	@ResponseBody
	public String del(@RequestParam("member_id") int member_id) {
		System.out.println("넘겨받은 member_id "+member_id);
		memberService.delete(member_id);
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"result\":1");
		sb.append("}");
		return sb.toString();
	}
	
	//맵정보 요청
	@RequestMapping(value="/admin/map", method=RequestMethod.GET)	
	public String map() {
		//로그인 확인
		return "admin/map/index";
	}
	
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ModelAndView handleException(AccountNotFoundException e) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/error/errorpage");
		mav.addObject("err", e);
		return mav;
	}
	
	@ExceptionHandler(RegistFailException.class)
	@ResponseBody
	public String registException(RegistFailException e, HttpServletResponse response) {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("result:0");
		sb.append("}");		
		System.out.println("등록실패"+sb);
		return sb.toString();
	}
	
	@ExceptionHandler(EditFailException.class)
	@ResponseBody
	public String editException(EditFailException e) {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("result:0");
		sb.append("}");		
		System.out.println("수정 요청실패"+sb);
		return sb.toString();
	}
	
	@ExceptionHandler(DeleteFailException.class)
	@ResponseBody
	public String deleteException(DeleteFailException e) {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("result:0");
		sb.append("}");		
		System.out.println("삭제실패"+sb);
		return sb.toString();
	}
	
}












