package org.bear.main;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.AmericanMacroPantherDao;
import org.bear.datainput.ImportAmericanMacroPanther;
import org.bear.entity.AmericanMacroPantherEntity;
import org.bear.parser.fred.FredEconomicUrl;
import org.bear.util.ParseFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 美國總經指標，記得改catagory.observation_init的起始值
 * @author edward
 *
 */
public class BuildAmericanMacroData extends ParseFile
{
	/**
	 * 改catagory.observation_init的起始值
	 * @param args
	 */
	List <AmericanMacroPantherEntity> list;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//new BuildAmericanMacroData().insertBatch();
		//new BuildMacroEconomicData().findAllList();
		//記得改FredEconomicUrl的observation_init
		FredEconomicUrl type = new FredEconomicUrl();
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_LIST, type, FredIndexParameterMap.HOUST);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_PAYEMS_LIST, type, FredIndexParameterMap.PAYEMS);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_ICSA_LIST, type, FredIndexParameterMap.ICSA);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_INTEREST_LIST, type, FredIndexParameterMap.INTEREST);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_YOY_LIST, type, FredIndexParameterMap.CPIAUCSL);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_MONEY_LIST, type, FredIndexParameterMap.MONEY);
		new BuildAmericanMacroData().insertByFredApi(FredIndexParameterMap.AMERICA_VIX_LIST, type, FredIndexParameterMap.VIX);
	}
	public void insertByFredApi(String[] catagoryList, FredEconomicUrl catagory, HashMap<String, String> parameterMap)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
		String key = "date";
		String value = "value";
		list = new ArrayList<AmericanMacroPantherEntity>();
		try
		{
			for (int i = 0; i < catagoryList.length; i++)
			{
				catagory.setUrlString(catagoryList[i], catagory.observation_init, parameterMap);
				/*
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
				String strDate = bufferData[0].trim() + "-" + bufferData[1].trim();
				Date date = dateFormat.parse(strDate);
				entity.setYearMonth(date);
				entity.setYear(bufferData[0].trim());
				entity.setDate(strDate);*/
				HashMap<String, String> map = catagory.getContent(key, value);
				//建第一欄資料與日期
				if (catagoryList[i].equals("HOUST"))
				{
					for (Map.Entry<String,String> entry : map.entrySet()) 
					{
						AmericanMacroPantherEntity entity = new AmericanMacroPantherEntity();
						String dateArray[] = entry.getKey().split("-");
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = dateFormat.parse(entry.getKey());
						entity.setDate(entry.getKey());
						entity.setYear(dateArray[0]);
						entity.setYearMonth(date);
						entity.setHoust(entry.getValue());
						entity.setBaseline("0");
						list.add(entity);
					}
					dao.insertBatch(list);
				}
				else if (catagoryList[i].equals("VIXCLS"))
					this.saveVixData(map, dao);
				//之後用日期當Key，更新其他欄位資料
				else
				{					
					for (Map.Entry<String,String> entry : map.entrySet()) 
					{				
						int result;						
						if (catagoryList[i].equals("CPIAUCSL"))
							result = dao.update("CPI", entry.getValue(), entry.getKey());						
						else
							result = dao.update(catagoryList[i], entry.getValue(), entry.getKey());
					    if (result <= 0)
					    {					    	
					    	System.out.println("Update Error! " + entry.getValue() + ", " + entry.getKey());
					    	//System.out.println("Abnormal Terminating!");
					    	//System.exit(1);
					    }
					}
					
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
		list = new ImportAmericanMacroPanther().getMacroEconomicList();
		dao.insertBatch(list);
	}
	public List<AmericanMacroPantherEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");	
		list = dao.findAll();		
		return list;
	}
	public List<AmericanMacroPantherEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");	
		list = dao.findByDate(startTime, endTime);		
		return list;
	}
	private void saveVixData(HashMap<String, String> map, AmericanMacroPantherDao dao)
	{
		HashMap<String, String> vixHigh = new HashMap<String, String>();
		HashMap<String, String> vixLow = new HashMap<String, String>();
		for (Map.Entry<String,String> entry : map.entrySet()) 
		{
			String key = entry.getKey().substring(0, 7);
			String data = entry.getValue();
			if (data.equals("."))
				continue;
			if (vixHigh.get(key) == null)
			{
				vixHigh.put(key, data);
			}
			else if (Double.parseDouble(data) > Double.parseDouble(vixHigh.get(key)))
			{
				vixHigh.put(key, data);
			}
			if (vixLow.get(key) == null)
			{
				vixLow.put(key, data);
			}
			else if (Double.parseDouble(data) < Double.parseDouble(vixLow.get(key)))
			{							
				vixLow.put(key, data);
			}
		}
		for (Map.Entry<String,String> entry : vixHigh.entrySet()) 
		{
			dao.update("VIXHigh", entry.getValue(), entry.getKey() + "-01");	
		}
		for (Map.Entry<String,String> entry : vixLow.entrySet()) 
		{
			dao.update("vixLow", entry.getValue(), entry.getKey() + "-01");	
		}
	}
}
