package org.bear.util;

public class GetURLCathayIncomeStatementSingle extends GetURLContentBase {
	public GetURLCathayIncomeStatementSingle(String stockID, boolean isYear)
	{
		urlHeaderSeason = "http://dj.mybank.com.tw/z/zc/zcq/zcq0_";
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcq/zcqa/zcqa0_";
		urlFooter = ".asp.htm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}		
}
