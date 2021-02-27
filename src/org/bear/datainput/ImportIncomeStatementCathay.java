/**
 * 
 */
package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bear.constant.FinancialReport;
import org.bear.parser.IncomeStatementParserCathay;
import org.bear.util.GetURLCathayIncomeStatement;
import org.bear.util.HttpUtil;

/**
 * @author edward
 * 去國泰網站抓損益表
 */
public class ImportIncomeStatementCathay extends ImportStockID 
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
			
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] seasons = {"01", "02", "03"};
				String[] years = {"2020"};
				String stockID = wrapperList.get(j).getStockID();
				//if (!stockID.equals("8925"))
					//continue;		
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("代碼：" + stockID + " " + idleTime + ". ");
				//季資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, false);
				String responseString = HttpUtil.httpGet(urlContent.getUrlString(), "UTF-8");
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContentString(responseString), stockID, false, years, seasons, expectedNum, true);
				
				try
				{					
					incomeStatementYear.parse(2);
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
				int expectedNum = 1;
				String[] years = {"2019"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//年資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, true);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				incomeStatementYear.parse(2);
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
