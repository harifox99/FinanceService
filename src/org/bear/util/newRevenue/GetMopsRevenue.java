package org.bear.util.newRevenue;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.constant.FinancialReport;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.MopsF_Parser;
import org.bear.parser.sfi.MopsRevenueParser;
import org.bear.util.HttpUtil;
import org.bear.util.StringUtil;
/**
 * 公開資訊觀測站擷取上市與上櫃營收
 * @author edward
 *
 */
public class GetMopsRevenue implements GetSFIContent {

	@Override
	public void getContent(String stockID, String stockName, String startYear, String startMonth,
			String endYear, String endMonth) {		
		//String url = "http://mops.twse.com.tw/mops/web/ajax_t05st10_ifrs";	
		String url = "https://mops.twse.com.tw/mops/web/ajax_t05st10_ifrs";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("encodeURIComponent", "1"));
		paramList.add(new BasicNameValuePair("run", "Y"));
		paramList.add(new BasicNameValuePair("step", "0"));
		paramList.add(new BasicNameValuePair("yearmonth", StringUtil.convertChineseYear(startYear) + startMonth));
		paramList.add(new BasicNameValuePair("TYPEK", "sii"));
		paramList.add(new BasicNameValuePair("co_id", stockID));
		paramList.add(new BasicNameValuePair("off", "1"));
		paramList.add(new BasicNameValuePair("year", StringUtil.convertChineseYear(startYear)));
		paramList.add(new BasicNameValuePair("month", startMonth));
		paramList.add(new BasicNameValuePair("firstin", "true"));
		boolean isSuccessful = false;
		int loopIndex = 5;
		while (isSuccessful == false && loopIndex++ < 7)
		{
			String responseString = null;
			try
			{
				responseString = HttpUtil.sendWithRetry(url, paramList, 1, "UTF-8");	
				if (responseString.contains("資料庫中查無需求資料"))
				{
					break;
				}
				else if (stockName.startsWith("F") || stockName.endsWith("KY"))
				{
					MopsF_Parser parser = new MopsF_Parser();
					parser.setResponseString(responseString);
					parser.setStockID(stockID);
					parser.setYear(startYear);
					parser.setMonth(startMonth);
					parser.parseRepeat(2);
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
				isSuccessful = true;
			}
			catch (IndexOutOfBoundsException ex)
			{
				System.out.println(stockID + ": 查無此股票營收資訊, 重新擷取!");
				System.out.println(responseString);
				isSuccessful = false;
				if (responseString.contains("外國發行人免申報本項資訊"))
					break;
				if (responseString.contains("前至採用IFRSs前之開立發票及營業收入資訊查詢"))
					break;
				//System.exit(0);
				try 
				{
					Thread.sleep(FinancialReport.sleepTime * 3);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					System.out.println(stockID + ": 重新查詢營收發生中斷!");
				}
			}
		}				
	}

}
