package org.bear.datainput;

import org.bear.entity.BranchDetailEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Gemini Auto Gen Java，執行爬蟲並回傳 Entity 列表
 */
public class MoneyDjCrawler
{

	/**
	 * 執行爬蟲並回傳 Entity 列表
	 */
	public List<BranchDetailEntity> crawlData(String targetUrl)
	{
		List<BranchDetailEntity> detailList = new ArrayList<>();
		try
		{
			// 1. 取得網頁 HTML 文件
			Document doc = Jsoup.connect(targetUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
					.timeout(10000).get();

			// 2. 解析「資料日期」
			Date exchangeDate = extractDate(doc);
			if (exchangeDate == null)
			{
				System.out.println("警告：找不到資料日期，將使用當下時間代替。");
				exchangeDate = new Date();
			}

			// 3. 解析表格資料
			Elements rows = doc.select("table.t0 tr");

			for (Element row : rows)
			{
				Elements tds = row.select("td");

				if (tds.text().contains("買進張數") || tds.isEmpty())
				{
					continue;
				}

				// 左側區塊
				if (tds.size() >= 4)
				{
					BranchDetailEntity entityLeft = parseEntity(tds, 0, exchangeDate);
					if (entityLeft != null)
					{
						detailList.add(entityLeft);
					}
				}

				// 右側區塊
				if (tds.size() >= 8)
				{
					BranchDetailEntity entityRight = parseEntity(tds, 4, exchangeDate);
					if (entityRight != null)
					{
						detailList.add(entityRight);
					}
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("抓取或解析過程中發生錯誤: " + e.getMessage());
			e.printStackTrace();
		}

		return detailList;
	}

	private Date extractDate(Document doc)
	{
		Elements divTags = doc.select("div");
		Pattern datePattern = Pattern.compile("(\\d{4}/\\d{2}/\\d{2})");

		for (Element div : divTags)
		{
			String text = div.text();
			if (text.contains("資料日期"))
			{
				Matcher matcher = datePattern.matcher(text);
				if (matcher.find())
				{
					try
					{
						return new SimpleDateFormat("yyyy/MM/dd").parse(matcher.group(1));
					}
					catch (Exception e)
					{
						return null;
					}
				}
			}
		}
		return null;
	}

	private BranchDetailEntity parseEntity(Elements tds, int startIndex, Date date)
	{
		String stockId = null;
		String scriptContent = tds.get(startIndex).data().trim();
		Pattern pattern = Pattern.compile("GenLink2stk\\((.*?)\\);");
		Matcher matcher = pattern.matcher(scriptContent);

		if (matcher.find())
		{
			String params = matcher.group(1);
			String[] parts = params.split(",");
			if (parts.length >= 2)
			{
				stockId = parts[0].replace("'", "").substring(2);				
				// 如果需要股票名稱，可以在這處理
				//String stockName = parts[1].replace("'", "");
			}
		}

		// 加入 null 檢查以避免例外錯誤
		if (stockId == null || stockId.isEmpty() || stockId.equals("-") || stockId.contains("合計"))
		{
			return null;
		}

		BranchDetailEntity entity = new BranchDetailEntity();
		entity.setExchangeDate(date);
		entity.setStockId(stockId);
		entity.setBuyColumn(parseVolume(tds.get(startIndex + 1).text()));
		entity.setSellColumn(parseVolume(tds.get(startIndex + 2).text()));
		entity.setDiff(parseVolume(tds.get(startIndex + 3).text()));
		return entity;
	}

	private int parseVolume(String volumeText)
	{
		try
		{
			String cleanText = volumeText.replaceAll("[^\\d-]", "");
			if (cleanText.isEmpty())
			{
				return 0;
			}
			return Integer.parseInt(cleanText);
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}
}