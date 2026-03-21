package org.bear.parser;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.parser.taiwanMacro.SimpleParser;
/**
 * 台灣加權指數月資料Parser
 * @author edward
 *
 */
public class TwseIndexParser extends SimpleParser
{
	String dateString;
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		double openIndex = 0;
		double closeIndex = 0;
		double highIndex = 0;
		double lowIndex = 20000;
		for (int i = 0; i < trList.size(); i++)
		{
			if (i < 2)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			
			//String dateString = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				content = content.replaceAll(",", "");
				try
				{
					//System.out.println(content);
					if (i == 2 && j == 1)
					{
						openIndex = Double.parseDouble(content);
					}
					else if (j == 4)
					{
						closeIndex = Double.parseDouble(content);		
					}
					else if (j == 2)
					{
						double index = Double.parseDouble(content);
						if (index > highIndex)
							highIndex = index;
					}
					else if (j == 3)
					{
						double index = Double.parseDouble(content);
						if (index < lowIndex)
							lowIndex = index;
					}
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		//System.out.println(openIndex);
		//System.out.println(closeIndex);
		//System.out.println(highIndex);
		//System.out.println(lowIndex);
		dao.update("twseOpen", String.valueOf(openIndex), dateString, "M");
		dao.update("twseClose", String.valueOf(closeIndex), dateString, "M");
		dao.update("twseHigh", String.valueOf(highIndex), dateString, "M");
		dao.update("twseLow", String.valueOf(lowIndex), dateString, "M");
	}
}
