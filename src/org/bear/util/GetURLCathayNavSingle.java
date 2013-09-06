package org.bear.util;

public class GetURLCathayNavSingle extends GetURLContentBase {
	public GetURLCathayNavSingle(String stockID, boolean isYear)
	{
		urlHeaderSeason = "";
		urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcr/zcra/zcra0_";
		urlFooter = ".asp.htm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
