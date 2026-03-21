package org.bear.util.distribution;

import org.bear.util.GetURLContentBase;

public class StockHolderDistribution extends GetURLContentBase 
{
	public StockHolderDistribution(String year, String month)
	{
		urlHeader = "http://siis.twse.com.tw/publish/sii/";
		urlFooter = ".HTM";
		urlString = urlHeader + year + "IRB110_" + month + urlFooter;
		System.out.println(urlString);
	}
}
