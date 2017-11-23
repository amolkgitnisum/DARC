package org.hac.drc.core;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingWorker extends Worker{

	private static final String CONNECTION = "Connection";
	private static final String ETAG = "ETag";
	private static final String Last_Modified = "Last-Modified";
	private static final String accept_encoding = "accept-encoding";
	private static final String vary = "vary";
	private static final String cache_control = "Cache-Control";
	private static final String status = "status";
	
	private List<String> urls;
	
	public ProcessingWorker(List<String> urls){
		this.urls=urls;
	}
	
	@Override
	public Object process() {
		
		List<String> connectionList = new ArrayList<>();
		List<String> eTagList = new ArrayList<>();
		List<String> lastModifiedList = new ArrayList<>();
		List<String> acceptEncodingList = new ArrayList<>();
		List<String> varyList = new ArrayList<>();
		List<String> cacheControlList = new ArrayList<>();
		List<String> statusList = new ArrayList<>();
		Map<String, List<String>> defaulterMap = new HashMap<>();
		
		urls.forEach(url -> {
			try{
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				// optional default is GET
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				//System.out.println("\nSending 'GET' request to URL : " + url);
				//System.out.println("Response Code : " + responseCode);
				Map<String, List<String>> headers =  con.getHeaderFields();
				if(!headers.containsKey(CONNECTION)){
					connectionList.add(url);
				}
				if(!headers.containsKey(ETAG)){
					eTagList.add(url);
				}
				if(!headers.containsKey(Last_Modified)){
					lastModifiedList.add(url);
				}
				if(!headers.containsKey(accept_encoding)){
					acceptEncodingList.add(url);
				}
				if(!headers.containsKey(vary)){
					varyList.add(url);
				}
				if(!headers.containsKey(cache_control)){
					cacheControlList.add(url);
				}else{
					List<String> valList = headers.get(cache_control);
					valList.forEach(str -> {
						if(str.contains("no-store") || str.contains("no-cache")){
							cacheControlList.add(url);
						}
					});
				}
				if(responseCode == 404){
					statusList.add(url);
				}
				
			}catch(Exception e){
				System.out.println("Exception Occured :"+e.getMessage());
			}
		});
		defaulterMap.put(CONNECTION, connectionList);
		defaulterMap.put(ETAG, eTagList);
		defaulterMap.put(Last_Modified, lastModifiedList);
		defaulterMap.put(vary, varyList);
		defaulterMap.put(cache_control, cacheControlList);
		defaulterMap.put(status, statusList);
		
		defaulterMap.forEach((k,v) -> {
			System.out.println(k +" :: "+ v);
		});
		return defaulterMap;
	}

}
