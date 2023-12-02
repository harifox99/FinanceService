package org.bear.constant;
import java.util.Arrays;
import java.util.List;
public class FinancialReport 
{
	public static int expectedNum = 3;
	public static int sleepTime = 1000;
	//證交所，櫃買三大法人買賣超前N(30)名
	public static int maxNumber = 30;
	//資料異常的奇怪股票
	public static List<String> skipStockId = Arrays.asList("2432","6763");
}
