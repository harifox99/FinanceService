package org.bear.util.distribution;

import org.bear.parser.TaifexOptionParser;

/**
 * 台指自營商選擇權未平倉餘額
 * @author edward
 *
 */
public class GetTaifexOption extends GetTaifexLot 
{
	public void getContent()
	{
		HttpPostWithHeader postHeader = new HttpPostWithHeader();
		TaifexOptionParser parser = new TaifexOptionParser();	
		postHeader.getContent(url, date, dao, parser);
	}
}
