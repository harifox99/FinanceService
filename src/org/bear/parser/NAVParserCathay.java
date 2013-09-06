/**
 * 
 */
package org.bear.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.util.AccountTitle;
import org.bear.util.StringUtil;

/**
 * @author edward
 *
 */
public class NAVParserCathay extends BalanceSheetParserCathay 
{
	//儲存NAV，以年份為Key，以NAV為Value
	HashMap <String, Double> mapNav;
	//儲存年份
	ArrayList <String> listYear;
	//儲存NAV
	ArrayList <Double> listNav;
	public NAVParserCathay(List<Element> elementList, String stockID)
	{
		this.elementList = elementList;
		this.stockID = stockID;
		mapNav = new HashMap <String, Double>();
		listYear = new ArrayList <String>();
		listNav = new ArrayList <Double>();
	}
	public void getTableContent(Element element)
	{
		String rowData[] = new String[dataLength];
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			for (int j = 0; j < tdList.size(); j++)
			{
				if (j == 0)
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					content = StringUtil.eraseSpecialChar(content);
					if (content.contains("每股淨值"))
						title = AccountTitle.NAV;
					else if (content.equals("期別") || content.equals("年"))
						title = AccountTitle.SEASON;
					else
					{
						title = AccountTitle.EMPTY;
						break;
					}
				}
				else
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					content = content.replaceAll(",", "");
					rowData[j-1] = content;
					if (j == tdList.size() - 1)
					{
						setStockData(rowData);
					}
				}
			}			
		}
	}
	public void setStockData(String rowData[])
	{
		for (int k = 0; k < dataLength; k++)
		{
			if (rowData[k] == null)
				continue;
			if (rowData[k].equals("N/A"))
		    {
				rowData[k] = "0.0";
		    }
			switch (title) 
			{
				case NAV:
				{
					double result = Double.parseDouble(rowData[k].substring(rowData[k].indexOf(">")+1));
					listNav.add(result);
					break;
				}	
				case SEASON:
				{
					if (rowData[k].length() > 3)
						rowData[k] = rowData[k].substring(0, 3);
					listYear.add(convertYear(rowData[k]));
					break;
				}
				default:
					break;
			}
		}		
	}
	public HashMap<String, Double> getNavData()
	{
		for (int i = 0; i < listNav.size(); i++)
		{
			mapNav.put(listYear.get(i), listNav.get(i));
		}
		return mapNav;
	}
	public ArrayList<String> getYearList()
	{
		return listYear;
	}
}
