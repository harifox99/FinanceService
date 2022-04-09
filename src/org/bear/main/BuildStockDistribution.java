package org.bear.main;
import org.bear.dao.*;
import org.bear.datainput.ImportStockID;
import org.bear.exception.TdccException;
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
		String[] dateString = {"20220401"};
		for (int i = 0; i < dateString.length; i++)
		{
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
				while(true)
				{
					try
					{
						System.out.println("StockID: " + wrapperList.get(j).getStockID() + ", " + j);
						StockDistribution stockDistribution = new StockDistribution();
						stockDistribution.setDao(stockDistributionDao);
						stockDistribution.setCurrentMonth(false);
						stockDistribution.getContent(wrapperList.get(j).getStockID(), 
						dateString[i].substring(0, 4), dateString[i].substring(4, 8), null, null);
						break;
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
