package org.bear.parser.sfi;

import java.util.ArrayList;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.bear.dao.RevenueDao;
import org.bear.entity.RevenueEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class RevenueParserBase {
	RevenueDao dao;
	String stockID;
	public String responseString;
	List<Element> elementList = null;
	public List<RevenueEntity> entityList = new ArrayList<RevenueEntity>();	
	public RevenueParserBase()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public void parse(int tableIndex) {
		Source source = new Source(responseString);
		//System.out.println(source.toString());
		try
		{
			elementList = source.getAllElements(HTMLElementName.TABLE);
			this.getTableContent(elementList.get(tableIndex));
		}
		catch (IndexOutOfBoundsException ex)
		{
			System.out.println(stockID + ": 查無此股票資訊");
		}
	}
	
	public List<Element> getElementList() {
		return elementList;
	}
	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}
	public abstract void getTableContent(Element element);
}
