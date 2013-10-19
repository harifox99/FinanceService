package org.bear.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.PMIndexDao;
import org.bear.datainput.ImportPMIndex;
import org.bear.entity.PMIndexEntity;
import org.bear.parser.fred.FredEconomicUrl;
import org.bear.util.ParseFile;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 建置PMI(也是從FRED來)
 * @author edward
 *
 */
public class BuildPMIndex extends ParseFile {

	/**
	 * @param args
	 */
	List <PMIndexEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//new BuildPMIndex().insertBatch();
		FredEconomicUrl type = new FredEconomicUrl();
		new BuildPMIndex().insertByFredApi(FredIndexParameterMap.AMERICA_PMI_LIST, type, FredIndexParameterMap.PMI);
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");
		list = new ImportPMIndex().getPMIndexList();
		dao.insertBatch(list);
	}
	public List<PMIndexEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");	
		list = dao.findAll();		
		return list;
	}
	public List<PMIndexEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");	
		list = dao.findByDate(startTime, endTime);		
		convert2MinusFifty();
		return list;
	}
	public void insertByFredApi(String[] catagoryList, FredEconomicUrl catagory, HashMap<String, String> parameterMap)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");
		String key = "date";
		String value = "value";
		list = new ArrayList<PMIndexEntity>();
		try
		{
			for (int i = 0; i < catagoryList.length; i++)
			{
				catagory.setUrlString(catagoryList[i], catagory.observation_init, parameterMap);
				HashMap<String, String> map = catagory.getContent(key, value);
				if (i < 1)
				{
					for (Map.Entry<String,String> entry : map.entrySet()) 
					{
						PMIndexEntity entity = new PMIndexEntity();
						String dateArray[] = entry.getKey().split("-");
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = dateFormat.parse(entry.getKey());
						entity.setDate(entry.getKey());
						entity.setYear(dateArray[0]);
						entity.setYearMonth(date);
						entity.setPmi(entry.getValue());
						entity.setBaseline("0");
						list.add(entity);
					}
					dao.insertBatch(list);
				}
				else
				{
					for (Map.Entry<String,String> entry : map.entrySet()) 
					{				
						int result = dao.update(FredIndexParameterMap.PMI_DB_COLUMN[i], entry.getValue(), entry.getKey());
					    if (result <= 0)
					    {					    	
					    	System.out.println(entry.getValue() + ", " + entry.getKey());
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
	private void convert2MinusFifty()
	{
		double pmiStandard = 50;
		
		for (int i = 0; i < list.size(); i++)
		{
			double result;
			result = StringUtil.sub(Double.parseDouble(list.get(i).getDeliveries()), pmiStandard);
			list.get(i).setDeliveries(String.valueOf(result));
			
			result = StringUtil.sub(Double.parseDouble(list.get(i).getEmployment()), pmiStandard);
			list.get(i).setEmployment(String.valueOf(result));
			
			result = StringUtil.sub(Double.parseDouble(list.get(i).getInventories()), pmiStandard);
			list.get(i).setInventories(String.valueOf(result));
			
			result = StringUtil.sub(Double.parseDouble(list.get(i).getNewOrders()), pmiStandard);
			list.get(i).setNewOrders(String.valueOf(result));
						
			result = StringUtil.sub(Double.parseDouble(list.get(i).getPmi()), pmiStandard);
			list.get(i).setPmi(String.valueOf(result));
						
			result = StringUtil.sub(Double.parseDouble(list.get(i).getProduction()), pmiStandard);
			list.get(i).setProduction(String.valueOf(result));
		}
	}
}
