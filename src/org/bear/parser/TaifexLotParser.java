package org.bear.parser;

import java.text.SimpleDateFormat;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.JuristicDailyEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
/**
 * 期交所未平倉契約金額Parser
 * @author edward
 *
 */
public class TaifexLotParser extends EasyParserBase
{
	JuristicDailyReportDao dao;
	String date;
	
	public JuristicDailyReportDao getDao() {
		return dao;
	}

	public void setDao(JuristicDailyReportDao dao) {
		this.dao = dao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public void parse(String responseString)
	{
		JuristicDailyEntity entity = new JuristicDailyEntity();
		Document xmlDoc = Jsoup.parse(responseString);
		Elements tables = xmlDoc.select("table");
		Element table = tables.get(0);
		Elements rows = table.select("tr");
		for (int i = 0; i < rows.size(); i++)
		{
			if (i == 5 || i == 14)
			{				
				Element td = rows.get(i);
				Elements tdList = td.select("td");
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{																
						if (i == 5 && j == 11)//外資未平倉台指期餘額
						{
							Element subElement = tdList.get(j).select("div").get(0);
							Element tinyElement = subElement.select("span").get(0);
							String content = tinyElement.text().trim();	
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setTotalLot(Integer.parseInt(content));
						}
						
						else if (i == 14 && j == 11)//外資未平倉小台指餘額
						{
							Element subElement = tdList.get(j).select("div").get(0);
							Element tinyElement = subElement.select("span").get(0);
							String content = tinyElement.text().trim();	
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setTotalSmallLot(Integer.parseInt(content));							
						}
						else if (i == 5 && j == 5)//外資新增台指期口數
						{
							Element subElement = tdList.get(j).select("div").get(0);
							Element tinyElement = subElement.select("span").get(0);
							String content = tinyElement.text().trim();	
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setNewLot(Integer.parseInt(content));							
						}
						else if (i == 14 && j == 5)//外資新增小台指口數
						{
							Element subElement = tdList.get(j).select("div").get(0);
							Element tinyElement = subElement.select("span").get(0);
							String content = tinyElement.text().trim();	
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setNewSmallLot(Integer.parseInt(content));							
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("TotalLot", entity.getTotalLot(), date);
		dao.update("TotalSmallLot", entity.getTotalSmallLot(), date);
		dao.update("NewLot", entity.getNewLot(), date);
		dao.update("NewSmallLot", entity.getNewSmallLot(), date);
	}

	@Override
	public void getTableContent(net.htmlparser.jericho.Element element) {
		// TODO Auto-generated method stub
		
	}
}
