package org.bear.util;

public class GetURLCathayNavSingle extends GetURLContentBase {
	public GetURLCathayNavSingle(String stockID, boolean isYear)
	{
		urlHeaderSeason = "";		               
		urlHeaderYear = "http://sjmain.esunsec.com.tw/z/zc/zcr/zcra/zcra0_";
		urlFooter = ".djhtm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
