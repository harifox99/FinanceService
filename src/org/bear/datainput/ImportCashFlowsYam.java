package org.bear.datainput;
import org.bear.parser.CashFlowsParserYam;
import org.bear.util.*;
/**
 * @author edward
 * 去蕃薯藤網站抓現金流量表
 */
public class ImportCashFlowsYam extends ImportStockIDData
{					
	public void insertBatchList()
	{
		//去蕃薯藤網站抓資料
		try
		{		
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");			
				GetURLYamCashFlow urlContent = new GetURLYamCashFlow(stockID);
				CashFlowsParserYam cashFlowsParser = new CashFlowsParserYam(urlContent.getContent(), stockID);
				cashFlowsParser.setYear("2011");
				cashFlowsParser.parse(1);
				Thread.sleep(10000);		
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

