package org.bear.parser;
import java.util.List;

import org.bear.dao.BasicStockDao;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 在外流通發行股數Parser
 * @author bear
 *
 */
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
			if (trList.get(i).toString().contains("公司<br>代號"))
				continue;
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
					content = content.replace("&nbsp;", "");
					content = content.replace(",", "");
					//System.out.print(content + ", ");
					if (j == 0)
					{
						stockID = content;
					}
					else if (j == 17)
					{
						long share = Long.parseLong(content);
						share = share/1000;
						dao.updateSharesOutstanding(stockID, (int)share);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(1));
	}

}
