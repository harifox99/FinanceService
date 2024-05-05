package org.bear.util.distribution;
import org.bear.parser.MtxTotalOiParser;
/**
 * 擷取小台指未平倉餘額
 * @author edward
 *
 */
public class GetMtxTotalOi extends GetTaifexLot 
{
	public void getContent()
	{		
		HttpPostWithHeader postHeader = new HttpPostWithHeader();
		MtxTotalOiParser parser = new MtxTotalOiParser();	
		postHeader.getContent(url, date, dao, parser);
	}
}
