package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.List;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.JuristicDailyEntity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 期交所未平倉契約金額Parser
 * @author edward
 *
 */
public class TaifexLotParser extends EasyParserBase {
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

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 5 || i == 14)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{																
						if (i == 5 && j == 11)//外資未平倉台指期餘額
						{
							resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
							resultElement = resultElement.getFirstElement(HTMLElementName.FONT);
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setLot(Integer.parseInt(content));
						}
						else if (i == 14 && j == 11)//外資未平倉小台指餘額
						{
							resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
							resultElement = resultElement.getFirstElement(HTMLElementName.FONT);
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");
							entity.setSmallLot(Integer.parseInt(content));							
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("Lot", entity.getLot(), date);
		dao.update("SmallLot", entity.getSmallLot(), date);
	}
}
