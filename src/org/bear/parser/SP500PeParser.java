package org.bear.parser;
import java.net.URL;
import java.util.List;

import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.AmericanMacroPantherDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
/**
 * 儲存S&P500 PE Ratio指數到美國總經資料庫 
 * @author edward
 *
 */
public class SP500PeParser{
	String url = "http://www.multpl.com/table?f=m";
	Source source;
	List<Element> elementList = null;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
	public void getConnection()
	{
		Source source = null;
		try
		{
			source = new Source(new URL(url));
			elementList = source.getAllElements(HTMLElementName.TABLE);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//return elementList;
	}
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String date = null;
			if (i < 2)
				continue;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				if (j == 0)
				{					
					String[] dateArr = content.split(",");
					date = dateArr[1].trim() + "-" + FredIndexParameterMap.MONTH.get(dateArr[0].subSequence(0, dateArr[0].indexOf(" "))) + "-01";					
				}
				else
				{
					try
					{
						int result = dao.update("SP500", content, date);
					    if (result <= 0)
					    {					    	
					    	System.out.println(content + ", " + date);
					    }
					}
					catch (NullPointerException ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}
	}
	public void parse(int tableIndex) {
		this.getTableContent(elementList.get(tableIndex));				
	}
}
