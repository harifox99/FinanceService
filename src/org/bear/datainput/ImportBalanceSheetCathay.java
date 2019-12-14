/**
 * 
 */
package org.bear.datainput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bear.constant.FinancialReport;
import org.bear.parser.BalanceSheetParserCathay;
import org.bear.util.GetURLCathayBalanceSheet;

/**
 * @author edward
 * 去國泰網站抓資產負債表
 */
public class ImportBalanceSheetCathay extends ImportStockID
{
	public void insertBatchList()
	{
		try
		{		
			int idleTime = 0;
			String readData;
			List<String> stockList = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/capital20191118/Desktop/Book1.txt"));
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData);
			}
			reader.close();
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] years = {"2019"};
				String[] seasons = {"01", "02", "03"};				
				String stockID = wrapperList.get(j).getStockID();
				//if (!stockID.equals("8925"))
					//continue;
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("代碼：" + stockID + " " + idleTime + ". ");				
				//季資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, false);
				BalanceSheetParserCathay balanceSheetSeason = new BalanceSheetParserCathay(urlContent.getContent(), stockID, false, years, seasons, expectedNum, true);
				try
				{					
					balanceSheetSeason.parse(2);
				}
				catch (NullPointerException ex)
				{
					System.out.println("NullPointerException!");
					idleTime--;
					j--;
				}
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}
			/*
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = 6;
				String[] years = {"2016", "2015", "2014", "2013", "2012", "2011"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//年資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, true);
				BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				balanceSheetYear.parse(2);
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
