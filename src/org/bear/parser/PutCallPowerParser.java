package org.bear.parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Put Call Power
 * @author edward
 *
 */
public class PutCallPowerParser extends TaifexLotParser 
{
	public void parse(String responseString) 
	{
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		Elements rows = table.select("tr");
		long callVolumn = 0;
		long putVolumn = 0;
		try
		{
			for (int i = 1; i < rows.size() - 2; i++)
			{
				Element trElement = rows.get(i);
				Elements tdList = trElement.select("td");
				String type = "";
				//結算價
				double price = 0;
				//未平倉
				int volumn = 0;
				for (int j = 0; j < tdList.size(); j++)
				{																	
					if (j == 3)//合約種類
					{			
						String content = tdList.get(j).text().trim();	
						type = content;
					}
					if (j == 8)//結算價
					{			
						String content = tdList.get(j).text().trim();
						if (content.contains("-"))
							break;
						price = Double.parseDouble(content);						
					}
					if (j == 14)//未平倉
					{	
						String content = tdList.get(j).text().trim();		
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
