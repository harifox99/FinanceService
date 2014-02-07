package org.bear.constant;

import java.util.HashMap;

public class CbcIndexConstant 
{
	/* ¤é´Á */
	public static final HashMap<String, Integer> MONTH_HASH = new HashMap<String, Integer>();
	static
	{
		MONTH_HASH.put("1993M02", 380);
		MONTH_HASH.put("2013M01", 619);
		MONTH_HASH.put("2013M03", 621);
		MONTH_HASH.put("2013M04", 622);
		MONTH_HASH.put("2013M07", 625);		
		MONTH_HASH.put("2013M08", 626);		
		MONTH_HASH.put("2013M09", 627);		
		MONTH_HASH.put("2013M10", 628);	
		MONTH_HASH.put("2013M11", 629);		
		MONTH_HASH.put("2013M12", 630);		
	}	
	
	/* ³f¹ô */
	public static final String[] MONEY = {"M1bAverage", "M1bEnd", "M2Average", "M2End"};
	
}
