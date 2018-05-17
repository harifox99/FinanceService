package org.bear.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
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
				responseString = new String(responseString.getBytes("ISO-8859-1"), encoding);
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
		catch (UnsupportedEncodingException e) 
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
//		String tempData=data;
//		try {
//			tempData=URLEncoder.encode(tempData,"ISO-8859-1");
//
//			URL targetUrl=new URL(url);
//			
//			URLConnection conn=targetUrl.openConnection();
//			System.out.println("send by enc:"+conn.getContentEncoding());
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
//			wr.write(tempData);
//			wr.flush();
//			
//			BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line=null;
//			StringBuffer sb=new StringBuffer();
//			while ((line=rd.readLine())!=null){
//				sb.append(line);
//			}
//			wr.close();
//			rd.close();
//			return sb.toString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
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
}

