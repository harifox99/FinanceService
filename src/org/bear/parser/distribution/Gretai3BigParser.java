package org.bear.parser.distribution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.dao.ThreeBigDao;
import org.bear.entity.ThreeBigEntity;

public class Gretai3BigParser {
	ThreeBigDao threeBigDao;
	String dateString;
	List<Element> elementList;
	
	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}

	public ThreeBigDao getThreeBigDao() {
		return threeBigDao;
	}

	public void setThreeBigDao(ThreeBigDao threeBigDao) {
		this.threeBigDao = threeBigDao;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{			
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			ThreeBigEntity entity = new ThreeBigEntity();
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)
					{
						if (content.length() != 4)
							break;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
						Date date = dateFormat.parse(dateString);
						entity.setYearMonth(date);
						entity.setStockID(content);
					}
					else if (j == 11)
					{
						entity.setQuantity(Long.parseLong(content));
					}
						
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (entity.getStockID() != null)
				threeBigDao.insert(entity);
		}
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
}
