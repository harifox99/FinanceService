package org.bear.parser.distribution;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.dao.JdbcThreeBigDao;
import org.bear.util.DateTimeFactory;

public class SupervisorParser {
	JdbcThreeBigDao threeBigDao;
	List<Element> elementList;
	String dateString;
	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}

	public JdbcThreeBigDao getThreeBigDao() {
		return threeBigDao;
	}

	public void setThreeBigDao(JdbcThreeBigDao threeBigDao) {
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
		DateTimeFactory dateTimeFactory = new DateTimeFactory();
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
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
				Date date = dateFormat.parse(dateString);
				String dateString = this.dateString;
				for (int j = 0; j < tdList.size(); j++)
				{				
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString().trim();
					content = content.replaceAll(",", "");				
				
					if (j == 3)
					{
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.A);
						content = resultElement.getContent().toString().trim();
						stockID = content.substring(0, 4);		
						System.out.println(stockID);
					}
					//前兩個月資料
					else if (j == 11)
					{
						date = dateTimeFactory.changeStrToDate(7, dateString);
						dateString = dateTimeFactory.getDateTimetoString(date, 3);
						threeBigDao.update("Supervisor", content, dateString, stockID);
					}
					//前一個月資料
					else if (j == 12)
					{
						date = dateTimeFactory.addMonth(date, 1);
						dateString = dateTimeFactory.getDateTimetoString(date, 3);
						threeBigDao.update("Supervisor", content, dateString, stockID);
					}
					//當月資料
					else if (j == 13)
					{
						date = dateTimeFactory.addMonth(date, 1);
						dateString = dateTimeFactory.getDateTimetoString(date, 3);
						threeBigDao.update("Supervisor", content, dateString, stockID);
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
