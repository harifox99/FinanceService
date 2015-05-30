package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.GretaiPriceEntity;
import org.bear.entity.RevenueEntity;
import org.bear.util.StringUtil;
import org.bear.util.newRevenue.GetGretaiPrice;
/**
 * ノ耫籓るΘユ戈癟耝程蔼程キАΜ絃基㏄锣瞯
 * @author edward
 *
 */
public class GretaiParser extends RevenueParserBase 
{
	String year;
	String startMonth;
	String endMonth;
	HashMap<String, Boolean> monthMap = new HashMap<String, Boolean>(); 
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public void getTableContent(Element element) 
	{
		this.setMonthMap();
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String year = null;
			String month = null;
			RevenueEntity entity = new RevenueEntity();
			for (int j = 0; j < tdList.size(); j++)
			{	
				resultElement = tdList.get(j);				
				String content = resultElement.getContent().toString().trim();				
				//琵计","ア
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)//
					{					
						year = StringUtil.convertYear(content);
						entity.setStockID(stockID);
					}
					else if (j == 1)//る
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
						month = content;
						//度耝惠璶る					
						if ( !(this.year.equals(year) && monthMap.get(month) != null) )
						{
							break;
						}
					}
					else if (j == 2)//程蔼基
					{
						entity.setHighIndex(content);
					}
					else if (j == 3)//程基
					{
						entity.setLowIndex(content);
					}
					else if (j == 4)//キАΜ絃基
					{
						entity.setAverageIndex(content);
					}
					else if (j == 8)//秅锣瞯
					{
						entity.setTurnoverRatio(content);
						GetGretaiPrice getGretaiPrice = new GetGretaiPrice(stockID, year, month, null, null);						
						GretaiPriceParser parser = new GretaiPriceParser();	
						parser.setElementList(getGretaiPrice.getElementList());
						parser.parse(0);
						//盢秨絃基籔Μ絃基纗
						GretaiPriceEntity gretaiPriceEntity = parser.getEntity();
						entity.setOpenIndex(gretaiPriceEntity.getOpenIndex());
						entity.setCloseIndex(gretaiPriceEntity.getCloseIndex());
						if (this.checkPrice(entity) == true)
						{						
							entityList.add(entity);
						}
						
						else
						{
							System.out.println("秆猂耫籓戈癟钵盽");
							System.out.println("stockID: " + stockID);
							//System.exit(0);
						}
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}		
			}
		}
		dao.insertBatch(entityList);
	}
	private boolean checkPrice(RevenueEntity entity)
	{
		boolean isLegal = true;
		try
		{
			if (Double.parseDouble(entity.getCloseIndex()) < Double.parseDouble(entity.getLowIndex()))
				entity.setLowIndex(entity.getCloseIndex());
			if (Double.parseDouble(entity.getOpenIndex()) < Double.parseDouble(entity.getLowIndex()))
				entity.setLowIndex(entity.getOpenIndex());
			if (Double.parseDouble(entity.getCloseIndex()) > Double.parseDouble(entity.getHighIndex()))
				entity.setHighIndex(entity.getCloseIndex());
			if (Double.parseDouble(entity.getOpenIndex()) > Double.parseDouble(entity.getHighIndex()))
				entity.setHighIndex(entity.getOpenIndex());
		}
		catch (NumberFormatException nfe)
		{
			isLegal = false;
			System.out.println("NumberFormatException occurred in GretaiParser.checkPrice.");
		}
		catch (NullPointerException npe)
		{
			isLegal = false;
			System.out.println("NullPointerException occurred in GretaiParser.checkPrice.");
		}
		return isLegal;
	}
	private void setMonthMap()
	{
		for (int i = Integer.parseInt(startMonth); i <= Integer.parseInt(endMonth); i++)
		{
			monthMap.put(String.valueOf(i), true);
		}
	}
}
