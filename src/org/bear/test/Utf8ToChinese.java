package org.bear.test;

import java.net.URLDecoder;
/**
 * 把被編碼的中文或特殊字元還原
 * @author edward
 *
 */
public class Utf8ToChinese 
{
	public static void main(String[] args) throws Exception 
	{
		String strUtf8 = "13%2C25%2C33%2C34%2C35%2C40";
		String strChinese = null;
		try 
		{
			//strChinese = new String(strUtf8.getBytes("UTF-8"), "utf-8");
			strChinese = URLDecoder.decode(strUtf8, "Big5");
			//strChinese = new String(strUtf8.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(strChinese);
			//strChinese= org.apache.commons.lang.StringEscapeUtils.unescapeJava(bbc);
			System.out.println(strChinese);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			strChinese = "decode error";
		}
		System.out.println(strChinese);
		
	}
}