package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bear.dao.GoodInfoDao;
import org.bear.entity.GoodInfoEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GoodInfoParser 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	GoodInfoDao dao = (GoodInfoDao)context.getBean("goodInfoDao");
	Map <String, Boolean> mapDay = new HashMap<String, Boolean>();
	public void parse(String responseString, String dateString, boolean isDay)
	{
		try
		{
			List<GoodInfoEntity> list = new ArrayList<GoodInfoEntity>();
			Document xmlDoc = Jsoup.parse(responseString);
	        //(要解析的文件,timeout)
			Elements tables = xmlDoc.select("#tblStockList");
			Element table = tables.get(0);
			Elements rows = table.select("tr");
	        for (int j = 0; j < rows.size(); j++)
	        {
	        	GoodInfoEntity entity = new GoodInfoEntity();
	        	Element td = rows.get(j);
        		Elements tdList = td.select("td");
        		Elements thList = td.select("th");
        		String data = "";
        		if (tdList.size() <= 0 || thList.size() <= 0)
        			continue;     
        		data = thList.get(0).text().trim();
        		//System.out.println("Stock ID: " + data);
        		String dtime = tdList.get(3).text().trim();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				/*
				Date date = new Date();  
			    //System.out.println(dateFormat.format(date));  
			    String year = dateFormat.format(date).substring(0, 4); */
				entity.setExchangeDate(dateFormat.parse(dateString + "/" + dtime));
        		//股票代碼	  
        		if (isDay == true)
        			entity.setStockId(data);
        		else //if (isDay == false)
        		{    //日週同時交叉
					if (mapDay.get(data) != null)
					{
						dao.update("Week_KD_20", "Y", dateString + "/" + dtime, data);
						break;
					}
					else
						entity.setStockId(data);
	        	}	        	        		
				//成交價
				data = tdList.get(0).text().trim();
        		entity.setPrice(Double.parseDouble(data));
        		//日K值
        		data = tdList.get(5).text().trim();
        		entity.setDay_k(data);
        		//日D值
        		data = tdList.get(6).text().trim();
        		entity.setDay_d(data);
        		//周K值
        		data = tdList.get(9).text().trim();
        		entity.setWeek_k(data);	
        		//周D值
        		data = tdList.get(10).text().trim();
				entity.setWeek_d(data);	
				if (isDay)
				{
					entity.setDay_kd_20("Y");
					entity.setWeek_kd_20("N");
					mapDay.put(entity.getStockId(), true);
				}
				else
				{
					entity.setDay_kd_20("N");
					entity.setWeek_kd_20("Y");
				}
				list.add(entity);
			}
			//System.out.print(tdList.get(k).text() + ", ");
	        dao.insertBatch(list);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
