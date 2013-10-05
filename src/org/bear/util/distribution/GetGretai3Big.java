package org.bear.util.distribution;

import org.bear.util.GetURLContentBase;
import org.bear.util.StringUtil;

public class GetGretai3Big extends GetURLContentBase 
{
	public GetGretai3Big(String year, String month)
	{
		urlHeader = "http://www.otc.org.tw/ch/stock/3insti/DAILY_TradE/BIGM_";
		urlFooter = ".html";
		urlString = urlHeader + StringUtil.convertChineseYear(year) + month + urlFooter;		
	}
}
