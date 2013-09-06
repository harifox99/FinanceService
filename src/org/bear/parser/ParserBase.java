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
	 * 將民國轉成西元年
	 * @param chineseYear
	 * @return
	 */
	public static String convertYear(String chineseYear)
	{
		int intYear = Integer.parseInt(chineseYear) + 1911;
		String year = String.valueOf(intYear);
		return year;
	}
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
	public boolean checkExpectedNum(BasicEntity[] entity, int expectedNum)
	{
		int counter = 0;
		for (int i = 0; i < dataLength; i++)
		{
			if (entity[i].year == null && entity[i].seasons == null && entity[i].stockID == null)
				counter++;
		}
		if ( (dataLength - counter) >= expectedNum)
			return false;
		else
			return true;
		
	}
}
