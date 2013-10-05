package org.bear.parser.distribution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bear.dao.JdbcThreeBigDao;
import org.bear.entity.ThreeBigEntity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

public class Twse3BigParser extends DistributionParserBase{
	String dateString;
	JdbcThreeBigDao threeBigDao;
	
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

	@Override
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
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
						content = resultElement.getContent().toString().trim();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
						Date date = dateFormat.parse(dateString);
						entity.setYearMonth(date);
						entity.setStockID(content);
					}
					else if (j == 8)
					{
						entity.setQuantity(Long.parseLong(content));
					}
						
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			System.out.println("StockID: " + entity.getStockID());
			threeBigDao.insert(entity);
		}
	}

}
