package org.bear.cron;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bear.main.BuildStockDistributionAll;

public class WeeklyDistribution 
{
	public void start()
	{
		System.out.println("Weekly Distribution Building!!!");
		Date date = new Date();
		//設定日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//進行轉換
		String dateString = dateFormat.format(date);
		BuildStockDistributionAll buildStock = new BuildStockDistributionAll();
		buildStock.getData(dateString, "https://opendata.tdcc.com.tw/getOD.ashx?id=1-5", "StockDistributionWeekly");
	}
	public static void main(String[] args)
	{		
		Date date = new Date();
		//設定日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//進行轉換
		String dateString = dateFormat.format(date);
		BuildStockDistributionAll buildStock = new BuildStockDistributionAll();
		buildStock.getData(dateString, "https://opendata.tdcc.com.tw/getOD.ashx?id=1-5", "StockDistributionWeekly");
	}
}
