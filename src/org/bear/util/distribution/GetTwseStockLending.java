package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.TwseStockLendingParser;
import org.bear.util.HttpUtil;

public class GetTwseStockLending extends GetTaifexLot 
{
	public void getContent()
	{
		TwseStockLendingParser parser = new TwseStockLendingParser();
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("qdate", date));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(0);
	}
}
