package org.bear.parser.sfi;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.GretaiPriceEntity;

public class GretaiPriceParser extends ParserBase 
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
				//讓數字的","消失
				content = content.replaceAll(",", "");
				try
				{
					if (j == 3 && i == 1)//開盤價
					{					
						entity.setOpenIndex(content);
					}
					else if (j == 6)//收盤價
					{
						entity.setCloseIndex(content);
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}
		}
	}
}
