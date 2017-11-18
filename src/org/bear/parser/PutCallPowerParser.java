package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * Put Call Power
 * @author edward
 *
 */
public class PutCallPowerParser extends TaifexLotParser 
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		long callVolumn = 0;
		long putVolumn = 0;
		try
		{
			for (int i = 1; i < trList.size()-2; i++)
			{
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				String type = "";
				//結算價
				double price = 0;
				//未平倉
				int volumn = 0;
				for (int j = 0; j < tdList.size(); j++)
				{																	
					if (j == 3)//合約種類
					{
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();		
						type = content;
					}
					if (j == 8)//結算價
					{
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();
						if (content.contains("-"))
							break;
						price = Double.parseDouble(content);						
					}
					if (j == 14)//未平倉
					{
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();		
						volumn = Integer.parseInt(content);						
						if (type.equals("Put"))
						{
							double total = volumn * price * 50;
							putVolumn = (long) (putVolumn + total);
						}
						else if (type.equals("Call"))
						{
							double total = volumn * price * 50;
							callVolumn = (long) (callVolumn + total);
						}
						
					}
				}
			}	
			putVolumn = putVolumn/1000000;
			callVolumn = callVolumn/1000000;
			dao.update("JuristicDailyReport", "PutPower", (int)putVolumn, date);
			dao.update("JuristicDailyReport", "CallPower", (int)callVolumn, date);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
