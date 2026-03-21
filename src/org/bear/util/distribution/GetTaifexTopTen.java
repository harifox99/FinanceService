package org.bear.util.distribution;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.bear.parser.GetTaifexTopTenParser;

/**
 * 期交所十大交易人Http Post URL 
 * @author edward
 *
 */
public class GetTaifexTopTen extends GetTaifexLot
{
	public void getContent()
	{
		HttpPostWithHeader postHeader = new HttpPostWithHeader();
		GetTaifexTopTenParser parser = new GetTaifexTopTenParser();	
		postHeader.getContent(url, date, dao, parser);
	}
	public static void main(String args[])
	{
		String date = "2024/04/25";
		String url = null;
		try 
		{
			url = "https://www.taifex.com.tw/cht/3/largeTraderFutQry?" + "datecount=&contractId2=&queryDate=" +
					URLEncoder.encode(date, "UTF-8") + "&contractId=tx";
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		GetTaifexTopTen getTaiFexForeignerLot = new GetTaifexTopTen();
		getTaiFexForeignerLot.setUrl(url);
		getTaiFexForeignerLot.setDate("2024/04/25");
		getTaiFexForeignerLot.setTableIndex(2);
		getTaiFexForeignerLot.getContent();
	}
}
