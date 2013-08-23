package org.bear.datainput;
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
			//for (int i = 0; i < wrapperList.size(); i++)
			for (int i = 0; i < 1; i++)
			{
				String[] seasons = {"00"};
				String[] years = {"2012"};
				String stockID = wrapperList.get(i).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, true);
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, years, seasons, true);
				cashFlowsParser.parse(2);
				Thread.sleep(5000);		
				idleTime++;
			}
			//季資料
			//for (int i = 0; i < wrapperList.size(); i++)
			for (int i = 0; i < 1; i++)
			{
				String[] seasons = {"01", "02", "03", "04"};
				String[] years = {"2012"};
				String stockID = wrapperList.get(i).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID, false);
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, years, seasons, false);
				cashFlowsParser.parse(2);
				Thread.sleep(5000);		
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
