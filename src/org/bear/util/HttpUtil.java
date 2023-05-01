package org.bear.util;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * HTTP POST Utility
 * @author edward
 *
 */
public class HttpUtil {
	/**
	 * send http request (by post)
	 * @param url url to send reuqest
	 * @param paramMap http parameters
	 * @param retryCount time to retry when server drop or fail (min 0, max 5)
	 * @param encoding TODO
	 * @return response content
	 * @throws IOException connection problem/aborted
	 */
	private String proxyHost = null;
	private Integer proxyPort = null;
	public static String send(String url, Map<String, String> paramMap, int retryCount, String encoding) throws IOException
	{
		DefaultHttpClient httpClinet = new DefaultHttpClient();
		retryCount=retryCount<0?0:retryCount;
		retryCount=retryCount>10?10:retryCount;
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, true);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(url);
		httpClinet.setHttpRequestRetryHandler(retryHandler);
		List<NameValuePair> paramList=new ArrayList<NameValuePair>();
		
		for (Entry<String, String> entry:paramMap.entrySet())
		{
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		UrlEncodedFormEntity entity = null;
		try 
		{
			entity = new UrlEncodedFormEntity(paramList, encoding);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		post.setEntity(entity);
		
		String responseString = null;
		
		try 
		{
			responseString = httpClinet.execute(post, responseHandler);
		} 
		catch (ClientProtocolException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
		return responseString;
	}
	
	public static String send(String url, List<NameValuePair> paramList, int retryCount, String encoding)
	{
		DefaultHttpClient httpClinet = new DefaultHttpClient();
		retryCount=retryCount<0?0:retryCount;
		retryCount=retryCount>10?10:retryCount;
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, true);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(url);
		httpClinet.setHttpRequestRetryHandler(retryHandler);
		UrlEncodedFormEntity entity = null;
		try 
		{
			entity = new UrlEncodedFormEntity(paramList, encoding);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		post.setEntity(entity);
		
		String responseString = null;
		
		try 
		{
			responseString = httpClinet.execute(post, responseHandler);
			responseString = new String(responseString.getBytes("ISO-8859-1"), encoding);
		} 
		catch (ClientProtocolException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Request Timeout in HttpUtil.java");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			e.printStackTrace();
		}
		
		return responseString;
	}
	/**
	 * 若擷取資料失敗，可以重新發送Request
	 * @param url
	 * @param paramList
	 * @param retryCount
	 * @param encoding
	 * @return
	 */
	public static String sendWithRetry(String url, List<NameValuePair> paramList, int retryCount, String encoding)
	{
		DefaultHttpClient httpClinet = new DefaultHttpClient();
		retryCount=retryCount<0?0:retryCount;
		retryCount=retryCount>10?10:retryCount;
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, true);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(url);
		httpClinet.setHttpRequestRetryHandler(retryHandler);
		UrlEncodedFormEntity entity = null;
		try 
		{
			entity = new UrlEncodedFormEntity(paramList, encoding);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		post.setEntity(entity);
		
		String responseString = null;
		boolean isConnect = false;
		while (isConnect == false)
		{
			try 
			{
				responseString = httpClinet.execute(post, responseHandler);
				//responseString = new String(responseString.getBytes("ISO-8859-1"), encoding);
				isConnect = true;
			} 
			
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Connection Timed out!");
				//e.printStackTrace();
				sleep(3000);
			}
		}
		
		return responseString;
	}
	
	
	
	/**
	 * post directly with data (use NO request parameter!)
	 * @param url
	 * @param data
	 * @param charset
	 * @return
	 */
	public static String send(String url, String data, String charset)
	{
		String tempData = data;
		DefaultHttpClient httpClinet = new DefaultHttpClient();
		int retryCount = 3;
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, true);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(url);
		httpClinet.setHttpRequestRetryHandler(retryHandler);

		try 
		{
			post.setEntity(new StringEntity(tempData, charset));
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String responseString=null;
		
		try 
		{
			responseString = httpClinet.execute(post, responseHandler);
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;
	}
	private static void sleep(int number)
	{
		try
		{
			Thread.sleep(number);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Response without ISO-8859
	 * @param url
	 * @param paramList
	 * @param retryCount
	 * @param encoding
	 * @return
	 */
	public static String sendUrl(String url, List<NameValuePair> paramList, int retryCount, String encoding)
	{
		DefaultHttpClient httpClinet = new DefaultHttpClient();
		retryCount=retryCount<0?0:retryCount;
		retryCount=retryCount>10?10:retryCount;
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, true);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(url);
		httpClinet.setHttpRequestRetryHandler(retryHandler);
		UrlEncodedFormEntity entity = null;
		try 
		{
			entity = new UrlEncodedFormEntity(paramList, encoding);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		post.setEntity(entity);
		
		String responseString = null;
		
		try 
		{
			responseString = httpClinet.execute(post, responseHandler);
		} 
		catch (ClientProtocolException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Request Timeout in HttpUtil.java");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			e.printStackTrace();
		}
		
		return responseString;
	}
	/**
	 * Http Get
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url, String charset) throws Exception 
	{
		URL localURL = new URL(url);
		URLConnection connection = this.openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		//響應失敗
		if (httpURLConnection.getResponseCode() >= 300) 
		{
			throw new Exception("HTTP Request is not success, Response code is "  + httpURLConnection.getResponseCode());
		}
		try 
		{
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) 
			{
				resultBuffer.append(tempLine);
			}
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
			if (inputStreamReader != null) 
			{
				inputStreamReader.close();
			}
			if (inputStream != null) 
			{
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}
	private URLConnection openConnection(URL localURL) throws IOException 
	{
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) 
		{
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} 
		else 
		{
			connection = localURL.openConnection();
		}
		return connection;
	}
	/**
	 * Http Get
	 * @param url
	 * @return
	 */
	public static String httpGet(String url, String charset)
	{
		if (charset == null)
			charset = "UTF-8";
		try
		{
			DefaultHttpClient demo = new DefaultHttpClient();
			demo.getParams().setParameter("http.protocol.content-charset", charset);
		    // Get Request Example，取得 google 查詢 httpclient 的結果
		    HttpGet httpGet = new HttpGet(url);
			HttpResponse response = demo.execute(httpGet);
			String responseString = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			{
		    	// 如果回傳是 200 OK 的話才輸出
				//System.out.println(responseString);
			} 
			else 
			{
		        System.out.println(response.getStatusLine());
			}
			return responseString;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
}

