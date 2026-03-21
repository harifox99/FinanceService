package org.bear.util;
/**
 * @author edward
 * 玉山網站的現金流量表URL
 */
public class GetURLCathayCashFlow extends GetURLContentBase
{
	//public final String cashFlowsUrlHeader = "http://dj.mybank.com.tw/z/zc/zc3/zc3_1101.asp.htm";
	//public final String cashFlowsUrlHeader2 = "http://dj.mybank.com.tw/z/zc/zc3/zc3_1101.djhtml";
	public GetURLCathayCashFlow(String stockID, boolean isYear)
	{
		urlHeaderSeason = "https://djinfo.cathaysec.com.tw/Z/ZC/ZC3/ZC3.DJHTM?A=";
		urlHeaderYear = "https://djinfo.cathaysec.com.tw/z/zc/zc30.djhtm?b=Y&a=";	
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
		System.out.println(urlString);
	}
}
