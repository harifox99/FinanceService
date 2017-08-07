package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

public class TwseStockLendingParser extends TaifexLotParser 
{
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);	
		Element trElement = trList.get(trList.size()-1);
		List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
		Element resultElement = null;
		try
		{				
			resultElement = tdList.get(12);
			String content = resultElement.getContent().toString().trim();
			content = content.replace(",", "");				   
			long stockLending = Long.parseLong(content)/1000;	
			dao.update("StockLending",(int)stockLending, date);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}										
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(7));
	}
}
