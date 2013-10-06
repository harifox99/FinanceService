package org.bear.util.distribution;

import org.bear.util.GetURLContentBase;

public class SupervisorDistribution extends GetURLContentBase {
	public SupervisorDistribution()
	{
		urlHeader = "http://norway.twsthr.info/StockBoardTop.aspx";
		urlString = urlHeader;
	}
}
