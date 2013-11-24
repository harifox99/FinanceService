/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 *
 */
public class GetURLCathayBasicData extends GetURLContentBase 
{
	public GetURLCathayBasicData(String stockID)
	{
		urlHeaderSeason = "";
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zca/zca_";
		urlFooter = ".asp.htm";
		this.urlString = urlHeaderYear + stockID + urlFooter;
		System.out.println(this.urlString);
	}
}
