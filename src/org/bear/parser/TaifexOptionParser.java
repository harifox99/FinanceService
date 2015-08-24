package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.JuristicDailyEntity;
/**
 * 台指自營商選擇權未平倉Parser
 * @author edward
 *
 */
public class TaifexOptionParser extends TaifexLotParser 
{
	@Override
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 3 || i == 6 || i == 5 || i == 8)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{																
						if (i == 3 && j == 15)//自營商未平倉買權
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setDealerCall(Integer.parseInt(content));
						}
						else if (i == 5 && j == 12)//外資未平倉買權
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
							content = content.replace(",", "");
							entity.setForeignerCall(Integer.parseInt(content));
						}
						else if (i == 6 && j == 13)//自營商未平倉賣權
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
							content = content.replace(",", "");	
							entity.setDealerPut(Integer.parseInt(content));							
						}
						else if (i == 8 && j == 12)//外資未平倉賣權
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
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
