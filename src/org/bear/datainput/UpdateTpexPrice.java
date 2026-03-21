package org.bear.datainput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bear.dao.BasicStockDao;
import org.bear.dao.DailyPriceDao;
import org.bear.entity.DailyPriceEntity;
import org.bear.massData.UpdateTpexTurnoverRate;
import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 穝耫Θユ戈癟
 * @author bear
 *
 */
public class UpdateTpexPrice 
{
	public void getContent(String dateString, String encode, DailyPriceDao dao, BasicStockDao basicStockDao)
	{
		String urlString = "https://www.tpex.org.tw/www/zh-tw/afterTrading/dailyQuotes?response=csv&date=";
		GetURLContent content = new GetURLContent(urlString + dateString);		
		FileParser fileParser = new FileParser();
		List<String> listData = fileParser.getResponse(content.getContent(encode));
		List<DailyPriceEntity> list = new ArrayList<DailyPriceEntity>();
		//㏄锣瞯
		UpdateTpexTurnoverRate tpexTurnoverRate = new UpdateTpexTurnoverRate();
		tpexTurnoverRate.getContent(dateString, "Big5");		
		Map<String, Double> mapTurnover = tpexTurnoverRate.getMapTurnover();
		for (int i = 3; i < listData.size(); i++)
		{
			//System.out.println(listData.get(i));
			//System.out.println("Index: " + i);
			if (listData.get(i).contains("恨瞶布"))
				break;
			if (listData.get(i).length() < 10)
				continue;
			try
			{
				DailyPriceEntity entity = new DailyPriceEntity();
				String[] rawData = listData.get(i).split("\",\"");
				//布絏
				String stockId = rawData[0].replace("\"", "").replace("=", "");
				if (stockId.length() > 4)
					continue;
				entity.setStockId(stockId);
				//ら戳
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				entity.setExchangeDate(dateFormat.parse(dateString));
				//Θユ计
				String volume = rawData[8].replace(",", "");
				int piece = Integer.parseInt(volume) / 1000;
				entity.setVolume(piece);
				//Θユ掸计
				String exchangeNum = rawData[10].replace(",", "");
				entity.setExchangeNum(Integer.parseInt(exchangeNum));
				//秨絃基
				String openPrice = rawData[4].replace(",", "");
				entity.setOpenPrice(Double.parseDouble(openPrice));
				//程蔼基
				String highPrice = rawData[5].replace(",", "");
				entity.setHighPrice(Double.parseDouble(highPrice));
				//程基
				String lowPrice = rawData[6].replace(",", "");
				entity.setLowPrice(Double.parseDouble(lowPrice));
				//Μ絃基
				String closePrice = rawData[2].replace(",", "");
				entity.setClosePrice(Double.parseDouble(closePrice));
				//㏄锣瞯
				double turnoverRate = mapTurnover.get(stockId);
				entity.setTurnoverRate(turnoverRate);		
				/*
				if (mapShares.get(stockId) != null)
				{
					double turnoverRate = (double) 100 * piece / mapShares.get(stockId);
					BigDecimal bd = new BigDecimal(turnoverRate);
					BigDecimal roundOff = bd.setScale(3, RoundingMode.HALF_UP);
					entity.setTurnoverRate(roundOff.doubleValue());
				}*/
				list.add(entity);
			}
			catch (NumberFormatException ex)
			{
				continue;
			}
			catch (ParseException ex)
			{
				ex.printStackTrace();
				System.out.println("Parse Data Error!");
			}			
		}
		dao.insertBatch(list);
	}
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		DailyPriceDao dailyPriceDao = (DailyPriceDao)context.getBean("dailyPriceDao");
		BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		UpdateTpexPrice getDailyPrice = new UpdateTpexPrice();
		getDailyPrice.getContent("2024/11/15", "Big5", dailyPriceDao, basicStockDao);
	}

}
