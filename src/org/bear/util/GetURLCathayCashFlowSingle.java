package org.bear.util;

public class GetURLCathayCashFlowSingle extends GetURLContentBase {
	public GetURLCathayCashFlowSingle(String stockID, boolean isYear)
	{
		urlHeaderSeason = "http://sjmain.esunsec.com.tw/z/zc/zc3/zc30.djhtm?A=";
		urlHeaderYear = "http://sjmain.esunsec.com.tw/z/zc/zc3/zc3a0.djhtm?A=";	
		urlFooter = "";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}
}
