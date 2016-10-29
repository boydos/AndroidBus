package com.example.listtest;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class CustomHttpRequest {
	private static final String CHARSET_GB2312="GB2312";
	private static CookieStore cookieStore;
	public static String sendPost(String url,NameValuePair ...nameValuePairs) {
		List<NameValuePair> params =new ArrayList<NameValuePair>();
		if(nameValuePairs!=null) {
			for(NameValuePair value:nameValuePairs) {
				params.add(value);
			}
		}
		try {
			UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(params, CHARSET_GB2312);
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpClient client = HttpClients.createDefault();

			HttpResponse response = client.execute(httpPost);
			String result= response2String(response);
			close(client);
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static String sendGet(String url,NameValuePair ...nameValuePairs) {
		StringBuilder sb =new StringBuilder();
		sb.append(url);
		if(nameValuePairs!=null&&nameValuePairs.length>0) {
			sb.append("?");
			int i=0;
			for(NameValuePair pair:nameValuePairs) {
				if(i>0)sb.append("&");
				i++;
				sb.append(String.format("%s=%s", pair.getName(),pair.getValue()));
			}
		}
		HttpGet httpGet = new HttpGet(sb.toString());
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpGet);
			String result= response2String(response);
			close(client);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String response2String(HttpResponse response){
		if(response==null||response.getStatusLine().getStatusCode() !=HttpStatus.SC_OK) {
			return null;
		}
		HttpEntity resEntity = response.getEntity();
		try {
			return resEntity==null?null:EntityUtils.toString(resEntity,HTTP.UTF_8);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void close(Closeable close) {
		if(close!=null) {
			try {
				close.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	 

}
