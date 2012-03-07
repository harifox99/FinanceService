/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 *
 */
public class GetURLCathayNav extends GetURLContentBase 
{
	public GetURLCathayNav(String stockID, boolean isYear)
	{
		urlHeaderSeason = "";
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcr/zcra/zcra_";
		urlFooter = ".asp.htm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
