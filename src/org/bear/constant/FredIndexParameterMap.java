package org.bear.constant;

import java.util.HashMap;

public class FredIndexParameterMap {
	/* 工業生產指數YoY,零售銷售,耐久財訂單,非國防非航空器耐久財,商業存貨,建築許可YoY */
	public static final String[] AMERICA_YOY_LIST = {"INDPROYoY", "RSAFS", "DGORDER", "NEWORDER", "BUSINV", "CPIAUCSL", "PERMITNSAYoY"};
	public static final HashMap<String, String> CPIAUCSL = new HashMap<String, String>();
		static
		{
			CPIAUCSL.put("units", "pc1");
			CPIAUCSL.put("frequency", "m");
		}	
	/*********************/
		
	/* 新屋開工數,失業率 ,建築許可,工業生產指數,存貨銷售比 ,每週加班工時,消費者信心指數,初領失業救濟金 */	
	public static final String[] AMERICA_LIST = {"HOUST","UNRATE","PERMITNSA","INDPRO","ISRATIO","AWOTMAN","UMCSENT","ICSA"};
	public static final HashMap<String, String> HOUST = new HashMap<String, String>();
	static
	{
		HOUST.put("units", "lin");
		HOUST.put("frequency", "m");
	}	
	/*********************/
	
	/* 非農就業 */
	public static final String[] AMERICA_PAYEMS_LIST = {"PAYEMS"};	
	public static final HashMap<String, String> PAYEMS = new HashMap<String, String>();
	static
	{
		PAYEMS.put("units", "ch1");
		PAYEMS.put("frequency", "m");
	}	  
	/*********************/
	
	/* 初領失業救濟金 */	
	public static final String[] AMERICA_ICSA_LIST = {"ICSA"};	
	public static final HashMap<String, String> ICSA = new HashMap<String, String>();
	static
	{
		ICSA.put("units", "lin");
		ICSA.put("frequency", "m");
		ICSA.put("aggregation_method", "eop");
	}	
	/*********************/
	
	/* 貨幣 */
	public static final String[] AMERICA_MONEY_LIST = {"M1SL","MZMSL"};
	public static final HashMap<String, String> MONEY = new HashMap<String, String>();
	static
	{
		MONEY.put("units", "pc1");
		MONEY.put("frequency", "m");
	}	
	/*********************/
	
	/* 3個月殖利率,10年期殖利率,BAA殖利率,90天商業本票, 高收益債利差 */
	public static final String[] AMERICA_INTEREST_LIST = {"TB3MS", "GS10", "BAA", "CPF3M", "BAMLH0A0HYM2"};
	public static final HashMap<String, String> INTEREST = new HashMap<String, String>();
		static
		{
			INTEREST.put("units", "lin");
			INTEREST.put("frequency", "m");
		}	 
	/*********************/
		
	/* VIX恐慌指數 */
	public static final String[] AMERICA_VIX_LIST = {"VIXCLS"};
	public static final HashMap<String, String> VIX = new HashMap<String, String>();
		static
		{
			VIX.put("units", "lin");
			VIX.put("frequency", "d");
		}	 
		/*********************/	
		
	/* 月份轉換 */
	public static final HashMap<String, String> MONTH = new HashMap<String, String>();
	static
	{
		MONTH.put("Jan", "01");
		MONTH.put("Feb", "02");
		MONTH.put("Mar", "03");
		MONTH.put("Apr", "04");
		MONTH.put("May", "05");
		MONTH.put("Jun", "06");
		MONTH.put("Jul", "07");
		MONTH.put("Aug", "08");
		MONTH.put("Sep", "09");
		MONTH.put("Oct", "10");
		MONTH.put("Nov", "11");
		MONTH.put("Dec", "12");
	}		
	/*********************/
	
	/* ISM,
	 * ISM New Orders,
	 * ISM Non-manufacturing: Business Activity Index,
	 * Inventories,  
	 * Production,
	 * Employment,
	 * Deliveries 
	 * */
	public static final String[] AMERICA_PMI_LIST = {"NAPM", "NAPMNOI", "NMFBAI", "NAPMII", "NAPMPI", "NAPMEI" ,"NAPMSDI"};
	public static final String[] PMI_DB_COLUMN = {"PMI", "NewOrders", "BAI", "Inventories", "Production", "Employment ", "Deliveries"};
	public static final HashMap<String, String> PMI = new HashMap<String, String>();
		static
		{
			PMI.put("units", "lin");
			PMI.put("frequency", "m");
		}	 
}
