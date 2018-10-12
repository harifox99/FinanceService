package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.TaifexOptionParser;
import org.bear.util.HttpUtil;
/**
 * 台指自營商選擇權未平倉餘額
 * @author edward
 *
 */
public class GetTaifexOption extends GetTaifexLot 
{
	public void getContent()
	{
		TaifexOptionParser parser = new TaifexOptionParser();		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("queryDate", date));
		paramList.add(new BasicNameValuePair("queryType", "1"));
		paramList.add(new BasicNameValuePair("doQuery", "1"));	
		paramList.add(new BasicNameValuePair("commodityId", "TXO"));	
		String responseString = HttpUtil.sendUrl(url, paramList, 1, "UTF-8");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(3);	
	}
}
