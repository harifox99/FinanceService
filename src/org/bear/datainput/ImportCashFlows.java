package org.bear.datainput;
import java.util.*;
import java.io.*;
import org.bear.parser.CashFlowsParser;
import org.bear.util.*;
import org.bear.dao.BasicStockDao;
import org.bear.dao.CashFlowsDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.CashFlowsEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author edward
 * 去證交所抓現金流量表
 */
public class ImportCashFlows 
{
	public final String cashFlowsUrlHeader = "http://mopsov.tse.com.tw/server-java/t05st36?colorchg=1&off=1&TYPEK=sii&isnew=false&";
	public final String cashFlowsUrlHeader2 = "http://mopsov.tse.com.tw/server-java/t05st36?colorchg=1&off=1&TYPEK=pub&isnew=false&";
	public final String cashFlowsUrlHeader3 = "http://mopsov.tse.com.tw/server-java/t05st36?colorchg=1&off=1&TYPEK=otc&isnew=false&";
	public final String yearSeason[] = {"9701"};//, "9802", "9803", "9804"};
										/*"9501", "9502", "9503", "9504",
										"9601", "9602", "9603", "9604",
										"9701", "9702", "9703", "9704",
										"9801", "9802", "9803", "9804",
										"9901", "9902"};*/
	BasicStockDao basicStockDao;
	CashFlowsDao basicCashFlowsDao;
	public void insertBatchList()
	{
		//1. 先去擷取所有股票代碼
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		basicCashFlowsDao = (CashFlowsDao)context.getBean("basicCashFlowsDao");
		List <BasicStockWrapper> wrapperList = null;
		wrapperList = basicStockDao.findAllData();		
		//2. 去證交所抓資料
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("cashflow.txt"));
			for (int i = 0; i < yearSeason.length; i++)
			{
				List <CashFlowsEntity> list = new ArrayList<CashFlowsEntity>();
				int idleTime = 0;
				for (int j = 0; j < wrapperList.size(); j++)
				{
					boolean isSuccessful;
					String stockID = wrapperList.get(j).getStockID();
					System.out.print("股票代碼：" + stockID + " " + idleTime + ". ");
					writer.write("股票代碼：" + stockID + " " + idleTime + ". ");
					//if (!stockID.startsWith("2413"))
						//continue;
					GetURLContent urlContent;
					/* 證交所不同的公司代碼，其URL Header，不盡相同，所以會有公司代碼對應不同的URL Header，
					 * 不過現在財報資料我已經決定不去證交所抓了
					 */
					if (stockID.startsWith("2391") || stockID.startsWith("2350"))
						urlContent = new GetURLContent(cashFlowsUrlHeader2, yearSeason[i], stockID);
					else if (stockID.startsWith("2381"))
					{
						urlContent = new GetURLContent(cashFlowsUrlHeader3, yearSeason[i], stockID);
					}
					else	
						urlContent = new GetURLContent(cashFlowsUrlHeader, yearSeason[i], stockID);
					do
					{
						CashFlowsParser cashFlowsParser = new CashFlowsParser(urlContent.getContent(), yearSeason[i], list, stockID, basicCashFlowsDao, writer);
						isSuccessful = cashFlowsParser.parse();
					}while (isSuccessful == false);
					Thread.sleep(1000);
					//休息一下，不然證交所會抗議
					/*
					if (idleTime++ > 100)
					{
						break;
					}*/
					//idleTime++;
				}
				writer.close();				
				/*
				HashMap<String, String> hash = new HashMap<String, String>();
				for (int k = 0 ; k < list.size(); k++)
				{
					if (hash.get(list.get(k).getStockID()) == null)
					{
						hash.put(list.get(k).getStockID(), "1");
					}
					else
					{
						System.out.println("Duplicating StockID: " + list.get(k).getStockID());
					}
				}*/
				basicCashFlowsDao.insertBatch(list);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
