package org.bear.util.newRevenue;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.MopsRevenueParser;
import org.bear.util.HttpUtil;
import org.bear.util.StringUtil;
/**
 * 公開資訊觀測站擷取上市與上櫃營收(控股公司)
 * @author edward
 *
 */
public class GetMopRevenueIfrs implements GetSFIContent 
{
	@Override
	public void getContent(String stockID, String stockName, String startYear, String startMonth,
			String stockBranch, String endMonth) 
	{		
		//String url = "http://mops.twse.com.tw/mops/web/ajax_t05st10_ifrs";	
		String url = "https://mops.twse.com.tw/mops/web/ajax_t05st10_ifrs";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("encodeURIComponent", "1"));
		paramList.add(new BasicNameValuePair("run", "Y"));
		paramList.add(new BasicNameValuePair("step", "0"));
		paramList.add(new BasicNameValuePair("yearmonth", StringUtil.convertChineseYear(startYear) + startMonth));
		if (stockBranch.equals("1"))
			paramList.add(new BasicNameValuePair("TYPEK", "sii"));
		else
			paramList.add(new BasicNameValuePair("TYPEK", "otc"));
		paramList.add(new BasicNameValuePair("co_id", stockID));
		paramList.add(new BasicNameValuePair("off", "1"));
		paramList.add(new BasicNameValuePair("year", StringUtil.convertChineseYear(startYear)));
		paramList.add(new BasicNameValuePair("month", startMonth));
		paramList.add(new BasicNameValuePair("firstin", "true"));
		String responseString = null;
		try
		{
			responseString = HttpUtil.sendWithRetry(url, paramList, 1, "UTF-8");
			System.out.println("控股公司");
			if (responseString.contains("資料庫中查無需求資料"))
			{
				System.out.println("資料庫中查無需求資料");
			}
			else
			{
				MopsRevenueParser parser = new MopsRevenueParser();
				parser.setResponseString(responseString);
				parser.setStockID(stockID);
				parser.setYear(startYear);
				parser.setMonth(startMonth);
				parser.parseRepeat(3);
			}
		}
		catch (IndexOutOfBoundsException ex)
		{			
			System.out.println(responseString);
			ex.printStackTrace();
		}				
	}
}
