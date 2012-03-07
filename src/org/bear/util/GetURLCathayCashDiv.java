/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 *
 */
public class GetURLCathayCashDiv extends GetURLContentBase 
{
	public GetURLCathayCashDiv(String stockID)
	{
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcc/zcc_";
		urlFooter = ".asp.htm";
		this.urlString = urlHeaderYear + stockID + urlFooter;
	}
}
