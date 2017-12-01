package com.flaginfo.lightning.common;

import java.net.URL;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.springframework.core.env.Environment;

import com.flaginfo.lightning.ApplicationContextProvider;

/**
 * ID生成器<br/>
 * 默认调用url：http://id.flaginfo.net:8080/IDGen/next-id
 * @author Rain
 *
 */
public class IDGen {
//	private static String url = "http://localhost:9230/next-id";
	private static String url;
	private static URL remoteUrl = null;
	
	static{
		try {
		    Environment env = ApplicationContextProvider.getBean("environment", Environment.class);
            
            url = env.getProperty("id_url");
		    
			remoteUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void setRemoteUrl(URL remoteUrl) {
		IDGen.remoteUrl = remoteUrl;
	}
	
	
	/**
	 * 组装参数
	 * @param params
	 * @return
	 */
	private static String fillParams(Map<String,String> params){
		if(params == null || params.size() == 0){
			return "";
		}
		Set<String> set = params.keySet();
		StringBuffer sb = new StringBuffer();
		boolean isFirst = true;
		for(String key:set){
			if(!isFirst){
				sb.append("&");
			}else{
				isFirst = false;
			}
			sb.append(key).append("=").append(params.get(key));
		}
		return sb.toString();
	}
	
	private static String requestGet(Map<String,String> params){
	    InputStream inputStream = null;
	    InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
	    try {
	    	URLConnection httpURLConnection = (HttpURLConnection)remoteUrl.openConnection();
	    	httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    httpURLConnection.setConnectTimeout(20000); 
		    httpURLConnection.setReadTimeout(20000);
		    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
		    outputStreamWriter.write(fillParams(params));
		    outputStreamWriter.flush();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            outputStreamWriter.close();
        }catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(url+"，获取资源失败",e);
        }finally {
            try {
				if (reader != null) {
				    reader.close();
				}
				
				if (inputStreamReader != null) {
				    inputStreamReader.close();
				}
				
				if (inputStream != null) {
				    inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	    return resultBuffer.toString();
	}
	
	/**
	 * 获取单个id
	 * @return
	 */
	public static String nextId(){
		String nextId = getNewSource(1)[0];
		return nextId;
	}
	
	/**
	 * 批量获取id
	 * @param batchNum
	 * @return
	 */
	public static String[] nextIds(int batchNum){
		return getNewSource(batchNum);
	}
	
	private static String[] getNewSource(int batchNum){
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("n", String.valueOf(batchNum));
		String ids = requestGet(paramsMap);
		return ids.split(",");
	}
	
	public static void main1(String[] args) {
		System.out.println(IDGen.nextId());
	}
	
}
