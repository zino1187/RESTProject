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
		//System.out.println("���� �� �����̳� ����"+member.getName());
		//System.out.println("�ٸ� �� �����̳� ����"+dog.getName());
		return null;
	}
	
	//������ �α��� ��û 
	@RequestMapping(value="/admin/login", method=RequestMethod.POST)
	public String login(Admin admin, HttpServletRequest request) {
		Admin obj=adminService.loginCheck(admin);
		
		System.out.println("obj is "+obj);
		
		//���ǿ� ���!!!
		request.getSession().setAttribute("admin", obj);
		
		return "redirect:/admin/main";
	}
	
	//���������� ��û
	@RequestMapping(value="/admin/main", method=RequestMethod.GET)
	public String main(HttpServletRequest request, String msg) {
		
		return "admin/index";
	}
	
	//ȸ������ ������ ��û
	@RequestMapping(value="/admin/member", method=RequestMethod.GET)
	public String member() {
		//�α��� Ȯ��
		return "admin/member/index";
	}
	
	//ȸ����� ��û (���� REST API ���°� �ƴ�!!)
	@RequestMapping(value="/admin/member/regist", method=RequestMethod.POST)
	@ResponseBody //���������� Ŭ���̾�Ʈ�� �ƴϴ�...(����Ʈ��..���� �ܸ���..)
	public String registMember(Member member) {
		System.out.println(member.getId());
		System.out.println(member.getPass());
		System.out.println(member.getName());
		
		memberService.insert(member);
		
		//json �����ϱ�!!
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
	
	//ȸ�� �� ����
	@RequestMapping(value="/admin/member/detail", method=RequestMethod.GET)
	@ResponseBody
	public String getDetail(int member_id) {
		
		System.out.println(" �Ѱܹ���  member_id "+member_id);
		
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
		System.out.println("�Ѱܹ��� member_id "+member_id);
		memberService.delete(member_id);
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"result\":1");
		sb.append("}");
		return sb.toString();
	}
	
	//������ ��û
	@RequestMapping(value="/admin/map", method=RequestMethod.GET)	
	public String map() {
		//�α��� Ȯ��
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
		System.out.println("��Ͻ���"+sb);
		return sb.toString();
	}
	
	@ExceptionHandler(EditFailException.class)
	@ResponseBody
	public String editException(EditFailException e) {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("result:0");
		sb.append("}");		
		System.out.println("���� ��û����"+sb);
		return sb.toString();
	}
	
	@ExceptionHandler(DeleteFailException.class)
	@ResponseBody
	public String deleteException(DeleteFailException e) {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("result:0");
		sb.append("}");		
		System.out.println("��������"+sb);
		return sb.toString();
	}
	
}












