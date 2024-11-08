package org.bear.datainput;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.DailyPriceDao;
import org.bear.entity.DailyPriceEntity;
import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 耝–らユ戈癟
 * @author bear
 *
 */
public class GetDailyPrice 
{

	public void getContent(String dateString, String encode, DailyPriceDao dao, BasicStockDao basicStockDao)
	{
		String urlString = "https://www.twse.com.tw/rwd/zh/afterTrading/MI_INDEX?date=" + dateString + "&type=ALLBUT0999&response=csv";
		GetURLContent content = new GetURLContent(urlString);		
		FileParser fileParser = new FileParser();
		List<String> listData = fileParser.getResponse(content.getContent(encode));
		boolean isParse = false;
		List<DailyPriceEntity> list = new ArrayList<DailyPriceEntity>();
		Map<String, Integer> mapShares = basicStockDao.getSharesOutstanding();
		for (int i = 300; i < listData.size(); i++)
		{
			System.out.println(listData.get(i));
			if (isParse)
			{
				if (listData.get(i).contains("称爹"))
					break;
				try
				{
					DailyPriceEntity entity = new DailyPriceEntity();
					String[] rawData = listData.get(i).split("\",\"");
					//布絏
					String stockId = rawData[0].replace("\"", "").replace("=", "");
					entity.setStockId(stockId);
					//ら戳
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					entity.setExchangeDate(dateFormat.parse(dateString));
					//Θユ计
					String volume = rawData[2].replace(",", "");
					int piece = Integer.parseInt(volume) / 1000;
					entity.setVolume(piece);
					//Θユ掸计
					String exchangeNum = rawData[3].replace(",", "");
					entity.setExchangeNum(Integer.parseInt(exchangeNum));
					//秨絃基
					String openPrice = rawData[5].replace(",", "");
					entity.setOpenPrice(Double.parseDouble(openPrice));
					//程蔼基
					String highPrice = rawData[6].replace(",", "");
					entity.setHighPrice(Double.parseDouble(highPrice));
					//程基
					String lowPrice = rawData[7].replace(",", "");
					entity.setLowPrice(Double.parseDouble(lowPrice));
					//Μ絃基
					String closePrice = rawData[8].replace(",", "");
					entity.setClosePrice(Double.parseDouble(closePrice));
					//㏄锣瞯
					if (mapShares.get(stockId) != null)
					{
						double turnoverRate = (double) 100 * piece / mapShares.get(stockId);
						BigDecimal bd = new BigDecimal(turnoverRate);
						BigDecimal roundOff = bd.setScale(3, RoundingMode.HALF_UP);
						entity.setTurnoverRate(roundOff.doubleValue());
					}
					list.add(entity);
					System.out.println();
				}
				catch (NumberFormatException ex)
				{
					continue;
				}
				catch (ParseException ex)
				{
					System.out.println("Parse Data Error!");
				}
				
			}
			if (listData.get(i).contains("靡ㄩ腹"))
			{
				isParse = true;
			}
		}
		dao.insertBatch(list);
	}
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		DailyPriceDao dailyPriceDao = (DailyPriceDao)context.getBean("dailyPriceDao");
		BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		GetDailyPrice getDailyPrice = new GetDailyPrice();
		getDailyPrice.getContent("20241108", "Big5", dailyPriceDao, basicStockDao);
	}
}
