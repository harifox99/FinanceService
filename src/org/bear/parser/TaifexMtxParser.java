package org.bear.parser;

import java.text.SimpleDateFormat;
import org.bear.entity.RetailInvestorsEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 小台指三大法人餘額Parser
 * @author edward
 *
 */
public class TaifexMtxParser extends TaifexLotParser 
{
	public void parse(String responseString) 
	{
		// TODO Auto-generated method stub
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		Elements rows = table.select("tr");
		RetailInvestorsEntity entity = new RetailInvestorsEntity();
		for (int i = 0; i < rows.size(); i++)
		{
			if (i == 11)
			{				
				Element td = rows.get(i);
				Elements tdList = td.select("td");
				for (int j = 0; j < tdList.size(); j++)
				{	
					if (j == 10)
					{
						String content = tdList.get(j).text();
						content = content.replace(",", "");
						try
						{
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setInstitutionalMtx(Integer.parseInt(content));
							dao.insert(entity);
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}	
	}
}
