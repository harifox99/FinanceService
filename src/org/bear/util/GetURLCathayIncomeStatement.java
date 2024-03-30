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
		urlHeaderYear = "http://sjmain.esunsec.com.tw/z/zc/zcq/zcqa/zcqa.djhtm?A=";		
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
