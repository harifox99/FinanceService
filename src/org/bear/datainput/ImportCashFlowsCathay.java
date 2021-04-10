package org.bear.datainput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bear.constant.FinancialReport;
import org.bear.parser.CashFlowsParserCathay;
import org.bear.util.*;
/**
 * @author edward
 * 去玉山網站抓現金流量表
 */
public class ImportCashFlowsCathay extends ImportStockID
{									
	public void insertBatchList()
	{
		try
		{			
			int idleTime = 0;
			String readData;
			List<String> stockList = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/capital20200324/Desktop/Book1.txt"));
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData);
			}
			reader.close();
			//年資料	
			
			for (int i = 0; i < wrapperList.size(); i++)
			{
				int expectedNum = 2;
				String[] years = {"2019", "2020"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(i).getStockID();
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, true);
				String responseString = HttpUtil.httpGet(urlContent.getUrlString(), "UTF-8");
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContentString(responseString), stockID, years, seasons, true, expectedNum, true);
				cashFlowsParser.parse(2);
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}
			//季資料
			/*
			for (int i = 0; i < wrapperList.size(); i++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] years = {"2020"};
				String[] seasons = {"01", "02", "03"};						
				String stockID = wrapperList.get(i).getStockID();
				//if (!stockID.equals("6131"))
					//continue;
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, false);
				String responseString = HttpUtil.httpGet(urlContent.getUrlString(), "UTF-8");
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContentString(responseString), stockID, years, seasons, false, expectedNum, true);
				try
				{					
					cashFlowsParser.parse(2);
				}
				catch (NullPointerException ex)
				{
					System.out.println("NullPointerException!");
					idleTime--;
					i--;
				}
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
