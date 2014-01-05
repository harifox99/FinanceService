package org.bear.parser.sfi;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.GretaiPriceEntity;

public class GretaiPriceParser extends RevenueParserBase 
{
	GretaiPriceEntity entity;
	public GretaiPriceEntity getEntity() {
		return entity;
	}
	public void setEntity(GretaiPriceEntity entity) {
		this.entity = entity;
	}
	public void getTableContent(Element element) 
	{
		entity = new GretaiPriceEntity();
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			for (int j = 0; j < tdList.size(); j++)
			{	
				resultElement = tdList.get(j);				
				String content = resultElement.getContent().toString().trim();				
				//琵计","ア
				content = content.replaceAll(",", "");
				try
				{
					if (j == 3)//秨絃基
					{			
						if (i == 1)
						{
							Double.parseDouble(content);
							entity.setOpenIndex(content);
						}
						else
						{
							if (entity.getOpenIndex() == null)
							{
								Double.parseDouble(content);
								entity.setOpenIndex(content);
							}
						}
					}
					else if (j == 6)//Μ絃基
					{
						Double.parseDouble(content);
						entity.setCloseIndex(content);
					}
					else
						continue;
				}			
				catch (NumberFormatException nfx)
				{
					System.out.println("基瞷--");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}
		}
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
	
}
