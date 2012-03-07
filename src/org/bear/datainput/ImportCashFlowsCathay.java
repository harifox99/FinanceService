package org.bear.datainput;
import java.io.*;
import org.bear.parser.CashFlowsParserCathay;
import org.bear.util.*;
/**
 * @author edward
 * 去國泰網站抓現金流量表
 */
public class ImportCashFlowsCathay extends ImportStockIDData
{									
	public void insertBatchList()
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("cashflowCathay.txt"));			
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				writer.write("股票代碼：" + stockID + " " + idleTime + ". ");
				
				//if (!stockID.startsWith("2392"))
				//if (j < 288)
					//continue;
				GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID);
				CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID);
				cashFlowsParser.parse(2);
				Thread.sleep(10000);		
				/*
				if (idleTime++ > 10)
				{
					break;
				}*/
				idleTime++;
			}
			writer.close();	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
