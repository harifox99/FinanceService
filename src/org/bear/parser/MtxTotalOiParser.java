package org.bear.parser;

import java.text.SimpleDateFormat;
import org.bear.entity.JuristicDailyEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 小台指未平倉餘額Parser
 * @author edward
 *
 */
public class MtxTotalOiParser extends TaifexLotParser 
{
	public void parse(String responseString) 
	{
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		Elements rows = table.select("tr");
		JuristicDailyEntity entity = new JuristicDailyEntity();
		try
		{
			for (int i = 0; i < rows.size(); i++)
			{
				Element trElement = rows.get(i);
				Elements tdList = trElement.select("td");
				for (int j = 0; j < tdList.size(); j++)
				{																	
					if (j == 10)//小台未平倉餘額
					{		
						String content = tdList.get(j).text();		
						content = content.replace(",", "");
						dao.update("Retail_Mtx", "TotalMtx", Integer.parseInt(content), date);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						entity.setExchangeDate(dateFormat.parse(date));
					}
				}
			}	
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
