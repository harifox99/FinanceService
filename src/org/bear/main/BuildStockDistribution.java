package org.bear.main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.bear.dao.StockDistributionDao;
import org.bear.parser.distribution.StockDistributionParser;
import org.jsoup.Connection;
/**
 *  AI開發的集保股權分配表
 */
public class BuildStockDistribution
{
	private static final String URL = "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock";
	public static void main(String[] args) throws Exception
	{
		BuildStockDistribution distribution = new BuildStockDistribution();
		distribution.getData("20260828", "StockDistribution", true);
	}
	public void getData(String dateString, String tableName, boolean isCurrentMonth)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao dao = (StockDistributionDao)context.getBean("stockDistributionDao");
		List<String> stockList = new ArrayList<String>();		
		String scaDate = dateString;
		try
		{
			String stockNo = "";
			// Step0 取得股票列表 
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/bear/Desktop/StockList.txt"));
			String readData;			
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData.trim());
			}
			reader.close();		
			for (int i = 0; i < stockList.size(); i++)
			{
				stockNo = stockList.get(i);
        		// Step1 取得首頁
        		Connection.Response response = Jsoup.connect(URL).method(Connection.Method.GET).userAgent("Mozilla/5.0").execute();
        		Document doc = response.parse();
        		// Step2 取得 Token
        		String token = doc.select("#SYNCHRONIZER_TOKEN").attr("value");
        		System.out.println("TOKEN=" + token);
        
        		// Step3 POST 查詢
        		Connection.Response postResponse = Jsoup.connect(URL).method(Connection.Method.POST)
        				.cookie("JSESSIONID", response.cookie("JSESSIONID")).userAgent("Mozilla/5.0")
        				.data("SYNCHRONIZER_TOKEN", token).data("SYNCHRONIZER_URI", "/portal/zh/smWeb/qryStock")
        				.data("method", "submit").data("firDate", scaDate).data("scaDate", scaDate).data("sqlMethod", "StockNo")
        				.data("stockNo", stockNo).data("stockName", "").execute();
        
        		Document resultDoc = postResponse.parse();    
        		System.out.println(resultDoc.title());
        		// Debug
        		System.out.println(resultDoc.outerHtml());
        		StockDistributionParser parser = new StockDistributionParser();
    			parser.setDao(dao);
    			parser.setStockID(stockNo);
    			parser.setCurrentMonth(isCurrentMonth);
    			parser.setDateString(dateString);
    			parser.setResponseString(resultDoc.outerHtml());
    			parser.parse(1);	
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}