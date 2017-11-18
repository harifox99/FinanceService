package org.bear.util.distribution;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.PutCallPowerParser;
import org.bear.util.HttpUtil;
/**
 * Put Call Power
 * @author edward
 *
 */
public class GetPutCallPower extends GetTaifexLot
{
	public void getContent()
	{
		PutCallPowerParser parser = new PutCallPowerParser();
		String[] dateArray = date.split("/");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("DATA_DATE_Y", dateArray[0]));
		paramList.add(new BasicNameValuePair("DATA_DATE_M", dateArray[1]));
		paramList.add(new BasicNameValuePair("DATA_DATE_D", dateArray[2]));
		paramList.add(new BasicNameValuePair("syear", dateArray[0]));
		paramList.add(new BasicNameValuePair("smonth", dateArray[1]));
		paramList.add(new BasicNameValuePair("sday", dateArray[2]));
		paramList.add(new BasicNameValuePair("qtype", "2"));
		paramList.add(new BasicNameValuePair("commodity_id", "TXO"));
		paramList.add(new BasicNameValuePair("commodity_idt", "TXO"));
		paramList.add(new BasicNameValuePair("market_code", "0"));
		paramList.add(new BasicNameValuePair("dateaddcnt", "0"));
		paramList.add(new BasicNameValuePair("datestart", date));	
		paramList.add(new BasicNameValuePair("MarketCode", "0"));	
		String responseString = HttpUtil.send(url, paramList, 1, "UTF-8");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(2);
	}

}
