package org.bear.parser;

import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.JuristicDailyEntity;
import org.bear.util.StringUtil;
/**
 * 上市指數，漲跌幅，成交量Parser
 * @author edward
 *
 */
public class TwseDailyDataParser extends TaifexLotParser 
{
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		//把西元轉換成民國
		String chineseDate = StringUtil.convertChineseYear(date.substring(0, 4));
		chineseDate = chineseDate + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i > 1)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{				
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();	
						content = content.replace(",", "");						
						if (j == 0)//日期
						{
							if (!chineseDate.equals(content))
								break; 
						}
						else if (j == 2)//成交金額
						{
							long volumn = Long.parseLong(content)/1000000;
							entity.setVolumn((int)volumn);									
						}
						else if (j == 5)//漲跌幅
						{							
							entity.setChange(Double.parseDouble(content));				
						}
						else if (j == 4)//指數
						{
							entity.setTwseIndex(Double.parseDouble(content));	
						}
						
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				
			}			
		}	
		dao.update("TwseIndex", entity.getTwseIndex(), date);
		dao.update("Change", entity.getChange(), date);
		dao.update("volumn", entity.getVolumn(), date);
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(0));
	}
}
