package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.MtxTotalOiParser;
import org.bear.util.HttpUtil;
/**
 * 擷取小台指未平倉餘額
 * @author edward
 *
 */
public class GetMtxTotalOi extends GetTaifexLot 
{
	public void getContent()
	{		
		MtxTotalOiParser parser = new MtxTotalOiParser();
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("queryDate", date));
		paramList.add(new BasicNameValuePair("queryType", "2"));
		paramList.add(new BasicNameValuePair("marketCode", "0"));	
		paramList.add(new BasicNameValuePair("commodity_id", "MTX"));	
		paramList.add(new BasicNameValuePair("commodity_idt", "MTX"));	
		String responseString = HttpUtil.send(url, paramList, 1, "UTF-8");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(4);
	}
}
