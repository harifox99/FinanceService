/**
 * 
 */
package org.bear.util;

/**
 * @author edward
 * 國泰網站的資產負債表URL
 */
public class GetURLCathayBalanceSheet extends GetURLContentBase 
{
	public GetURLCathayBalanceSheet(String stockID, boolean isYear)
	{
		//urlHeaderSeason = "http://dj.mybank.com.tw/z/zc/zcp/zcpa/zcpa_";
	    urlHeaderSeason = "http://www.esunsec.com.tw/z/zc/zcp/zcpa/zcpa_";
		//urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcp/zcpb/zcpb_";
		urlHeaderYear = "http://www.esunsec.com.tw/z/zc/zcp/zcpb/zcpb_";
		urlFooter = ".djhtm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
