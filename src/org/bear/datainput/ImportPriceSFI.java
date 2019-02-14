package org.bear.datainput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bear.util.newRevenue.GetHinetStockPrice;
import org.bear.util.newRevenue.GetMopsRevenue;
import org.bear.util.newRevenue.GetSFIGrateiRevenue;
import org.bear.util.newRevenue.GetSFITwseRevenue;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class ImportPriceSFI extends ImportStockID
{
	public void insertBatchList(String startYear, String startMonth, String endYear, String endMonth, GetSFIContent sfi)
	{
		//去證券期貨發展基金會抓營收資料
		try
		{		
			int sleepTime = 1000;
			int idleTime = 0;
			
			String readData;
			List<String> stockList = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/capital20180917/Desktop/Book1.csv"));
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData);
			}
			reader.close();
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				String stockName = wrapperList.get(j).getStockName();
				
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				System.out.print(j);
				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");	
				idleTime++;
				if (sfi instanceof GetTwseIndividualIndex && stockBranch == 2)
					continue;
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 1)
					continue;
				else if (sfi instanceof GetHinetStockPrice)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 2)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetTwseIndividualIndex && stockBranch == 1)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetSFIGrateiRevenue && stockBranch == 2)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetSFITwseRevenue && stockBranch == 1)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetMopsRevenue)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else
					continue;
				
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
