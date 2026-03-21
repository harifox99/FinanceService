package org.bear.main;

import java.io.BufferedReader;
import java.io.FileReader;

import org.bear.dao.JdbcMacroEconomicDao;
import org.bear.dao.MacroEconomicDao;
import org.bear.util.ParseFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain extends ParseFile
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	MacroEconomicDao dao = (JdbcMacroEconomicDao)context.getBean("macroEconomicDao");
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//Scanner scanner = new Scanner(System.in);        
		//System.out.print("Please input a number: ");        
		//int input = scanner.nextInt();
		//System.out.printf("Oh! I get %d!!", scanner.nextInt());
		//System.out.printf("Oh! I get " + input);
		//TestMain main = new TestMain();
	}
	public TestMain()
	{
		
		String[] month = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String[] year = {"2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"};
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("data/confidence.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ <= 0)
					continue;
				bufferData = readData.split(",");
				for (int i = 0; i < bufferData.length; i++)
				{
					dao.update("Confidence", bufferData[i], year[i] + "-" + month[intRowIndex-1], "-");
					System.out.println(year[i] + "-" + month[intRowIndex-1]);
				}				
			}
			reader.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
