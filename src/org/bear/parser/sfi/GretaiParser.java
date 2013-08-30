package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.GretaiPriceEntity;
import org.bear.entity.RevenueEntity;
import org.bear.util.newRevenue.GetGretaiPrice;

public class GretaiParser extends ParserBase 
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String year = null;
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
						year = org.bear.parser.ParserBase.convertYear(content);
						entity.setStockID(stockID);
					}
					else if (j == 1)//る
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
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
						GetGretaiPrice getGretaiPrice = new GetGretaiPrice();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(entity.getYearMonth());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
						String dateString = sdf.format(calendar.getTime());
						getGretaiPrice.getContent(stockID, dateString.substring(0, 4), 
							dateString.substring(4, dateString.length()), null, null);
						GretaiPriceEntity gretaiPriceEntity = getGretaiPrice.getEntity();
						entity.setOpenIndex(gretaiPriceEntity.getOpenIndex());
						entity.setCloseIndex(gretaiPriceEntity.getCloseIndex());
						if (this.checkPrice(entity) == true)
							entityList.add(entity);
						else
						{
							System.out.println("秆猂耫籓戈癟钵盽");
							System.out.println("stockID: " + stockID);
							System.out.println("dateString: " + dateString);
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
}
