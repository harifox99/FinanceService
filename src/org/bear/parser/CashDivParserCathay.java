/**
 * 
 */
package org.bear.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bear.util.StringUtil;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

/**
 * @author edward
 *
 */
public class CashDivParserCathay extends ParserBase {

	//儲存現金股息，以年份為Key，以NAV為Value
	HashMap <String, Double> mapCashDiv;
	//儲存年份
	ArrayList <String> listYear;
	//儲存配發股息
	ArrayList <Double> listCashDiv;
	public CashDivParserCathay(List<Element> elementList, String stockID)
	{
		this.elementList = elementList;
		this.stockID = stockID;
		mapCashDiv = new HashMap <String, Double>();
		listYear = new ArrayList <String>();
		listCashDiv = new ArrayList <Double>();
	}
	public void getTableContent(Element element)
	{
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i < 3)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			for (int j = 0; j < tdList.size(); j++)
			{
				if (j == 0)
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					content = content.replaceAll(",", "");
					listYear.add(StringUtil.convertYear(content));
				}
				else if (j == 1)
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					content = content.replaceAll(",", "");
					listCashDiv.add(Double.parseDouble(content));
				}
			}			
		}
	}
	public HashMap<String, Double> getCashDivData()
	{
		for (int i = 0; i < listCashDiv.size(); i++)
		{
			mapCashDiv.put(listYear.get(i), listCashDiv.get(i));
		}
		return mapCashDiv;
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
}

