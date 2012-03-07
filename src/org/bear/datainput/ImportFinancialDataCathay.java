/**
 * 
 */
package org.bear.datainput;
import java.util.*;

import org.bear.dao.FinancialDataDao;
import org.bear.entity.FinancialDataEntity;
import org.bear.parser.CashDivParserCathay;
import org.bear.parser.NAVParserCathay;
import org.bear.util.GetURLCathayCashDiv;
import org.bear.util.GetURLCathayNav;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class ImportFinancialDataCathay extends ImportStockIDData 
{
	//將elementList轉成可以儲存至DB的資料
	List <FinancialDataEntity> entityList;
	//其他財務資料DAO
	FinancialDataDao dao;
	FinancialDataEntity entity;
	public void insertBatchList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (FinancialDataDao)context.getBean("basicFinancialDataDao");
		try
		{		
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				entityList = new ArrayList <FinancialDataEntity>();
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//if (!stockID.startsWith("2002"))
				if (j < 497)	
					continue;
				//每股淨值年資料
				GetURLCathayNav urlNav = new GetURLCathayNav(stockID, true);
				NAVParserCathay navParser = new NAVParserCathay(urlNav.getContent(), stockID);
				navParser.parse(2);
				//每年配發股息
				GetURLCathayCashDiv urlCashDiv = new GetURLCathayCashDiv(stockID);
				CashDivParserCathay cashDivParser = new CashDivParserCathay(urlCashDiv.getContent(), stockID);
				cashDivParser.parse(3);
				HashMap <String, Double> mapCashDiv = cashDivParser.getCashDivData();
				HashMap <String, Double> mapNav = navParser.getNavData();
				for (int k = 0; k < mapNav.size(); k++)
				{
					entity = new FinancialDataEntity();
					String year = navParser.getYearList().get(k);
					entity.setYear(year);
					entity.setSeasons("00");
					entity.setNav(mapNav.get(year));
					entity.setStockID(stockID);
					if (mapCashDiv.get(year) == null)
						entity.setCashDiv(0.0);
					else
						entity.setCashDiv(mapCashDiv.get(year));	
					entityList.add(entity);
				}				
				dao.insertBatch(entityList);
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
