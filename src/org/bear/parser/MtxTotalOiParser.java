package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.JuristicDailyEntity;
/**
 * 小台指未平倉餘額Parser
 * @author edward
 *
 */
public class MtxTotalOiParser extends TaifexLotParser 
{
	@Override
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		try
		{
			for (int i = 0; i < trList.size(); i++)
			{
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{																	
					if (j == 10)//小台未平倉餘額
					{
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();		
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
