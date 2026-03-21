package org.bear.parser;
import java.net.URL;

import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.AmericanMacroPantherDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.htmlparser.jericho.Source;
/**
 * 
 * @author edward
 *
 */
public class CrbParser {
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
	Source source;
	String content;
	public void getConnection(String url)
	{
		Source source = null;
		try
		{
			source = new Source(new URL(url));
			content = new String(source.toString().getBytes("UTF-8"));
			String[] contentArr = content.split("\n");
			for (int i = 0; i < contentArr.length; i++)
			{
				if (contentArr[i].startsWith("<area shape"))
					this.parse(contentArr[i]);
				else
					continue;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void parse(String rawData)
	{
		String[] rawDataArr = rawData.substring(rawData.indexOf("["), rawData.length()).split(", ");
		rawDataArr[0] = rawDataArr[0].replaceAll("(\\[)|(\\])|(')", "");
		String[] dateArr = rawDataArr[0].split(" ");
		String date = dateArr[1].trim() + "-" + FredIndexParameterMap.MONTH.get(dateArr[0]) + "-01";
		String value = rawDataArr[2].replaceAll("(')|", "").substring(0, 7);
		dao.update("CRB", value, date);
	}
}
