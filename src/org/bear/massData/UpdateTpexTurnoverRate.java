package org.bear.massData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;

/**
 * 更新上櫃周轉率
 * @author bear
 *
 */
public class UpdateTpexTurnoverRate
{
	Map<String, Double> mapTurnover = new HashMap<String, Double>();	
	public Map<String, Double> getMapTurnover() {
		return mapTurnover;
	}
	public void setMapTurnover(Map<String, Double> mapTurnover) {
		this.mapTurnover = mapTurnover;
	}
	public void getContent(String dateString, String encode)
	{
		String urlString = "https://www.tpex.org.tw/www/zh-tw/afterTrading/turnover?type=Daily&response=csv&date=";
		GetURLContent content = new GetURLContent(urlString + dateString);		
		FileParser fileParser = new FileParser();
		List<String> listData = fileParser.getResponse(content.getContent(encode));			
		for (int i = 3; i < listData.size(); i++)
		{
			//System.out.println(listData.get(i));
			if (listData.get(i).contains("不含使用綜合帳戶之投信買賣量"))
				break;
			try
			{				
				String[] rawData = listData.get(i).split("\",\"");
				//股票代碼
				String stockId = rawData[1].replace("\"", "").replace("=", "");
				/*
				if (stockId.length() > 4)
					continue;*/
				//周轉率
				String turnover = rawData[5].replace("\"", "");
				double turnoverRate = Double.parseDouble(turnover);				
				mapTurnover.put(stockId, turnoverRate);				
			}
			catch (NumberFormatException ex)
			{
				ex.printStackTrace();
				continue;
			}
		}
	}
	public static void main(String[] args)
	{
		UpdateTpexTurnoverRate getDailyPrice = new UpdateTpexTurnoverRate();
		getDailyPrice.getContent("2024/11/13", "Big5");
	}
	

}
