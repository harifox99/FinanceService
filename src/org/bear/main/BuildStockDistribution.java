package org.bear.main;
import org.bear.dao.*;
import org.bear.datainput.ImportStockID;
import org.bear.util.distribution.StockDistribution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildStockDistribution extends ImportStockID
{
	public static void main(String[] args) 
	{
		new BuildStockDistribution();
	}
	public BuildStockDistribution()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao stockDistributionDao = (StockDistributionDao)context.getBean("stockDistributionDao");
		String[] dateString = {"20180331"};
		for (int i = 0; i < dateString.length; i++)
		{
			for (int j = 0; j < wrapperList.size(); j++)
			{
				//if (!wrapperList.get(j).getStockID().equals("3577"))
					//continue;
				System.out.println("StockID: " + wrapperList.get(j).getStockID() + ", " + j);
				StockDistribution stockDistribution = new StockDistribution();
				stockDistribution.setDao(stockDistributionDao);
				stockDistribution.setCurrentMonth(true);
				stockDistribution.getContent(wrapperList.get(j).getStockID(), 
				dateString[i].substring(0, 4), dateString[i].substring(4, 8), null, null);
			}
		}
	}
}
