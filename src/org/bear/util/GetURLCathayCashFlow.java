package org.bear.util;
/**
 * @author edward
 * 國泰網站的現金流量表URL
 */
public class GetURLCathayCashFlow extends GetURLContentBase
{
	//public final String cashFlowsUrlHeader = "http://dj.mybank.com.tw/z/zc/zc3/zc3_1101.asp.htm";
	//public final String cashFlowsUrlHeader2 = "http://dj.mybank.com.tw/z/zc/zc3/zc3_1101.djhtml";
	public GetURLCathayCashFlow(String stockID)
	{
		urlHeader = "http://dj.mybank.com.tw/z/zc/zc3/zc3_";
		urlFooter = ".asp.htm";
		this.urlString = urlHeader + stockID + urlFooter;
	}
}
