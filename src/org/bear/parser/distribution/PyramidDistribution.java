package org.bear.parser.distribution;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bear.dao.PyramidDistributionDao;
import org.bear.datainput.ImportStockID;
import org.bear.entity.PyramidDistributionEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PyramidDistribution extends ImportStockID
{
	public void getData()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PyramidDistributionDao dao = (PyramidDistributionDao)context.getBean("pyramidDistributionDao");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");	
		
		try 
	    {				
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockId = wrapperList.get(j).getStockID();
				System.out.println("StockId: " + stockId);
				URL url = new URL("https://norway.twsthr.info/StockHolders.aspx?stock=" + stockId);
		        Document xmlDoc =  Jsoup.parse(url, 3000); //使用Jsoup jar 去解析網頁
		        //(要解析的文件,timeout)
		        Elements tables = xmlDoc.select("table"); 
		        Element table = tables.get(13);
		        Elements tr = table.select("tr"); //要解析的tag元素為tr
		        PyramidDistributionEntity entity = new PyramidDistributionEntity();
		        for (int i = 1; i < tr.size() - 2; i++)
		        {
			        Element td = tr.get(i);
			        Elements tdList = td.select("td");
			        for (int k = 2; k < tdList.size() - 1; k++)
			        {
			        	String data = tdList.get(k).text();
			        	if (k == 2)
			        	{
			        		data = data.replace("\u00A0", "");
			        		Date date = dateFormat.parse(data);
			        		entity.setStockID(stockId);
			        		entity.setYearMonth(date);			        		
			        	}
			        	else if (k == 3)
			        	{
			        		entity.setP1(Double.parseDouble(data));
			        	}	 
			        	else if (k == 4)
			        	{
			        		entity.setP1000(Double.parseDouble(data));
			        	}
			        	else if (k == 5)
			        	{
			        		entity.setP5000(Double.parseDouble(data));
			        	}
			        	else if (k == 6)
			        	{
			        		entity.setP10000(Double.parseDouble(data));
			        	}
			        	else if (k == 7)
			        	{
			        		entity.setP15000(Double.parseDouble(data));
			        	}
			        	else if (k == 8)
			        	{
			        		entity.setP20000(Double.parseDouble(data));
			        	}
			        	else if (k == 9)
			        	{
			        		entity.setP30000(Double.parseDouble(data));
			        	}
			        	else if (k == 10)
			        	{
			        		entity.setP40000(Double.parseDouble(data));
			        	}
			        	else if (k == 11)
			        	{
			        		entity.setP50000(Double.parseDouble(data));
			        	}
			        	else if (k == 12)
			        	{
			        		entity.setP100000(Double.parseDouble(data));
			        	}
			        	else if (k == 13)
			        	{
			        		entity.setP200000(Double.parseDouble(data));
			        	}
			        	else if (k == 14)
			        	{
			        		entity.setP400000(Double.parseDouble(data));
			        	}
			        	else if (k == 15)
			        	{
			        		entity.setP600000(Double.parseDouble(data));
			        	}
			        	else if (k == 16)
			        	{
			        		entity.setP800000(Double.parseDouble(data));
			        	}
			        	else if (k == 17)
			        	{
			        		entity.setP1000000(Double.parseDouble(data));
			        		dao.insert(entity);
			        	}
			        }
		        }
				Thread.sleep(5000);
			}			
	    }
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		PyramidDistribution distribution = new PyramidDistribution();
		distribution.getData();
	}
}
