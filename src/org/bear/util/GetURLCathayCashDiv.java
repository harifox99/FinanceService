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
		//urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcc/zcc_";
		urlHeaderYear = "http://www.esunsec.com.tw/z/zc/zcc/zcc.djhtm?A=";
		//urlFooter = ".asp.htm";
		this.urlString = urlHeaderYear + stockID;
	}
}
