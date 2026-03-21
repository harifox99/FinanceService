package org.bear.util.distribution;
import org.bear.parser.PutCallPowerParser;
/**
 * Put Call Power
 * @author edward
 *
 */
public class GetPutCallPower extends GetTaifexLot
{
	public void getContent()
	{
		HttpPostWithHeader postHeader = new HttpPostWithHeader();
		PutCallPowerParser parser = new PutCallPowerParser();	
		postHeader.getContent(url, date, dao, parser);
	}
	public static void main(String args[])
	{
		String url = "https://www.taifex.com.tw/cht/3/optDailyMarketReport?" + "queryType=2&marketCode=0&commodity_id=TXO" + 
					 "&queryDate=2024%2F04%2F12&MarketCode=0&commodity_idt=TXO";
		GetPutCallPower getPutCallPower = new GetPutCallPower();
		getPutCallPower.setUrl(url);
		getPutCallPower.setDate("2024/04/12");
		getPutCallPower.getContent();
	}

}
