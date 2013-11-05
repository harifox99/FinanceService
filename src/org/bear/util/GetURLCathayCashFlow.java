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
		urlHeaderSeason = "http://www.esunsec.com.tw/z/zc/zc3/zc3.djhtm?A=";
		urlHeaderYear = "http://www.esunsec.com.tw/z/zc/zc3/zc3a.djhtm?A=";	
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
