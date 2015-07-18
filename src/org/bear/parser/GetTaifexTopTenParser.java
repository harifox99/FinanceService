package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.JuristicDailyEntity;

public class GetTaifexTopTenParser extends TaifexLotParser 
{
	@Override
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		for (int i = 0; i < trList.size(); i++)
		{
			if (i > 3)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{		
					try
					{															
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);		
						if (i == 4 && j == 3)//台指期當月契約買方
						{																															
							String content = resultElement.getContent().toString();
							content = content.replace(",", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setBigTenLotBuyMonth(Integer.parseInt(this.retrivelData(content)));
						}
						else if (i == 4 && j == 7)//台指期當月契約賣方
						{
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");					
							entity.setBigTenLotSellMonth(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 3)//台指期所有契約買方
						{
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");					
							entity.setBigTenLotBuyTotal(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 7)//台指期所有契約賣方
						{
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");					
							entity.setBigTenLotSellTotal(Integer.parseInt(this.retrivelData(content)));					
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("BigTenLotBuyMonth", entity.getBigTenLotBuyMonth(), date);
		dao.update("BigTenLotBuyTotal", entity.getBigTenLotBuyTotal(), date);
		dao.update("BigTenLotSellMonth", entity.getBigTenLotSellMonth(), date);
		dao.update("BigTenLotSellTotal", entity.getBigTenLotSellTotal(), date);
	}
	private String retrivelData(String content)
	{
		String[] contentArray = content.split("<br>");
		return contentArray[0];
	}
}
