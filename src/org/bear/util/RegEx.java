package org.bear.util;
import java.util.regex.*;
public class RegEx 
{
	boolean isMatch;
	Pattern pattern;
	public RegEx(String patternString, String matchString)
	{
		pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(matchString);
		isMatch = matcher.find();
	}
	public boolean isMatch()
	{
		return isMatch;
	}
}
