package org.bear.parser;

import java.io.BufferedWriter;
import java.util.List;
import net.htmlparser.jericho.Element;
import org.bear.entity.BasicEntity;
import org.bear.util.AccountTitle;
/**
 * @author edward
 * Parser的基礎資料
 */
public class ParserBase 
{
	//Parsing後的Table List資料
	public List<Element> elementList;
	//股票代碼
	public String stockID;
	//Log File
	public BufferedWriter writer;
	//儲存資料的筆數
	public final int dataLength = 8;
	//辨別會計科目
	public AccountTitle title;
	//判斷合併報表資料是否足夠
	int expectedNum;
	//擷取合併報表=true, 否則=false
	boolean isCombined;
	/**
	 * 轉換季度顯示方法
	 * @param season
	 * @return
	 */
	public String convertMonth(String season)
	{
		if (season.equals("1Q"))
			return "01";
		else if (season.equals("2Q"))
			return "02";
		else if (season.equals("3Q"))
			return "03";
		else if (season.equals("4Q"))
			return "04";
		else
			return "05";
	}
	/**
	 * 檢查合併報表資料是否足夠，若不足，則擷取單一報表
	 * @return
	 */
	public boolean checkExpectedNum(BasicEntity[] entity, int expectedNum, String[] years, String[] seasons)
	{
		int counter = 0;
		for (int i = 0; i < dataLength; i++)
		{
			if (entity[i].year == null && entity[i].seasons == null && entity[i].stockID == null)
			{
				counter++;
			}
			else
			{
				boolean isFind = false;
				for (int j = 0; j < seasons.length; j++)
				{
					if (isFind)
						break;
					for (int k = 0; k < years.length; k++)
					{
						if (entity[i].year.equals(years[k]) && entity[i].seasons.equals(seasons[j]))
						{
							//找到年份相符合的資料，不做處置
							isFind = true;
							break;
						}
					}
				}
				if (isFind == false)
					counter++;
			}
		}
		if ( (dataLength - counter) >= expectedNum)
			return false;
		else
			return true;
		
	}
}
