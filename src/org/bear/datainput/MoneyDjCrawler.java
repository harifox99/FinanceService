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
 * Gemini Auto Gen Java
 */
public class MoneyDjCrawler
{
	public static void main(String[] args)
	{
		String targetUrl = "https://www.moneydj.com/z/zg/zgb/zgb0.djhtm?a=1030&b=0031003000330046&c=E&d=5";
		MoneyDjCrawler moneyDjCrawler = new MoneyDjCrawler();
		moneyDjCrawler.crawer(targetUrl);
	}

	public void crawer(String targetUrl)
	{
		try
		{
			System.out.println("開始連線並取得網頁資料...");

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
			List<BranchDetailEntity> detailList = new ArrayList<>();

			// MoneyDJ 的主要資料表格通常會套用 "t01" 這個 CSS class
			Elements rows = doc.select("table.t0 tr");

			for (Element row : rows)
			{
				Elements tds = row.select("td");

				// 略過標題列（如果包含「買進張數」等文字則跳過）
				if (tds.text().contains("買進張數") || tds.isEmpty())
				{
					continue;
				}

				// MoneyDJ 表格經常會將資料拆成左右兩側：
				// 左側區塊 index: 0(代碼/名稱), 1(買進), 2(賣出), 3(差額)
				if (tds.size() >= 4)
				{
					BranchDetailEntity entityLeft = parseEntity(tds, 0, exchangeDate);
					if (entityLeft != null)
					{
						detailList.add(entityLeft);
					}
				}

				// 右側區塊 index: 4(代碼/名稱), 5(買進), 6(賣出), 7(差額)
				if (tds.size() >= 8)
				{
					BranchDetailEntity entityRight = parseEntity(tds, 4, exchangeDate);
					if (entityRight != null)
					{
						detailList.add(entityRight);
					}
				}
			}

			// 4. 輸出結果預覽
			System.out.println("資料抓取完成，共取得 " + detailList.size() + " 筆紀錄。\n");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			for (int i = 0; i < Math.min(detailList.size(), 5); i++)
			{
				BranchDetailEntity e = detailList.get(i);
				System.out.printf("日期: %s | 代碼: %-20s | 買進: %-6d | 賣出: %-6d | 差額: %-6d%n",
						sdf.format(e.getExchangeDate()), e.getStockId(), e.getBuyColumn(), e.getSellColumn(),
						e.getDiff());
			}

		}
		catch (Exception e)
		{
			System.err.println("抓取或解析過程中發生錯誤: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 從網頁中尋找並解析「資料日期：2026/05/08」
	 */
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

	/**
	 * 從 td 節點中將特定欄位映射至 Entity
	 */
	private BranchDetailEntity parseEntity(Elements tds, int startIndex, Date date)
	{
		String stockId = null;
		String scriptContent = tds.get(startIndex).data().trim();
		Pattern pattern = Pattern.compile("GenLink2stk\\((.*?)\\);");
		Matcher matcher = pattern.matcher(scriptContent);
		if (matcher.find())
		{
			// 這是完整的字串: GenLink2stk('AP00982A','主動群益台灣強棒');
			String fullFunction = matcher.group(0);
			System.out.println("擷取完整函式: " + fullFunction);

			// 這是括號內的參數: 'AP00982A','主動群益台灣強棒'
			String params = matcher.group(1);
			// 如果要進一步拆分參數並存入您的 BranchDetailEntity.stockID
			String[] parts = params.split(",");
			if (parts.length >= 2)
			{
				// 移除單引號得到: AP00982A 主動群益台灣強棒
				stockId = parts[0].replace("'", "");
				stockId = stockId.substring(2);
				String stockName = parts[1].replace("'", "");
				System.out.println("股票代碼: " + stockId);
				System.out.println("股票名稱: " + stockName);
				// 建議存入格式：
				// entity.setStockID(stockId + " " + stockName);
			}
		}
		// 過濾掉空值、佔位符號或最後一列的「合計」
		if (stockId.isEmpty() || stockId.equals("-") || stockId.contains("合計"))
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

	/**
	 * 處理數字字串，移除千分位逗號，並將負號計算在內
	 */
	private int parseVolume(String volumeText)
	{
		try
		{
			// [^\\d-] 意思是：除了數字(\d)和負號(-)以外的字元全部刪除（例如逗號）
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
