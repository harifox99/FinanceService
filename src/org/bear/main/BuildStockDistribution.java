package org.bear.main;
import java.util.List;
import org.bear.dao.JdbcStockDistributionDao;
import org.bear.dao.JdbcStockTypeDao;
import org.bear.dao.JdbcThreeBigDao;
import org.bear.datainput.ImportStockIDData;
import org.bear.parser.distribution.Gretai3BigParser;
import org.bear.util.distribution.GetGretai3Big;
import org.bear.util.distribution.StockDistribution;
import org.bear.util.distribution.GetTwse3Big;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildStockDistribution extends ImportStockIDData
{
	public static void main(String[] args) 
	{
		new BuildStockDistribution();
	}
	public BuildStockDistribution()
	{
		String startYear = "2014";
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JdbcStockDistributionDao stockDistributionDao = (JdbcStockDistributionDao)context.getBean("stockDistributionDao");
		JdbcStockTypeDao stockTypedao = (JdbcStockTypeDao)context.getBean("stockTypeDao");
		JdbcThreeBigDao threeBigDao = (JdbcThreeBigDao)context.getBean("threeBigDao");
		//集保庫存資料
		//String[] dateString = {"20121001", "20121101", "20121203", "20130102", "20130201", "20130301", 
	    //"20130401", "20130502", "20130603", "20130701", "20130801", "20130902", "20131001"};
		String[] dateString = {"20140901"};
		
		for (int i = 0; i < dateString.length; i++)
		{
			for (int j = 0; j < wrapperList.size(); j++)
			{
				//if (!wrapperList.get(j).getStockID().equals("2923"))
					//continue;
				System.out.println("StockID: " + wrapperList.get(j).getStockID() + ", " + j);
				StockDistribution stockDistribution = new StockDistribution();
				stockDistribution.setDao(stockDistributionDao);
				stockDistribution.getContent(wrapperList.get(j).getStockID(), 
					dateString[i].substring(0, 4), dateString[i].substring(4, 8), null, null);
			}
		}
		
		//證交所三大法人增減
		String[] monthList = {"07", "08"};
		List<Integer> typeIdList = stockTypedao.findAllData();
		for (int i = 0; i < typeIdList.size(); i++)
		{
			for (int j = 0; j < monthList.length; j++)
			{
				//7(化學生技醫療)包含21(化學工業) and 22(生技醫療業)，其他族繁不及備載
				if (typeIdList.get(i) == 21 || typeIdList.get(i) == 22 || typeIdList.get(i) == 24 ||
					typeIdList.get(i) == 25 || typeIdList.get(i) == 26 || typeIdList.get(i) == 27 ||
					typeIdList.get(i) == 28 || typeIdList.get(i) == 29 || typeIdList.get(i) == 30 || 
					typeIdList.get(i) == 31)
					continue;
				GetTwse3Big twse3Big = new GetTwse3Big();
				twse3Big.setThreeBigDao(threeBigDao);
				System.out.println(startYear + "/" + monthList[j] + ", typeId: " + typeIdList.get(i).toString());
				twse3Big.getContent(typeIdList.get(i).toString(), startYear, monthList[j], null, null);
			}
		}
		
		//櫃臺三大法人增減
		for (int i = 0; i < monthList.length; i++)
		{
			System.out.println(monthList[i]);
			GetGretai3Big gretai3Big = new GetGretai3Big(startYear, monthList[i]);
			Gretai3BigParser parser = new Gretai3BigParser();
			parser.setElementList(gretai3Big.getContent());
			parser.setThreeBigDao(threeBigDao);
			parser.setDateString(startYear + monthList[i]);
			parser.parse(0);
		}
		
		
	}
}
