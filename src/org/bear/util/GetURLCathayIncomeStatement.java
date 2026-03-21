/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 * 玉山網站的損益表URL
 */
public class GetURLCathayIncomeStatement extends GetURLContentBase 
{
	public GetURLCathayIncomeStatement(String stockID, boolean isYear)
	{
		urlHeaderSeason = "https://djinfo.cathaysec.com.tw/z/zc/zcq/zcq.djhtm?A=";		
		urlHeaderYear = "https://djinfo.cathaysec.com.tw/z/zc/zcq/zcq0.djhtm?b=Y&a=";		
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
		System.out.println(urlString);
	}
}
