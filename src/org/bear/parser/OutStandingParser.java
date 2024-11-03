package org.bear.parser;

import java.util.List;

import org.bear.dao.BasicStockDao;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

public class OutStandingParser extends EasyParserBase 
{
	BasicStockDao dao;
	public BasicStockDao getDao() {
		return dao;
	}
	public void setDao(BasicStockDao dao) {
		this.dao = dao;
	}
	@Override
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);		
		for (int i = 0; i < trList.size(); i++)
		{
			if (i < 1)
				continue;
			else
			{
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;		
				String stockID = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{											
						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();	
						System.out.print(content + ", ");
						if (j == 0)
						{
							stockID = content;
						}
						else if (j == 4)
						{
							int share = Integer.parseInt(content);
							dao.updateOutstandingShare(stockID, share);
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				System.out.println("");
			}
		}
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(0));
	}

}
