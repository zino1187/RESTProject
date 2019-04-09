package com.itbank.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Sample {
	public static void main(String[] args) throws IOException {
		String apiKey="TPK6sq5VdCOFrijK99CmJHQCEVer9GwK4sxLvP6ED6dBExrBc6FO298QjQadJsw7C4sDZ8yBXJfsYZ%2FVT6LG0A%3D%3D";
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1400000/service/cultureInfoService/frtrlSectnOpenAPI"); /* URL */
		
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "="+apiKey); /* Service Key */
		
		//urlBuilder.append("&" + URLEncoder.encode("mntiListNo", "UTF-8") + "="+ URLEncoder.encode("2310124200", "UTF-8")); /* 산정보ID */
		urlBuilder.append("&" + URLEncoder.encode("searchWrd", "UTF-8") + "="+ URLEncoder.encode("북한산", "UTF-8")); /* 산정보ID */
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
	}
}
