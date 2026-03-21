/**
 * 
 */
package org.bear.datainput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import org.bear.constant.FinancialReport;
import org.bear.dao.FinancialDataDao;
import org.bear.entity.FinancialDataEntity;
import org.bear.parser.CashDivParserJsoup;
import org.bear.parser.NAVParserCathay;
import org.bear.util.GetURLCathayCashDiv;
import org.bear.util.GetURLCathayNav;
import org.bear.util.GetURLCathayNavSingle;
import org.bear.util.GetURLContentBase;
import org.bear.util.HttpUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class ImportFinancialDataCathay extends ImportStockID 
{
	//將elementList轉成可以儲存至DB的資料
	List <FinancialDataEntity> entityList;
	//其他財務資料DAO
	FinancialDataDao dao;
	FinancialDataEntity entity;
	int expectedNum = 1;
	public void insertBatchList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (FinancialDataDao)context.getBean("basicFinancialDataDao");
		String readData;
		List<String> stockList = new ArrayList<String>();
		try
		{	
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/bear/Desktop/StockListBack.txt"));
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData);
			}
			reader.close();
			int idleTime = 0;			
			for (int j = 0; j < wrapperList.size(); j++)
			{
				entityList = new ArrayList <FinancialDataEntity>();
				String stockID = wrapperList.get(j).getStockID();
				if (stockList.contains(stockID) == false)
				{
					continue;
				}
				//if (!stockID.equals("2437"))
					//continue;
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				/***********/
				this.setFinancialData(stockID, true);
				/***********/
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private void setFinancialData(String stockID, boolean isCombined)
	{
		//每股淨值年資料
		GetURLContentBase urlNav;
		if (isCombined == true)
			urlNav = new GetURLCathayNav(stockID, true);
		else
			urlNav = new GetURLCathayNavSingle(stockID, true);
		String responseString = HttpUtil.httpGet(urlNav.getUrlString(), "UTF-8");
		NAVParserCathay navParser = new NAVParserCathay(urlNav.getContentString(responseString), stockID);
		navParser.parse(1);
		//每年配發股息
		GetURLCathayCashDiv urlCashDiv = new GetURLCathayCashDiv(stockID);
		/** 國泰/玉山的網頁，其實是精誠資訊，HTML有問題，所以換Parser
		  CashDivParserCathay cashDivParser = new CashDivParserCathay(urlCashDiv.getContent(), stockID);
		  cashDivParser.parse(2);		
		  HashMap <String, Double> mapCashDiv = cashDivParser.getCashDivData();
		 */
		CashDivParserJsoup jsoup = new CashDivParserJsoup(urlCashDiv.getUrlString());
		jsoup.parsing(7);
		HashMap <String, Double> mapCashDiv = jsoup.getCashDivData();
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
			{
				continue;
				//entity.setCashDiv(0.0);
			}
			else
				entity.setCashDiv(mapCashDiv.get(year));	
			//只要當年的
			if (entity.year.equals("2021"))
			{
				entityList.add(entity);
				//合併財務資料不足，擷取非合併財務資料
				if (isCombined == true)
					dao.insert(entity);
				else
					dao.insertWithCheck(entity);
			}
		}	
			
		if (entityList.size() < expectedNum && isCombined == true)
		{
			this.setFinancialData(stockID, false);
		}
	}
}
