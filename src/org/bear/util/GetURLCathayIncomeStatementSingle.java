package org.bear.util;
/**
 * 玉山網站非合併損益表的URL
 * @author edward
 *
 */
public class GetURLCathayIncomeStatementSingle extends GetURLContentBase {
	public GetURLCathayIncomeStatementSingle(String stockID, boolean isYear)
	{		                 
		urlHeaderSeason = "http://sjmain.esunsec.com.tw/z/zc/zcq/zcq0_";
		urlHeaderYear = "http://sjmain.esunsec.com.tw/z/zc/zcq/zcqa/zcqa0_";
		urlFooter = ".djhtm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}		
}
