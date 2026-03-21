package org.bear.parser;

import java.text.SimpleDateFormat;
import org.bear.entity.JuristicDailyEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 台指自營商選擇權未平倉Parser
 * @author edward
 *
 */
public class TaifexOptionParser extends TaifexLotParser 
{
	public void parse(String responseString) 
	{
		// TODO Auto-generated method stub
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		Elements rows = table.select("tr");
		for (int i = 0; i < rows.size(); i++)
		{
			if (i == 3 || i == 6 || i == 5 || i == 8)
			{				
				Element trElement = rows.get(i);
				Elements tdList = trElement.select("td");
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{																
						if (i == 3 && j == 10)//自營商未平倉買權
						{
							Element subElement = tdList.get(j).select("span").get(0);	
							String content = subElement.text().trim();			
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setDealerCall(Integer.parseInt(content));
						}
						else if (i == 5 && j == 7)//外資未平倉買權
						{
							Element subElement = tdList.get(j).select("span").get(0);	
							String content = subElement.text().trim();	
							content = content.replace(",", "");
							entity.setForeignerCall(Integer.parseInt(content));
						}
						else if (i == 3 && j == 12)//自營商未平倉賣權
						{
							Element subElement = tdList.get(j).select("span").get(0);	
							String content = subElement.text().trim();	
							content = content.replace(",", "");	
							entity.setDealerPut(Integer.parseInt(content));							
						}
						else if (i == 5 && j == 9)//外資未平倉賣權
						{
							Element subElement = tdList.get(j).select("span").get(0);	
							String content = subElement.text().trim();				
							content = content.replace(",", "");	
							entity.setForeignerPut(Integer.parseInt(content));							
						}
						
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("dealerPut", entity.getDealerPut(), date);
		dao.update("dealerCall", entity.getDealerCall(), date);
		dao.update("foreignerPut", entity.getForeignerPut(), date);
		dao.update("foreignerCall", entity.getForeignerCall(), date);
	}
}
