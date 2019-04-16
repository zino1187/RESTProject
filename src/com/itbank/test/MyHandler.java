package com.itbank.test;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	List<Member> memberList;
	Member member=null;
	private boolean id;
	private boolean name;
	private boolean sal;
	
	public void startDocument() throws SAXException {
		System.out.println("������ ���۵Ǿ����ϴ�");
	}

	public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
		System.out.print("<"+tag+">");
		
		
		if(tag.equals("members")) {
			memberList = new ArrayList();
		}else if(tag.equals("member")) {
			member = new Member();
		}else if(tag.equals("id")) {
			id=true;
		}else if(tag.equals("name")) {
			name=true;
		}else if(tag.equals("sal")) {
			sal=true;
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String content=new String(ch, start, length);
		
		System.out.print(new String(ch, start, length));
		//���� �� ��Ʈ���� ��� �±��� ��Ʈ������ �� �� ����
		if(id) {
			member.setId(content);
		}else if(name) {
			member.setName(content);
		}else if(sal) {
			member.setSal(Integer.parseInt(content));
		}
	}

	public void endElement(String uri, String localName, String tag) throws SAXException {
		System.out.println("</"+tag+">");
		
		if(tag.equals("id")) {
			id=false;
		}else if(tag.equals("name")) {
			name=false;
		}else if(tag.equals("sal")) {
			sal=false;
		}else if(tag.equals("member")) {
			memberList.add(member);
		}
	}

	public void endDocument() throws SAXException {
		System.out.println("�������� ��  List ������� "+memberList.size());
		for(int i=0;i<memberList.size();i++) {
			Member obj=memberList.get(i);
			System.out.println("ID : "+obj.getId()+", Name : "+obj.getName()+", Sal : "+obj.getSal());
		}
	}
}
