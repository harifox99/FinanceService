package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.GetTaifexTopTenParser;
import org.bear.util.HttpUtil;
/**
 * 期交所十大交易人Http Post URL 
 * @author edward
 *
 */
public class GetTaifexTopTen extends GetTaifexLot
{
	public void getContent()
	{
		GetTaifexTopTenParser parser = new GetTaifexTopTenParser();		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("queryDate", date));
		paramList.add(new BasicNameValuePair("contractId", "TX"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(3);	
	}
}
