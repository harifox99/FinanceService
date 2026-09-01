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
		urlHeaderYear = "https://djinfo.cathaysec.com.tw/z/zc/zcr/zcra/zcra.djhtm?A=";
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
