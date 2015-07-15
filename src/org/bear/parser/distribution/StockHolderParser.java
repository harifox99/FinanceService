package org.bear.parser.distribution;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.dao.ThreeBigDao;
public class StockHolderParser {
	ThreeBigDao threeBigDao;
	List<Element> elementList;
	String dateString;
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public ThreeBigDao getThreeBigDao() {
		return threeBigDao;
	}

	public void setThreeBigDao(ThreeBigDao threeBigDao) {
		this.threeBigDao = threeBigDao;
	}

	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{			
			if (i == 0 || i == 1)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String stockID = "";
			try
			{
				for (int j = 0; j < tdList.size(); j++)
				{				
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString().trim();
					content = content.replaceAll(",", "");								
					if (j == 0)
					{
						stockID = content.substring(0, 4);
					}
					else if (j == 4)
					{
						threeBigDao.update("Supervisor", content, dateString, stockID);
					}
					else if (j == 6)
					{
						threeBigDao.update("Manager", content, dateString, stockID);
					}
					else if (j == 7)
					{
						threeBigDao.update("StrongStockHolder", content, dateString, stockID);
					}										
				}
				
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
}
