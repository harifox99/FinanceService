package org.bear.util;

public class GetURLCathayBalanceSheetSingle extends GetURLContentBase {

	public GetURLCathayBalanceSheetSingle(String stockID, boolean isYear) {
		//urlHeaderSeason = "http://dj.mybank.com.tw/z/zc/zcp/zcpa/zcpa0_";
		urlHeaderSeason = "http://sjmain.esunsec.com.tw/z/zc/zcp/zcpa/zcpa0_";
		//urlHeaderYear = "http://dj.mybank.com.tw/z/zc/zcp/zcpb/zcpb0_";
		urlHeaderYear = "http://sjmain.esunsec.com.tw/z/zc/zcp/zcpb/zcpb0_";
		urlFooter = ".djhtm";
		if (isYear)
			this.urlString = urlHeaderYear + stockID + urlFooter;
		else
			this.urlString = urlHeaderSeason + stockID + urlFooter;
	}

}
