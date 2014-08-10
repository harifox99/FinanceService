package org.bear.parser;

import java.net.URL;
import java.util.List;

import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.AmericanMacroPantherDao;
import org.bear.dao.PMIndexDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
/**
 * 
 * 儲存S&P500指數到美國總經資料庫與PMI資料庫 
 * @author edward
 *
 */
public class SP500Index{
	/*
	private String[] url = {"http://finance.yahoo.com/q/hp?s=%5EGSPC&a=01&b=1&c=1993&d=04&e=13&f=2013&g=m",
			        "http://finance.yahoo.com/q/hp?s=%5EGSPC&a=01&b=1&c=1993&d=04&e=13&f=2013&g=m&z=66&y=66",
			        "http://finance.yahoo.com/q/hp?s=%5EGSPC&a=01&b=1&c=1993&d=04&e=13&f=2013&g=m&z=66&y=132",
			        "http://finance.yahoo.com/q/hp?s=%5EGSPC&a=01&b=1&c=1993&d=04&e=13&f=2013&g=m&z=66&y=198"};*/
	
	//private String[] url = {"http://finance.yahoo.com/q/hp?s=%5EGSPC&a=00&b=1&c=2013&d=01&e=28&f=2014&g=m"};
	  private String[] url = {"http://finance.yahoo.com/q/hp?s=%5EGSPC&a=00&b=1&c=2013&d=06&e=31&f=2014&g=m"};
	Source source;
	List<Element> elementList = null;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	AmericanMacroPantherDao pantherDao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
	PMIndexDao pmiDao = (PMIndexDao)context.getBean("pmIndexDao");
	public void getConnection()
	{		
		Source source = null;
		for (int i = 0; i < url.length; i++)
		{
			try
			{
				source = new Source(new URL(url[i]));
				elementList = source.getAllElements(HTMLElementName.TABLE);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			//System.out.println("url index: " + i);
			this.parse(14);
		}
		//return elementList;
	}
	private void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == trList.size()-1)
				break;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String date = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				String[] dateArr = null;
				if (j == 0)
				{					
					dateArr = content.split(",");
					date = dateArr[1].trim() + "-" + FredIndexParameterMap.MONTH.get(dateArr[0].subSequence(0, dateArr[0].indexOf(" "))) + "-01";					
				}
				else
				{
					try
					{
						int result;
						if (j == 1)
						{
							result = pantherDao.update("SPOpen", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
							result = pmiDao.update("SPOpen", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}
						else if (j == 2)
						{
							result = pantherDao.update("SPHigh", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
							result = pmiDao.update("SPHigh", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}
						else if (j == 3)
						{
							result = pantherDao.update("SPLow", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
							result = pmiDao.update("SPLow", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}
						else if (j == 4)
						{
							result = pantherDao.update("SPClose", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
							result = pmiDao.update("SPClose", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}
						else
							break;					    
					}
					catch (Exception ex)
					{
						System.out.println(date);
						ex.printStackTrace();
					}
				}
			}
		}
	}
	private void printDaoErrorMessage(int result, String content, String date)
	{
		if (result <= 0)
	    {					    	
	    	System.out.println(content + ", " + date);
	    }
	}
	public void parse(int tableIndex) {
		this.getTableContent(elementList.get(tableIndex));				
	}
	
}
