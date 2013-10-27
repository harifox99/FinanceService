package org.bear.main;
import org.bear.dao.JdbcThreeBigDao;
import org.bear.parser.distribution.StockHolderParser;
import org.bear.util.StringUtil;
import org.bear.util.distribution.StockHolderDistribution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildSupervisor 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JdbcThreeBigDao threeBigDao = (JdbcThreeBigDao)context.getBean("threeBigDao");
	public static void main(String[] args) 
	{
		new BuildSupervisor();
	}
	public BuildSupervisor()
	{
		String startYear = "2013";
		String[] monthList = {"09"};
		//董監持股改用證交所資料, http://siis.twse.com.tw/publish/sii
		//SupervisorDistribution supervisor = new SupervisorDistribution();
		//SupervisorParser parser = new SupervisorParser();
		//parser.setElementList(supervisor.getContent());
		//parser.setThreeBigDao(threeBigDao);
		//parser.setDateString(startYear + monthList[0] + "01");
		//parser.parse(5);
		
		for (int i = 0; i < monthList.length; i++)
		{
			StockHolderDistribution stockHolder = new StockHolderDistribution(StringUtil.convertChineseYear(startYear), monthList[i]);
			StockHolderParser stockHolderParser = new StockHolderParser();
			stockHolderParser.setThreeBigDao(threeBigDao);
			stockHolderParser.setElementList(stockHolder.getContent());
			stockHolderParser.setDateString(startYear + "-" + monthList[i] + "-01");
			stockHolderParser.parse(1);
		}
	}
}
