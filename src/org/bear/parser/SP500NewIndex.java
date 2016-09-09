package org.bear.parser;

import java.util.List;

import org.bear.dao.AmericanMacroPantherDao;
import org.bear.dao.PMIndexDao;
import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 新的Yahoo財經網站Parser
 * @author edward
 *
 */
public class SP500NewIndex 
{
	GetURLContent content;
	FileParser fileParser;
	private String url = "http://chart.finance.yahoo.com/table.csv?s=^GSPC&a=8&b=9&c=2015&d=8&e=9&f=2016&g=m&ignore=.csv";
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	AmericanMacroPantherDao pantherDao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
	PMIndexDao pmiDao = (PMIndexDao)context.getBean("pmIndexDao");
	public void update()
	{
		List<String> listData;
		content = new GetURLContent(url);
		fileParser = new FileParser();
		listData = fileParser.getResponse(content.getContent());
		String[] data;
		for (int i = 1; i < listData.size(); i++)
		{
			int result;					
			data = listData.get(i).split(",");
			if (data[0].endsWith("1") == false)
			{
				StringBuffer buffer = new StringBuffer(data[0]);
				buffer = buffer.replace(data[0].length()-1, data[0].length(), "1");
				data[0] = buffer.toString(); 
			}
			result = pantherDao.update("SPOpen", StringUtil.setPointLength(data[1], 2), data[0]);
			this.printDaoErrorMessage(result, StringUtil.setPointLength(data[1], 2), data[0]);
			result = pantherDao.update("SPHigh", StringUtil.setPointLength(data[2], 2), data[0]);
			this.printDaoErrorMessage(result, StringUtil.setPointLength(data[2], 2), data[0]);
			result = pantherDao.update("SPLow", StringUtil.setPointLength(data[3], 2), data[0]);
			this.printDaoErrorMessage(result, StringUtil.setPointLength(data[3], 2), data[0]);
			result = pantherDao.update("SPClose", StringUtil.setPointLength(data[4], 2), data[0]);
			this.printDaoErrorMessage(result, StringUtil.setPointLength(data[4], 2), data[0]);
		}
	}
	private void printDaoErrorMessage(int result, String content, String date)
	{
		if (result <= 0)
	    {					    	
	    	System.out.println(content + ", " + date);
	    }
	}

}
