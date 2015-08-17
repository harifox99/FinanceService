/**
 * 
 */
package org.bear.datainput;
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
			
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] years = {"2013"};
				String[] seasons = {"02", "03", "04"};				
				String stockID = wrapperList.get(j).getStockID();
				if (!stockID.equals("1262") &&						
						!stockID.equals("1264") &&
						!stockID.equals("1340") &&
						!stockID.equals("1568") &&
						!stockID.equals("1592") &&
						!stockID.equals("1626") &&
						!stockID.equals("1783") &&
						!stockID.equals("1786") &&
						!stockID.equals("1817") &&
						!stockID.equals("2064") &&
						!stockID.equals("2066") &&
						!stockID.equals("2067") &&
						!stockID.equals("2115") &&
						!stockID.equals("2228") &&
						!stockID.equals("2230") &&
						!stockID.equals("2235") &&
						!stockID.equals("2634") &&
						!stockID.equals("2641") &&
						!stockID.equals("2642") &&
						!stockID.equals("2712") &&
						!stockID.equals("2726") &&
						!stockID.equals("2731") &&
						!stockID.equals("2732") &&
						!stockID.equals("2734") &&
						!stockID.equals("2929") &&
						!stockID.equals("3122") &&
						!stockID.equals("3141") &&
						!stockID.equals("3167") &&
						!stockID.equals("3272") &&
						!stockID.equals("3338") &&
						!stockID.equals("3374") &&
						!stockID.equals("3437") &&
						!stockID.equals("3492") &&
						!stockID.equals("3558") &&
						!stockID.equals("3564") &&
						!stockID.equals("3581") &&
						!stockID.equals("3583") &&
						!stockID.equals("3594") &&
						!stockID.equals("3661") &&
						!stockID.equals("3666") &&
						!stockID.equals("3672") &&
						!stockID.equals("3682") &&
						!stockID.equals("3684") &&
						!stockID.equals("3693") &&
						!stockID.equals("3706") &&
						!stockID.equals("3707") &&
						!stockID.equals("4137") &&
						!stockID.equals("4153") &&
						!stockID.equals("4157") &&
						!stockID.equals("4161") &&
						!stockID.equals("4171") &&
						!stockID.equals("4173") &&
						!stockID.equals("4174") &&
						!stockID.equals("4175") &&
						!stockID.equals("4180") &&
						!stockID.equals("4188") &&
						!stockID.equals("4192") &&
						!stockID.equals("4198") &&
						!stockID.equals("4413") &&
						!stockID.equals("4415") &&
						!stockID.equals("4433") &&
						!stockID.equals("4536") &&
						!stockID.equals("4541") &&
						!stockID.equals("4542") &&
						!stockID.equals("4549") &&
						!stockID.equals("4550") &&
						!stockID.equals("4726") &&
						!stockID.equals("4747") &&
						!stockID.equals("4755") &&
						!stockID.equals("4916") &&
						!stockID.equals("4953") &&
						!stockID.equals("4971") &&
						!stockID.equals("4972") &&
						!stockID.equals("4977") &&
						!stockID.equals("4991") &&
						!stockID.equals("4999") &&
						!stockID.equals("5227") &&
						!stockID.equals("5243") &&
						!stockID.equals("5245") &&
						!stockID.equals("5255") &&
						!stockID.equals("5259") &&
						!stockID.equals("5264") &&
						!stockID.equals("5266") &&
						!stockID.equals("5272") &&
						!stockID.equals("5274") &&
						!stockID.equals("5276") &&
						!stockID.equals("5278") &&
						!stockID.equals("5280") &&
						!stockID.equals("5284") &&
						!stockID.equals("5285") &&
						!stockID.equals("5287") &&
						!stockID.equals("5288") &&
						!stockID.equals("5289") &&
						!stockID.equals("5291") &&
						!stockID.equals("5538") &&
						!stockID.equals("5878") &&
						!stockID.equals("6404") &&
						!stockID.equals("6405") &&
						!stockID.equals("6409") &&
						!stockID.equals("6411") &&
						!stockID.equals("6412") &&
						!stockID.equals("6414") &&
						!stockID.equals("6415") &&
						!stockID.equals("6419") &&
						!stockID.equals("6422") &&
						!stockID.equals("6426") &&
						!stockID.equals("6431") &&
						!stockID.equals("6432") &&
						!stockID.equals("6449") &&
						!stockID.equals("6457") &&
						!stockID.equals("6504") &&
						!stockID.equals("8027") &&
						!stockID.equals("8038") &&
						!stockID.equals("8147") &&
						!stockID.equals("8150") &&
						!stockID.equals("8176") &&
						!stockID.equals("8409") &&
						!stockID.equals("8420") &&
						!stockID.equals("8431") &&
						!stockID.equals("8436") &&
						!stockID.equals("8437") &&
						!stockID.equals("8443") &&
						!stockID.equals("8446") &&
						!stockID.equals("8450") &&
						!stockID.equals("8454") 					
					)
					continue;
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
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
				int expectedNum = 8;
				String[] years = {"2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				/*
				if (!stockID.equals("1262") &&						
						!stockID.equals("1264") &&
						!stockID.equals("1340") &&
						!stockID.equals("1568") &&
						!stockID.equals("1592") &&
						!stockID.equals("1626") &&
						!stockID.equals("1783") &&
						!stockID.equals("1786") &&
						!stockID.equals("1817") &&
						!stockID.equals("2064") &&
						!stockID.equals("2066") &&
						!stockID.equals("2067") &&
						!stockID.equals("2115") &&
						!stockID.equals("2228") &&
						!stockID.equals("2230") &&
						!stockID.equals("2235") &&
						!stockID.equals("2634") &&
						!stockID.equals("2641") &&
						!stockID.equals("2642") &&
						!stockID.equals("2712") &&
						!stockID.equals("2726") &&
						!stockID.equals("2731") &&
						!stockID.equals("2732") &&
						!stockID.equals("2734") &&
						!stockID.equals("2929") &&
						!stockID.equals("3122") &&
						!stockID.equals("3141") &&
						!stockID.equals("3167") &&
						!stockID.equals("3272") &&
						!stockID.equals("3338") &&
						!stockID.equals("3374") &&
						!stockID.equals("3437") &&
						!stockID.equals("3492") &&
						!stockID.equals("3558") &&
						!stockID.equals("3564") &&
						!stockID.equals("3581") &&
						!stockID.equals("3583") &&
						!stockID.equals("3594") &&
						!stockID.equals("3661") &&
						!stockID.equals("3666") &&
						!stockID.equals("3672") &&
						!stockID.equals("3682") &&
						!stockID.equals("3684") &&
						!stockID.equals("3693") &&
						!stockID.equals("3706") &&
						!stockID.equals("3707") &&
						!stockID.equals("4137") &&
						!stockID.equals("4153") &&
						!stockID.equals("4157") &&
						!stockID.equals("4161") &&
						!stockID.equals("4171") &&
						!stockID.equals("4173") &&
						!stockID.equals("4174") &&
						!stockID.equals("4175") &&
						!stockID.equals("4180") &&
						!stockID.equals("4188") &&
						!stockID.equals("4192") &&
						!stockID.equals("4198") &&
						!stockID.equals("4413") &&
						!stockID.equals("4415") &&
						!stockID.equals("4433") &&
						!stockID.equals("4536") &&
						!stockID.equals("4541") &&
						!stockID.equals("4542") &&
						!stockID.equals("4549") &&
						!stockID.equals("4550") &&
						!stockID.equals("4726") &&
						!stockID.equals("4747") &&
						!stockID.equals("4755") &&
						!stockID.equals("4916") &&
						!stockID.equals("4953") &&
						!stockID.equals("4971") &&
						!stockID.equals("4972") &&
						!stockID.equals("4977") &&
						!stockID.equals("4991") &&
						!stockID.equals("4999") &&
						!stockID.equals("5227") &&
						!stockID.equals("5243") &&
						!stockID.equals("5245") &&
						!stockID.equals("5255") &&
						!stockID.equals("5259") &&
						!stockID.equals("5264") &&
						!stockID.equals("5266") &&
						!stockID.equals("5272") &&
						!stockID.equals("5274") &&
						!stockID.equals("5276") &&
						!stockID.equals("5278") &&
						!stockID.equals("5280") &&
						!stockID.equals("5284") &&
						!stockID.equals("5285") &&
						!stockID.equals("5287") &&
						!stockID.equals("5288") &&
						!stockID.equals("5289") &&
						!stockID.equals("5291") &&
						!stockID.equals("5538") &&
						!stockID.equals("5878") &&
						!stockID.equals("6404") &&
						!stockID.equals("6405") &&
						!stockID.equals("6409") &&
						!stockID.equals("6411") &&
						!stockID.equals("6412") &&
						!stockID.equals("6414") &&
						!stockID.equals("6415") &&
						!stockID.equals("6419") &&
						!stockID.equals("6422") &&
						!stockID.equals("6426") &&
						!stockID.equals("6431") &&
						!stockID.equals("6432") &&
						!stockID.equals("6449") &&
						!stockID.equals("6457") &&
						!stockID.equals("6504") &&
						!stockID.equals("8027") &&
						!stockID.equals("8038") &&
						!stockID.equals("8147") &&
						!stockID.equals("8150") &&
						!stockID.equals("8176") &&
						!stockID.equals("8409") &&
						!stockID.equals("8420") &&
						!stockID.equals("8431") &&
						!stockID.equals("8436") &&
						!stockID.equals("8437") &&
						!stockID.equals("8443") &&
						!stockID.equals("8446") &&
						!stockID.equals("8450") &&
						!stockID.equals("8454") 					
					)
					continue;
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
