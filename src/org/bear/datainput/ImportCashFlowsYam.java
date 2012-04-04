package org.bear.datainput;
import org.bear.parser.CashFlowsParserYam;
import org.bear.util.*;
/**
 * @author edward
 * 去蕃薯藤網站抓現金流量表（年表）
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
				/*if (!stockID.startsWith("3561"))
				if (j < 521)
					continue;
				*/
				GetURLYamCashFlow urlContent = new GetURLYamCashFlow(stockID);
				CashFlowsParserYam cashFlowsParser = new CashFlowsParserYam(urlContent.getContent(), stockID);
				cashFlowsParser.setYear("2010");
				cashFlowsParser.parse(1);
				Thread.sleep(10000);		
				/*
				if (idleTime++ > 10)
				{
					break;
				}*/
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

