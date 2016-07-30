package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.RetailInvestorsEntity;
/**
 * 小台指三大法人餘額Parser
 * @author edward
 *
 */
public class TaifexMtxParser extends TaifexLotParser 
{
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		RetailInvestorsEntity entity = new RetailInvestorsEntity();
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 9)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					if (j == 11)
					{
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
						resultElement = resultElement.getFirstElement(HTMLElementName.FONT);
						//resultElement = resultElement.getFirstElement(HTMLElementName.B);
						String content = resultElement.getContent().toString().trim();		
						content = content.replace("<B>", "");
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
