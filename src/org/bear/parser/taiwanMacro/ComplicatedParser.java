package org.bear.parser.taiwanMacro;

import java.util.List;

import org.bear.util.StringUtil;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 台股股價淨值比Parser
 * @author edward
 *
 */
public class ComplicatedParser extends SimpleParser 
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size()-1; i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String dateString = null;
			double price = 0;
			double book;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				content = content.replace(",", "");
				try
				{
					if (j == 0)
					{
						dateString = content;
					}
					else if (j == 1)
					{
						price = Double.parseDouble(content);
					}
					else if (j == 2)
					{
						book = Double.parseDouble(content);
						double pbr = price/book;
						pbr = StringUtil.setPointLength(pbr);
						int result = dao.update(tableName, String.valueOf(pbr), dateString, "M");
						if (result <= 0)
					    {					    	
					    	System.out.println(content + ", " + dateString);
					    }
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
