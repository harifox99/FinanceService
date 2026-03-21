package org.bear.main;
import org.bear.parser.SP500NewIndex;
import org.bear.parser.SP500PeParser;
/**
 * 改SP500Index
 * 建置S&P500 Index, PE Ratio, and CRB Index
 * 改SP500Index的URL來擷取S&P500指數
 * @author edward
 *
 */
public class SP500Main 
{
	public static void main(String[] args) 
	{	
		SP500PeParser spParser = new SP500PeParser();
		spParser.getConnection();
		spParser.parse(0);
		SP500NewIndex index = new SP500NewIndex();
		index.update();
		/*
		String url;
		url = "http://www.barchart.com/chart.php?sym=%24CRB&style=technical&template=&p=MO&d=X&sd=02%2F01%2F1993&ed=12%2F31%2F2002&size=L&log=0&t=LINE&v=0&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=&addindicator=&submitted=1&fpage=&txtDate=12%2F31%2F2002#jump";
		CrbParser crb = new CrbParser();
		crb.getConnection(url);
		url = "http://www.barchart.com/chart.php?sym=%24CRB&style=technical&template=&p=MO&d=X&sd=01%2F01%2F2003&ed=04%2F30%2F2013&size=L&log=0&t=LINE&v=0&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=&addindicator=&submitted=1&fpage=&txtDate=12%2F31%2F2002#jump";
		crb.getConnection(url);*/
	}
}
