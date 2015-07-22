package org.bear.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat; 
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date; 
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class DateTimeFactory {

	/**
	 * 將目前的系統時間轉換為各種形式的 String
	 * @param type 
	 *  [0]:yyyy-MM-dd hh
	 *  [1]:yyyy-MM-dd hh:mm
	 *  [2]:MM/dd/yyyy
	 *  [3]:yyyy-MM-dd
	 *  [4]:yyyy/MM/dd hh:mm:ss.SSSSS
	 *  [5]:不做轉換，直接取得時間的long int數
	 *  [6]:hh:mm
	 *  [7]:yyyyMMdd
	 *  [8]:yyyMMdd(yyy為民國年)
	 *  [9]:yyyyMMddhh
	 *  [10]:yyyyMMddhhmm
	 * @return
	 */
	public String getCurrentDateTimetoString(int type) {
		Date date = new Date();
		DateFormat dateFormat = null;
		String result = null;
		
		switch (type) {
		case 0:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			result = dateFormat.format(date).toString();
			break;
		case 1:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			result = dateFormat.format(date).toString();
			break;
		case 2:
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			result = dateFormat.format(date).toString();
			break;
		case 3:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result = dateFormat.format(date).toString();
			break;
		case 4:
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSS");
			result = dateFormat.format(date).toString();
			break;
		case 5:
			result = String.valueOf(date.getTime());
			break;
		case 6:
			dateFormat = new SimpleDateFormat("HH:mm");
			result = dateFormat.format(date).toString();
			break;
		case 7:
			dateFormat = new SimpleDateFormat("yyyyMMdd");
			result = dateFormat.format(date).toString();
			break;
		case 8:
			dateFormat = new SimpleDateFormat("yyyyMMdd");
		    SimpleDateFormat df2 = new SimpleDateFormat("MMdd");
		    Calendar cal = Calendar.getInstance();
		    String tmp = dateFormat.format(date).toString();
		    try 
		    {
				cal.setTime(dateFormat.parse(tmp));
				cal.add(Calendar.YEAR,-1911);
				result = Integer.toString(cal.get(Calendar.YEAR)) + df2.format(cal.getTime());
			} 
		    catch (ParseException e) {				
				e.printStackTrace();
			}
		    
			break;
		case 9:
			dateFormat = new SimpleDateFormat("yyyyMMddHH");
			result = dateFormat.format(date).toString();
			break;
		case 10:
			dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			result = dateFormat.format(date).toString();
			break;
		case 11:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = dateFormat.format(date).toString();
			break;
		}		
		return result;
	}
	
	public Date getCurrentDateTimetoString2(int type) 
	{
		//Date date = new Date();
		//DateFormat dateFormat = null;
		Date result = null;
		
		switch (type) 
		{
			case 1:
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");         
				String str= getCurrentDateTimetoString(type);
			    try 
			    {
			    	result = format1.parse(str);
				} 
			    catch (ParseException e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			    break;
			case 3:
				
				format1 = new SimpleDateFormat("yyyy-MM-dd");         
				str= getCurrentDateTimetoString(3);
				str =str + " 00:00";
			    try 
			    {
			    	result = format1.parse(str);
					
			    } 
			    catch (ParseException e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   				
			    break;
		}		
		return result;
	}
	
	public int getTodayis() 
	{
		int ret = -1;
		String[] ids = TimeZone.getAvailableIDs(+8 * 60 * 60 * 1000);//Taiwan Time is GMT+8
		SimpleTimeZone pdt = new SimpleTimeZone(+8 * 60 * 60 * 1000, ids[0]);
		Calendar calendar = new GregorianCalendar(pdt);
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		ret = calendar.get(Calendar.DAY_OF_MONTH);
		return ret;
	}
    public int getHourOfdayis() 
    {
    	int ret = -1;
		String[] ids = TimeZone.getAvailableIDs(+8 * 60 * 60 * 1000);//Taiwan Time is GMT+8
		SimpleTimeZone pdt = new SimpleTimeZone(+8 * 60 * 60 * 1000, ids[0]);
		Calendar calendar = new GregorianCalendar(pdt);
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		ret = calendar.get(Calendar.HOUR_OF_DAY);
		return ret;
	}
    public int getMinis() 
    {
    	int ret = -1;
		String[] ids = TimeZone.getAvailableIDs(+8 * 60 * 60 * 1000);//Taiwan Time is GMT+8
		SimpleTimeZone pdt = new SimpleTimeZone(+8 * 60 * 60 * 1000, ids[0]);
		Calendar calendar = new GregorianCalendar(pdt);
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		ret=calendar.get(Calendar.MINUTE);
		return ret;
	}
	public Date changeStrToDate(int type,String orgTimeStr)
	{
		//Date date = new Date();
		//DateFormat dateFormat = null;
		Date result = null;
		if (orgTimeStr.length()<=0) 
		{
			return null;
		}
		switch (type) 
		{
			case 1:
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");         
				String str= orgTimeStr;
			    try 
			    {
			    	result = format1.parse(str);
				} 
			    catch (ParseException e) 
			    {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			    break;
			case 2:
				format1 = new SimpleDateFormat("MM/dd/yyyy");         
				str= orgTimeStr;
			    try 
			    {
			    	result = format1.parse(str);
				}
			    catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			    break;	 
			case 3:
				format1 = new SimpleDateFormat("yyyy-MM-dd");         
				str= orgTimeStr;
				try 
				{
					result = format1.parse(str);
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			
				break;
			case 4:
				format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSS");         
				str= orgTimeStr;
			    try 
			    {
			    	result = format1.parse(str);
				} 
			    catch (ParseException e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   			
			    break;	 
			case 5:
				format1 = new SimpleDateFormat("yyyy/MM/dd");         
				str= orgTimeStr;
			    try 
			    {
			    	result = format1.parse(str);
				} 
			    catch (ParseException e) 
			    {
			    	// TODO Auto-generated catch block	
			    	e.printStackTrace();
				}   				
				break;	
			case 6:
				format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");         
				str= orgTimeStr;
				try 
				{
					result = format1.parse(str);
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   			
				break;
			case 7:
				format1 = new SimpleDateFormat("yyyyMMdd");         
				str= orgTimeStr;
				try 
				{
					result = format1.parse(str);
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
				break;
			case 8:
				format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
				str= orgTimeStr;
				try 
				{
					result = format1.parse(str);
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
				break;
			default:
				break;	 	 
		}		
		return result;
	}
	
	/**
	 * 將輸入的時間轉換為各種形式的 String
	 * @param date
	 * @param type 
	 *  [0]:yyyy-MM-dd hh
	 *  [1]:yyyy-MM-dd hh:mm
	 *  [2]:MM/dd/yyyy
	 *  [3]:yyyy-MM-dd
	 *  [4]:yyyy/MM/dd hh:mm:ss.SSSSS
	 *  [5]:不做轉換，直接取得時間的long int數
	 *  [6]:yyyyMMdd
	 *  [7]:民國年MMdd
	 *  [8]:yyyy-MM-dd hh:mm:ss
	 *  [9]:yyyyMMddhhmmss
	 * @return
	 * @author lsw
	 */
	public String getDateTimetoString(Date date, int type) {
		DateFormat dateFormat = null;
		String result = null;
		
		switch (type) {
		case 0:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			result = dateFormat.format(date).toString();
			break;
		case 1:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			result = dateFormat.format(date).toString();
			break;
		case 2:
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			result = dateFormat.format(date).toString();
			break;
		case 3:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result = dateFormat.format(date).toString();
			break;
		case 4:
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSS");
			result = dateFormat.format(date).toString();
			break;
		case 5:
			result = String.valueOf(date.getTime());
			break;
		case 6:
			dateFormat = new SimpleDateFormat("yyyyMMdd");
			result = dateFormat.format(date).toString();
			break;
		case 7:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String tmpDate = dateFormat.format(date).toString();
			String[] okCur = tmpDate.split("-");
			result = Integer.parseInt(okCur[0])-1911 + okCur[1] + okCur[2];
			break;
		case 8:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = dateFormat.format(date).toString();
			break;
		case 9:
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			result = dateFormat.format(date).toString();
			break;
		}		
		return result;
	}
	
	/**
	 * 格式化時間幾天後幾天前
	 * 
	 * @param typeis
	 * @param TorY
	 *            1: 20040812 2: 2004-08-12 12:31 3: 2004/08/12 4 08/12/2004 5
	 *            2004-01-01 TorY : 0 nothing 1:tomorrow 2:yesterday ; >2 幾天後 ;
	 *            <0 幾天前
	 * @return Time Format
	 */
	public String cjyTimeformat(int typeis, int TorY) {
		String rettime = "", year_f = "", mon_f = "", day_f = "", hour_f = "", minute_f = "";
		Calendar rightNow = Calendar.getInstance();

		if (TorY == 1) {
			rightNow.add(Calendar.DATE, 1);
		}

		if (TorY == 2) {
			rightNow.add(Calendar.DATE, -1);
		}
		if (TorY > 2) {
			rightNow.add(Calendar.DATE, TorY);
		}
		if (TorY < 0) {
			rightNow.add(Calendar.DATE, TorY);
		}

		int yearis = rightNow.get(Calendar.YEAR);
		int mon = rightNow.get(Calendar.MONTH) + 1;
		int dayis = rightNow.get(Calendar.DAY_OF_MONTH);
		int houris = rightNow.get(Calendar.HOUR_OF_DAY);
		int minute = rightNow.get(Calendar.MINUTE);
		//int secound = rightNow.get(Calendar.SECOND);

		year_f = String.valueOf(yearis);
		mon_f = String.valueOf(mon);
		hour_f = String.valueOf(houris);
		minute_f = String.valueOf(minute);
		day_f = String.valueOf(dayis);

		if (mon_f.length() < 2) {
			mon_f = "0" + mon_f;
		}
		if (day_f.length() < 2) {
			day_f = "0" + day_f;
		}

		if (hour_f.length() < 2) {
			hour_f = "0" + hour_f;
		}

		if (minute_f.length() < 2) {
			minute_f = "0" + minute_f;
		}
		if (typeis == 1) {
			rettime = year_f + mon_f + day_f;
		}
		if (typeis == 2) {
			rettime = year_f + "-" + mon_f + "-" + day_f + " " + hour_f + ":"
					+ minute_f;
		}
		if (typeis == 3) {
			rettime = year_f + "/" + mon_f + "/" + day_f;
		}
		if (typeis == 4) {
			rettime = mon_f + "/" + day_f + "/" + year_f;
		}

		if (typeis == 5) {
			rettime = year_f + "-" + mon_f + "-" + day_f;
		}
		return rettime;
	} // end of cjyTimeformat
	
	/**
	 * 計算前後幾天的日期
	 * @param date
	 * @param day
	 * @return Date
	 * @author lsw
	 */
	
	public Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	public Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	public Date addHour(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.HOUR,hours);
		return calendar.getTime();
	}
	public Date addMin(Date date, int mins) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE,mins);
		return calendar.getTime();
	}
	/**
	 * 計算相差天數
	 * @param date1
	 * @param date2
	 * @param outputType 1:seconds, 2:minutes, 3:hours, 4:days
	 * @return long
	 * @author lsw
	 */
	public long dateDifferent(Date date1, Date date2, int outputType) {
		long result = 0;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		
		long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
		
		switch (outputType) {
		case 1:
			result = diff/1000;
			break;
		case 2:
			result = diff/(60 * 1000);
			break;
		case 3:
			result = diff/(60 * 60 * 1000);		    
			break;
		case 4:
			result = diff/(24 * 60 * 60 * 1000);	    
			break;
		default:
			result = diff/1000;
			break;
		}
		
		return result;
	}
	
	/**
	 * 依照給定的月份,抓出當月起始Date
	 * @param type : reference to changeStrToDate()
	 * @param qYear
	 * @param qMon
	 * @return Date(需求日期格式)
	 * @author Jane Liu (only implement type=1,2)
	 */
	public Date getMonthFirstDate(int type, int qYear, int qMon) {
		String qDateBeginStr = "";
		if (qMon>12) {
			qYear++; qMon=1;
		}
		switch (type) {
		case 1:
			qDateBeginStr = qYear +"-" + qMon + "-01 00:00";
			break;
		case 2:
			qDateBeginStr = qMon +"/01/"+ qYear;
			break;
		}
		return this.changeStrToDate(type, qDateBeginStr);
	}
	/**
	 * 判斷是否是假日
	 * @return
	 */
	public boolean isHoliday(Date date)
	{
		SimpleDateFormat date2DayFormat = new SimpleDateFormat("E");
	    String day = date2DayFormat.format(date);
	    if (day.equals("星期六") || day.equals("星期日"))
	    	return true;
	    else
	    	return false;
	}
	
	public static void main(String[] args) 
	{
		DateTimeFactory dateTimeFactory = new DateTimeFactory();
		Date date = new Date();
		Date newDate = dateTimeFactory.addDay(date, -400);
		
		System.out.println("old date: " + dateTimeFactory.getDateTimetoString(date, 9));
		System.out.println("new date: " + dateTimeFactory.getDateTimetoString(newDate, 3));
		System.out.println("diff day: " + dateTimeFactory.dateDifferent(date, newDate, 4));
		System.out.println("old date: " + dateTimeFactory.getCurrentDateTimetoString(8));
		System.out.println("date: " + dateTimeFactory.getCurrentDateTimetoString(9));
	}

}
