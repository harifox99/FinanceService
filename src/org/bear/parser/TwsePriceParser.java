package org.bear.parser;

import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 證交所收盤價Parser
 * @author edward
 *
 */
public class TwsePriceParser extends EasyParserBase
{
	HashMap<String, Double> hashPrice = new HashMap<String, Double>();
	public HashMap<String, Double> getHashPrice() {
		return hashPrice;
	}
	public void setHashPrice(HashMap<String, Double> hashPrice) {
		this.hashPrice = hashPrice;
	}
	@Override
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 3; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			String stockID = "";
			for (int j = 0; j < tdList.size(); j++)
			{
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString();	
				if (j == 0)
					stockID = content.trim();
				else if (j == 8)
				{
					double price;
					if (content.equals("--"))
					{
						price = -1;
						hashPrice.put(stockID, price);
					}
					else if (content.contains(","))
					{
						content = content.replace(",", "");
						price = Double.parseDouble(content);
						hashPrice.put(stockID, price);
					}
					else
					{
						price = Double.parseDouble(content);
						hashPrice.put(stockID, price);
					}
				}
			}
		}			
	}
	public void parse(int index)
	{		
		this.getTableContent(elementList.get(index));
	}	
}
