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
	        //Element table = xmlDoc.select("table").get(98); //select the first table.
			Elements tables = xmlDoc.select("table");
			boolean isTrueTable = false;
			for (int i = 0; i < tables.size(); i++)
			{
				Element table = xmlDoc.select("table").get(i);
				Elements rows = table.select("tr");
		        for (int j = 0; j < rows.size(); j++)
		        {
		        	GoodInfoEntity entity = new GoodInfoEntity();
		        	Element td = rows.get(j);
	        		Elements tdList = td.select("td");
	        		String data = tdList.get(0).text().trim();
	        		//搜尋代號為開頭的<table>
	        		if (!data.contains("代號") && isTrueTable == false)
    				{
    					 break;
    				}
	        		else
	        		{
	        			isTrueTable = true;
	        		}
	        		for (int k = 0; k < tdList.size(); k++)
	        		{
	        			data = tdList.get(k).text().trim();
	        			if (k == 0)
	        			{
	        				if (data.contains("代號"))
	        					break;
	        				else if (isDay == true)
	        					entity.setStockId(data);
	        				else //if (isDay == false)
	        				{    //日週同時交叉
	        					if (mapDay.get(data) != null)
	        					{
	        						dao.update("Week_KD_20", "Y", dateString, data);
	        						break;
	        					}
	        					else
	        						entity.setStockId(data);
	        					//System.out.println(data);
	        				}
	        			}
	        			else if (k == 2)
	        			{
	        				
	        				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        				/*
	        				Date date = new Date();  
	        			    //System.out.println(dateFormat.format(date));  
	        			    String year = dateFormat.format(date).substring(0, 4); */
							entity.setExchangeDate(dateFormat.parse(dateString));
	        			}
	        			else if (k == 3)
	        				entity.setPrice(Double.parseDouble(data));
	        			else if (k == 7)
	        				entity.setDay_k(data);
	        			else if (k == 8)
	        				entity.setDay_d(data);
	        			else if (k == 11)
	        				entity.setWeek_k(data);
	        			else if (k == 12)
	        			{
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
	        		}
	        		//System.out.println();
		        }
			}
	        dao.insertBatch(list);
	        isTrueTable = false;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
