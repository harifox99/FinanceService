/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 * 國泰網站的損益表URL
 */
public class GetURLCathayIncomeStatement extends GetURLContentBase 
{
	public GetURLCathayIncomeStatement(String stockID, boolean isYear)
	{
		urlHeaderSeason = "http://dj.mybank.com.tw/z/zc/zcq/zcq_";
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcq/zcqa/zcqa_";
		urlFooter = ".asp.htm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
