package org.bear.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * ¦Û»sString Utility
 * @author edward
 *
 */
public class StringUtil 
{
	public static String eraseSpecialChar(String string)
	{
		if (string.contains("$"))
		{
			string = string.replace("$", "");
		}
		if (string.contains("("))
		{
			string = string.replace("(", " ");
		}
		if (string.endsWith(")"))
		{
			string = string.replace(")", "");
			string = "-" + string;
		}
		if (string.contains(","))
		{
			string = string.replace(",", "");
		}
		if (string.contains("."))
		{
			string = string.replace(".", "");
		}
		if (string.contains("¡@"))
		{
			string = string.replace("¡@", "");
		}
		return string;
	}
	public static double setPointLength(double number)
	{
		NumberFormat formatter = new DecimalFormat("##.##");
		number = Double.parseDouble(formatter.format(number));
		return number;
	}
	public static double setPointLength(double number, int length)
	{
		String decimal = "##.";
		for (int i = 0; i < length; i++)
		{
			decimal = decimal + "#";
		}
		NumberFormat formatter = new DecimalFormat(decimal);
		number = Double.parseDouble(formatter.format(number));
		return number;
	}
}
