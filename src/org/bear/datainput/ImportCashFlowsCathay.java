package org.bear.datainput;
import org.bear.constant.FinancialReport;
import org.bear.parser.CashFlowsParserCathay;
import org.bear.util.*;
/**
 * @author edward
 * 去玉山網站抓現金流量表
 */
public class ImportCashFlowsCathay extends ImportStockIDData
{									
	public void insertBatchList()
	{
		try
		{			
			int idleTime = 0;
			//年資料	
			/*
			for (int i = 1361; i < wrapperList.size(); i++)
			{
				int expectedNum = 1;
				String[] years = {"2013"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(i).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, true);
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, years, seasons, true, expectedNum, true);
				cashFlowsParser.parse(2);
				Thread.sleep(3000);		
				idleTime++;
			}*/
			
			//季資料
			for (int i = 1391; i < wrapperList.size(); i++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] seasons = {"01"};
				String[] years = {"2014"};
				String stockID = wrapperList.get(i).getStockID();
				//if (!stockID.equals("2850"))
					//continue;
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, false);
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, years, seasons, false, expectedNum, true);
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
				Thread.sleep(3000);		
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
