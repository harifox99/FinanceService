package org.bear.main;
import org.bear.dao.BasicStockDao;
import org.bear.parser.OutStandingParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateOutstandingShare 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
	public void parse()
	{
		String urlString = "https://stock.wespai.com/p/10355";
		OutStandingParser parser = new OutStandingParser();
		parser.setDao(dao);
		parser.setUrl(urlString);
		parser.getConnection();
		parser.parse();
	}
	
	public static void main(String[] args) 
	{
		UpdateOutstandingShare share = new UpdateOutstandingShare();
		share.parse();
	}
}
