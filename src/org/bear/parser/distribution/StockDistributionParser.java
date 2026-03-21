package org.bear.parser.distribution;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.dao.StockDistributionDao;
import org.bear.entity.StockDistributionEntity;
import org.bear.exception.TdccException;
import org.bear.parser.ExceptionParserBase;

public class StockDistributionParser extends ExceptionParserBase
{
	StockDistributionDao dao;
	String stockID;
	String dateString;
	List<Element> elementList = null;
	boolean isCurrentMonth;
	public StockDistributionDao getDao() {
		return dao;
	}
	public void setDao(StockDistributionDao dao) {
		this.dao = dao;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}
	public void setCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}
	public void getTableContent(Element element) throws TdccException 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		StockDistributionEntity entity = new StockDistributionEntity();
		for (int i = 0; i < trList.size(); i++)
		{			
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				content = content.replaceAll(",", "");
				try
				{
					if (i == 0 && j == 0)
					{
						entity.setStockID(stockID);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");						
						if (isCurrentMonth)
						{
							String month = dateString.substring(4, 6);
							String year = dateString.substring(0, 4);
							Date date = dateFormat.parse(year + month);
							entity.setYearMonth(date);
						}
						else
						{
							Date date = dateFormat.parse(this.convertMonth());
							entity.setYearMonth(date);
						}
						
					}
					//1켲쩑짾
					else if (i == 1)
					{
						if (j == 3)
						{
							entity.setD1(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP1(Double.parseDouble(content));
						}
					}
					//1-5켲
					else if (i == 2)
					{
						if (j == 3)
						{
							entity.setD1000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP1000(Double.parseDouble(content));
						}
					}
					//5-10켲
					else if (i == 3)
					{
						if (j == 3)
						{
							entity.setD5000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP5000(Double.parseDouble(content));
						}
					}
					//10-15켲
					else if (i == 4)
					{
						if (j == 3)
						{
							entity.setD10000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP10000(Double.parseDouble(content));
						}
					}
					//15-20켲
					else if (i == 5)
					{
						if (j == 3)
						{
							entity.setD15000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP15000(Double.parseDouble(content));
						}
					}
					//20-30켲
					else if (i == 6)
					{
						if (j == 3)
						{
							entity.setD20000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP20000(Double.parseDouble(content));
						}
					}
					//30-40켲
					else if (i == 7)
					{
						if (j == 3)
						{
							entity.setD30000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP30000(Double.parseDouble(content));
						}
					}
					//40-50켲
					else if (i == 8)
					{
						if (j == 3)
						{
							entity.setD40000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP40000(Double.parseDouble(content));
						}
					}
					//50-100켲
					else if (i == 9)
					{
						if (j == 3)
						{
							entity.setD50000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP50000(Double.parseDouble(content));
						}
					}
					//100-200켲
					else if (i == 10)
					{
						if (j == 3)
						{
							entity.setD100000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP100000(Double.parseDouble(content));
						}
					}
					//200-400켲
					else if (i == 11)
					{
						if (j == 3)
						{
							entity.setD200000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP200000(Double.parseDouble(content));
						}
					}
					//400-600켲
					else if (i == 12)
					{
						if (j == 3)
						{
							entity.setD400000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP400000(Double.parseDouble(content));
						}
					}
					//600-800켲
					else if (i == 13)
					{
						if (j == 3)
						{
							entity.setD600000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP600000(Double.parseDouble(content));
						}
					}
					//800-1000켲
					else if (i == 14)
					{
						if (j == 3)
						{
							entity.setD800000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP800000(Double.parseDouble(content));
						}
					}
					//1000켲쩑쨁
					else if (i == 15)
					{
						if (j == 3)
						{
							entity.setD1000000(Long.parseLong(content));
						}
						else if (j == 4)
						{
							entity.setP1000000(Double.parseDouble(content));
						}
					}
				}
				
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		if (entity.getD1() == 0 && entity.getP1000000() == 0)
			throw new TdccException();
		else
			dao.insert(entity);
	}
	
	private String convertMonth()
	{
		String month = dateString.substring(4, 6);
		String year = dateString.substring(0, 4);
		if (month.equals("01"))
		{
			int intYear = Integer.parseInt(year);
			intYear--;
			return String.valueOf(intYear) + "12";
		}
		else
		{
			int intMonth = Integer.parseInt(month);
			intMonth--;
			return year + String.valueOf(intMonth);
		}
	}
}
