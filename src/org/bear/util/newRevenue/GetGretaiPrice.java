package org.bear.util.newRevenue;
import java.util.List;

import net.htmlparser.jericho.Element;

import org.bear.util.GetURLContentBase;
import org.bear.util.StringUtil;
/**
 * 利用櫃臺個股日成交資訊擷取當月開盤、收盤價
 * @author edward
 *
 */
public class GetGretaiPrice extends GetURLContentBase
{
	List<Element> elementList;
	/* 櫃臺由Post改成Get，所以你知道的........
	public void getContent(String stockID, String startYear, String startMonth,
			String endYear, String endMonth) {
		GretaiPriceParser parser = new GretaiPriceParser();
		String url = "http://www.otc.org.tw/ch/stock/aftertrading/daily_trading_info/result_st43.php?";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("timestamp", "1377322695093"));
		paramList.add(new BasicNameValuePair("ajax", "true"));
		paramList.add(new BasicNameValuePair("yy", startYear));
		paramList.add(new BasicNameValuePair("mm", startMonth));
		paramList.add(new BasicNameValuePair("input_stock_code", stockID));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.parse(2);
		entity = parser.getEntity();
		//System.out.println(responseString);	
	}*/
	public GetGretaiPrice(String stockID, String startYear, String startMonth,
			String endYear, String endMonth) 
	{
		urlHeader = "http://www.otc.org.tw/ch/stock/aftertrading/daily_trading_info/st43_print.php?d=";
		urlFooter = "&s=0,asc,0";
		urlString = urlHeader + StringUtil.convertChineseYear(startYear) + "/" + startMonth + "&stkno=" + stockID + urlFooter;		
		System.out.println(urlString);
		elementList = super.getContent();
	}
	public static void main(String args[])
	{
		//GetGretaiPrice price = new GetGretaiPrice();
		//price.getContent("1336", "2013", "7", null, null);
	}
	public List<Element> getElementList() {
		return elementList;
	}
	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}
	
}
