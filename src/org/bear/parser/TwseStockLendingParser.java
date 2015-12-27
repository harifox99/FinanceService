package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.util.StringUtil;

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
			resultElement = tdList.get(11);
			String content = resultElement.getContent().toString().trim();
			content = content.replace(",", "");				   
			long stockLending = Long.parseLong(content)/1000;	
			//把民國轉換成西元
			String[] dateArray = date.split("/");
			String year = StringUtil.convertYear(dateArray[0]);
			year = year + "/" + dateArray[1] + "/" + dateArray[2];	
			dao.update("StockLending",(int)stockLending, year);
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
