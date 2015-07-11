/**
 * 
 */
package org.bear.parser;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.util.StringUtil;

/**
 * @author edward
 *
 */
public class BasicDataParserCathay extends ParserBase
{
	/**
	 * NAV
	 */
	double nav;
	/**
	 * Μ絃基
	 */
	double price;
	/**
	 * 程蔼基
	 */
	double maxPrice;
	/**
	 * 程基
	 */
	double minPrice;
	/**
	 * セ痲ゑ
	 */
	double per;
	/**
	 * セ
	 */
	String capital;
	public BasicDataParserCathay(List<Element> elementList, String stockID)
	{
		this.elementList = elementList;
		this.stockID = stockID;
	}
	/* (non-Javadoc)
	 * @see org.bear.parser.Parser#getTableContent(net.htmlparser.jericho.Element)
	 */
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		Element resultElement = null;
		
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			for (int j = 0; j < tdList.size(); j++)
			{
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString();
				content = StringUtil.eraseSpecialChar(content);
				if (content.contains("–瞓"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					if (content.equals("N/A"))
						nav = 0.9;
					else
					{
						//传URL
						List<Element> pList = resultElement.getAllElements(HTMLElementName.SPAN);
						resultElement = pList.get(0);
						content = resultElement.getContent().toString();
						nav = Double.parseDouble(content);
						nav = StringUtil.setPointLength(nav);
					}
				}
				else if (content.equals("Μ絃基"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					price = Double.parseDouble(content);
					price = StringUtil.setPointLength(price);
				}
				else if (content.contains("ず程蔼基"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					maxPrice = Double.parseDouble(content);
					maxPrice = StringUtil.setPointLength(maxPrice);
				}
				else if (content.contains("ず程基"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					minPrice = Double.parseDouble(content);
					minPrice = StringUtil.setPointLength(minPrice);
				}
				else if (content.equals("セ痲ゑ"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					if (content.equals("N/A"))
						per = -1;
					else
					{
						content = content.replaceAll(",", "");
						per = Double.parseDouble(content);
						per = StringUtil.setPointLength(per);
					}
				}
				else if (content.contains("セ"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					content = content.replaceAll(",", "");
					capital = content;
				}
				/*
				//狦琩礚セ痲ゑ玥穨キАセ痲ゑ蠢
				else if (content.equals("穨キАセ痲ゑ"))
				{
					resultElement = tdList.get(j+1);
					content = resultElement.getContent().toString();
					if (per == 0)
					{
						per = Double.parseDouble(content);
						per = StringUtil.setPointLength(per);
					}
				}*/
			}
		}
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
	public double getNav() {
		return nav;
	}
	public void setNav(double nav) {
		this.nav = nav;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public double getPer() {
		return per;
	}
	public void setPer(double per) {
		this.per = per;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
}
