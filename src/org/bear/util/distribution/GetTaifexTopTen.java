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
		String[] dateArray = date.split("/");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("datestart", date));
		paramList.add(new BasicNameValuePair("yytemp", dateArray[0]));
		paramList.add(new BasicNameValuePair("mmtemp", dateArray[1]));
		paramList.add(new BasicNameValuePair("ddtemp", dateArray[2]));
		paramList.add(new BasicNameValuePair("chooseitemtemp", "TX"));
		paramList.add(new BasicNameValuePair("chooseitem", "TX"));
		paramList.add(new BasicNameValuePair("choose_yy", dateArray[0]));
		paramList.add(new BasicNameValuePair("choose_mm", dateArray[1]));
		paramList.add(new BasicNameValuePair("choose_dd", dateArray[2]));		 	
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(3);	
	}
}
