package org.bear.datainput;
import java.io.*;
import org.bear.parser.CashFlowsParserCathay;
import org.bear.util.*;
/**
 * @author edward
 * 去國泰網站抓現金流量表（季表）
 */
public class ImportCashFlowsCathay extends ImportStockIDData
{									
	public void insertBatchList()
	{
		try
		{
			String[] seasons = {"01", "02", "03", "04"};
			for (int i = 0; i < seasons.length; i++)
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter("cashflowCathay.txt"));			
				int idleTime = 0;
				for (int j = 0; j < wrapperList.size(); j++)
				{
					String stockID = wrapperList.get(j).getStockID();
					System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
					writer.write("股票代碼：" + stockID + " " + idleTime + ". ");
					GetURLCathayCashFlow urlContent = new GetURLCathayCashFlow(stockID);
					CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, "2010", seasons[i]);
					cashFlowsParser.parse(2);
					Thread.sleep(10000);		
					idleTime++;
				}
				writer.close();	
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
