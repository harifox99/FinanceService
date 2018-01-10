package org.bear.parser;
import java.util.List;

import org.bear.dao.BasicStockDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 更新股本資料，因為需要計算投本比，外本比
 * 投本比 (投信買賣超佔股本比)，外本比(外資買賣超佔股本比)
 * @author edward
 *
 */
public class CapitalParser extends EasyParserBase
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
	public CapitalParser()
	{
		this.url = "https://stock.wespai.com/p/28904";
	}
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 1; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			String stockID = null;
			for (int j = 0; j < tdList.size(); j++)
			{
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				if (j == 0)
				{
					stockID = content;
				}
				else if (j == 3)
				{
					String capital = content;
					dao.updateCapital(stockID, capital);
				}
			}
		}
	}
	public void parse(int index)
	{		
		this.getTableContent(elementList.get(index));
	}
	
	public static void main(String args[])
	{
		CapitalParser parser = new CapitalParser();
		parser.getConnection();
		parser.parse(0);
		System.out.println("Update Captital Successfully!");
	}
}
