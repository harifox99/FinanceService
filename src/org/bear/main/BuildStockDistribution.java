package org.bear.main;
import org.bear.dao.*;
import org.bear.datainput.ImportStockID;
import org.bear.exception.TdccException;
import org.bear.token.CsrfToken;
import org.bear.util.distribution.StockDistribution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildStockDistribution extends ImportStockID
{
	public static void main(String[] args) throws InterruptedException 
	{
		new BuildStockDistribution();
	}
	public BuildStockDistribution() throws InterruptedException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao stockDistributionDao = (StockDistributionDao)context.getBean("stockDistributionDao");
		String[] dateString = {"20220930"};										
		for (int i = 0; i < dateString.length; i++)
		{
			CsrfToken token = new CsrfToken("https://www.tdcc.com.tw/portal/zh/smWeb/qryStock");	
			for (int j = 0; j < wrapperList.size(); j++)
			{
				/*
				if (!wrapperList.get(j).getStockID().equals("1235") &&
					!wrapperList.get(j).getStockID().equals("3562") &&	
					!wrapperList.get(j).getStockID().equals("4939") &&
					!wrapperList.get(j).getStockID().equals("6128") &&
					!wrapperList.get(j).getStockID().equals("6240") &&
					!wrapperList.get(j).getStockID().equals("5392"))
					continue;*/
				String csrf_token = token.getCsrf_token();
				while(true)
				{
					try
					{
						System.out.println("csrf_token: " + csrf_token);
						System.out.println("StockID: " + wrapperList.get(j).getStockID() + ", " + j);						
						StockDistribution stockDistribution = new StockDistribution();
						stockDistribution.setDao(stockDistributionDao);
						stockDistribution.setCurrentMonth(true);
						stockDistribution.getContent(wrapperList.get(j).getStockID(), 
						dateString[i].substring(0, 4), dateString[i].substring(4, 8), null, null, csrf_token);
						if (csrf_token == null)
							break;
						else
							continue;
					}
					catch (TdccException ex)
					{
						System.out.println("Zero Data!");
						Thread.sleep(1000);						
						//Do Nothing
					}
				}
			}
		}
	}
}
