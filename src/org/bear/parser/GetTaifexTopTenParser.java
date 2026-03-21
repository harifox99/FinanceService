package org.bear.parser;

import java.text.SimpleDateFormat;
import org.bear.entity.JuristicDailyEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetTaifexTopTenParser extends TaifexLotParser 
{
	public void parse(String responseString)
	{
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		Elements rows = table.select("tr");
		for (int i = 0; i < rows.size(); i++)
		{
			if (i > 3)
			{				
				Element trElement = rows.get(i);
				Elements tdList = trElement.select("td");
				for (int j = 0; j < tdList.size(); j++)
				{		
					try
					{		
						if (i == 4 && j == 3)//台指期當月契約買方
						{						
							Element subElement = tdList.get(j).select("div").get(0);	
							String content = subElement.text().trim();	
							content = content.replace(",", "");
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setTopTenLotBuyMonth(Integer.parseInt(this.retrivelData(content)));
						}
						else if (i == 4 && j == 7)//台指期當月契約賣方
						{
							Element subElement = tdList.get(j).select("div").get(0);	
							String content = subElement.text().toString().trim();	
							content = content.replace(",", "");					
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							entity.setTopTenLotSellMonth(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 3)//台指期所有契約買方
						{
							Element subElement = tdList.get(j).select("div").get(0);
							String content = subElement.text().toString().trim();	
							content = content.replace(",", "");			
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							entity.setTopTenLotBuyTotal(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 7)//台指期所有契約賣方
						{
							Element subElement = tdList.get(j).select("div").get(0);	
							String content = subElement.text().toString().trim();	
							content = content.replace(",", "");		
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							entity.setTopTenLotSellTotal(Integer.parseInt(this.retrivelData(content)));					
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("TopTenLotBuyMonth", entity.getTopTenLotBuyMonth(), date);
		dao.update("TopTenLotBuyTotal", entity.getTopTenLotBuyTotal(), date);
		dao.update("TopTenLotSellMonth", entity.getTopTenLotSellMonth(), date);
		dao.update("TopTenLotSellTotal", entity.getTopTenLotSellTotal(), date);
	}
	private String retrivelData(String content)
	{
		String[] contentArray = content.split(" ");
		return contentArray[0];
	}
}
