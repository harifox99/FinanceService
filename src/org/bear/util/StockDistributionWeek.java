package org.bear.util;
import java.util.*;
import org.bear.datainput.ImportStockID;
import org.bear.entity.StockDistributionEntity;
import org.bear.parser.file.FileParser;
/**
 * 擷取集保即時資料
 * @author bear
 *
 */
public class StockDistributionWeek extends ImportStockID
{
	public Map<String, StockDistributionEntity> getData(String url)
	{
		Map<String, StockDistributionEntity> entityMap = null;
		try 
	    {					 
			//把Stock List存到HashMap
			Map<String, Boolean> stockMap = new HashMap<String, Boolean>();
			for (int j = 0; j < wrapperList.size(); j++)
				stockMap.put(wrapperList.get(j).getStockID(), true);
			//下載資料
			GetURLContent content = new GetURLContent(url);
			FileParser fileParser = new FileParser();
			List<String> listData = fileParser.getResponse(content.getContent(null));
			//Parse Data
			StockDistributionEntity entity = null;
			entityMap = new HashMap<String, StockDistributionEntity>();
			for (int j = 1; j < listData.size(); j++)
			{				
				String[] stockDataArray = listData.get(j).split(",");
				if (stockMap.get(stockDataArray[1]) != null)
				{
					String number = stockDataArray[2];
					String data = stockDataArray[4].replaceAll(",", "");
					String percentage = stockDataArray[5];
					if (number.equals("1"))
					{						
						entity = new StockDistributionEntity();
						entity.setStockID(stockDataArray[1]);
						entity.setD1(Long.parseLong(data));
						entity.setP1(Double.parseDouble(percentage));
					}
					else if (number.equals("2"))
					{
						entity.setD1000(Long.parseLong(data));
						entity.setP1000(Double.parseDouble(percentage));
					}
					else if (number.equals("3"))
					{
						entity.setD5000(Long.parseLong(data));
						entity.setP5000(Double.parseDouble(percentage));
					}
					else if (number.equals("4"))
					{
						entity.setD10000(Long.parseLong(data));
						entity.setP10000(Double.parseDouble(percentage));
					}
					else if (number.equals("5"))
					{
						entity.setD15000(Long.parseLong(data));
						entity.setP15000(Double.parseDouble(percentage));
					}
					else if (number.equals("6"))
					{
						entity.setD20000(Long.parseLong(data));
						entity.setP20000(Double.parseDouble(percentage));
					}
					else if (number.equals("7"))
					{
						entity.setD30000(Long.parseLong(data));
						entity.setP30000(Double.parseDouble(percentage));
					}
					else if (number.equals("8"))
					{
						entity.setD40000(Long.parseLong(data));
						entity.setP40000(Double.parseDouble(percentage));
					}
					else if (number.equals("9"))
					{
						entity.setD50000(Long.parseLong(data));
						entity.setP50000(Double.parseDouble(percentage));
					}
					else if (number.equals("10"))
					{
						entity.setD100000(Long.parseLong(data));
						entity.setP100000(Double.parseDouble(percentage));
					}
					else if (number.equals("11"))
					{
						entity.setD200000(Long.parseLong(data));
						entity.setP200000(Double.parseDouble(percentage));
					}
					else if (number.equals("12"))
					{
						entity.setD400000(Long.parseLong(data));
						entity.setP400000(Double.parseDouble(percentage));
					}
					else if (number.equals("13"))
					{
						entity.setD600000(Long.parseLong(data));
						entity.setP600000(Double.parseDouble(percentage));
					}
					else if (number.equals("14"))
					{
						entity.setD800000(Long.parseLong(data));
						entity.setP800000(Double.parseDouble(percentage));
					}
					else if (number.equals("15"))
					{
						entity.setD1000000(Long.parseLong(data));
						entity.setP1000000(Double.parseDouble(percentage));
						entityMap.put(entity.getStockID(), entity);
					}
				}
			}
	    }					       
        catch (Exception e) 
        {	
			e.printStackTrace();
		}		
		return entityMap;
	}
	public static void main(String[] args)
	{
		StockDistributionWeek buildStock = new StockDistributionWeek();
		buildStock.getData("https://smart.tdcc.com.tw/opendata/getOD.ashx?id=1-5");
	}
}
