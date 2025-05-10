package org.bear.massData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bear.dao.RevenueDao;
import org.bear.entity.RevenueEntity;
import org.bear.util.HttpUtil;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 大批營收更新
 * @author bear
 *
 */
public class UpdateRevenue 
{
	String url = "https://mopsov.twse.com.tw/server-java/FileDownLoad";
	String parameters;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	RevenueDao dao = (RevenueDao)context.getBean("revenueDao");
	public List<RevenueEntity> entityList = new ArrayList<RevenueEntity>();	
	public void getData()
	{
		String url = "https://mopsov.twse.com.tw/server-java/FileDownLoad";
		//String parameters = "step=9&functionName=show_file2&filePath=%2Ft21%2Fsii%2F&fileName=t21sc03_113_10.csv";				
		HttpUtil httpUtil = new HttpUtil();		
		List<String> listData = httpUtil.httpPostList(url, parameters, "UTF-8");
		try
		{
			for (int i = 1; i < listData.size(); i++)
			{
				String[] data = listData.get(i).split("\",\"");
				RevenueEntity entity = new RevenueEntity();
				String[] dateString = data[1].split("/");
				String year = StringUtil.convertYear(dateString[0]);
				String month = StringUtil.addZeroMonth(dateString[1]);
				entity.setStockID(data[2]);
				entity.setRevenue(Integer.parseInt(data[5]));
				entity.setLastRevenue(Integer.parseInt(data[7]));
				entity.setAccumulation(Long.parseLong(data[10]));
				entity.setLastAccumulation(Long.parseLong(data[11]));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
				Date date = dateFormat.parse(year + "-" + month);
				entity.setYearMonth(date);
				entityList.add(entity);	
			}
			dao.insertBatch(entityList);				
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		
	}
	public static void main(String[] args)
	{
		//上市
		UpdateRevenue revenueTwse = new UpdateRevenue();
		revenueTwse.setParameters("step=9&functionName=show_file2&filePath=%2Ft21%2Fsii%2F&fileName=t21sc03_114_4.csv");
		revenueTwse.getData();
		//上櫃
		UpdateRevenue revenueOtc = new UpdateRevenue();
		revenueOtc.setParameters("step=9&functionName=show_file2&filePath=%2Ft21%2Fotc%2F&fileName=t21sc03_114_4.csv");
		revenueOtc.getData();
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public RevenueDao getDao() {
		return dao;
	}
	public void setDao(RevenueDao dao) {
		this.dao = dao;
	}	
}
