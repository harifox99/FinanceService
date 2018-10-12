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
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							entity.setExchangeDate(dateFormat.parse(date));
							entity.setTopTenLotBuyMonth(Integer.parseInt(this.retrivelData(content)));
						}
						else if (i == 4 && j == 7)//台指期當月契約賣方
						{
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");					
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							entity.setTopTenLotSellMonth(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 3)//台指期所有契約買方
						{
							String content = resultElement.getContent().toString().trim();	
							content = content.replace(",", "");			
							content = content.replace("\r", "");
							content = content.replace("\n", "");
							content = content.replace("\t", "");
							entity.setTopTenLotBuyTotal(Integer.parseInt(this.retrivelData(content)));					
						}
						else if (i == 5 && j == 7)//台指期所有契約賣方
						{
							String content = resultElement.getContent().toString().trim();	
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
		String[] contentArray = content.split("<br>");
		return contentArray[0];
	}
}
