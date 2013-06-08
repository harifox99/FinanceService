package org.bear.parser.taiwanMacro;

import java.util.List;
/**
 * ¬¡´ÁÀx»W¦s´ÚYoY
 */
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
public class DemandDepositParser extends CbcParser
{
	@Override
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size()-1; i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String dateString = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				try
				{
					if (j == 0)
					{
						dateString = content;
					}
					else
					{
						int result = dao.update("DemandDeposits", content, dateString, "M");
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
