package com.itbank.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class MyParser {
	SAXParserFactory parserFactory;
	SAXParser saxParser;
	String path="E:/workspace/spring_test/RestProject/src/com/itbank/test/member.xml";
	
	public MyParser() {
		parserFactory = SAXParserFactory.newInstance();
		try {
			saxParser = parserFactory.newSAXParser();
			saxParser.parse(new File(path), new MyHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MyParser();
	}
}
