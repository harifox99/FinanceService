package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bear.entity.AmericanMacroPantherEntity;
import org.bear.util.ParseFile;

public class ImportAmericanMacroPanther extends ParseFile
{
	public ImportAmericanMacroPanther(){};
	public ArrayList <AmericanMacroPantherEntity> getMacroEconomicList()
	{
		ArrayList <AmericanMacroPantherEntity> list = new ArrayList <AmericanMacroPantherEntity>();
		try
		{
			reader = new BufferedReader(new FileReader("Economic/America_Panther.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ <= 3)
					continue;
				else
					System.out.println(this.intRowIndex);
				bufferData = readData.split(",");		
				list.add(this.getMacroEconomicEntity(bufferData));
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	private AmericanMacroPantherEntity getMacroEconomicEntity(String[] bufferData)
	{
		AmericanMacroPantherEntity entity = new AmericanMacroPantherEntity();
		try
		{			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = bufferData[0].trim() + "-" + bufferData[1].trim();
			Date date = dateFormat.parse(strDate);
			entity.setYearMonth(date);
			entity.setYear(bufferData[0].trim());
			entity.setDate(strDate);
			/** 總經指標 **/
			//工業生產指數YoY
			entity.setIndproYoy(bufferData[2].trim());
			//零售銷售
			entity.setRsafs(bufferData[3].trim());
			//新屋開工YoY
			entity.setHoust(bufferData[4].trim());
			//耐久財訂單YoY
			entity.setDgOrder(bufferData[5].trim());
			//非國防資本財YoY
			entity.setNeworder(bufferData[6].trim());
			//失業率
			entity.setUnrate(bufferData[7].trim());
			//建築許可YoY
			entity.setPermitnsa(bufferData[8].trim());		
			//工業生產指數
			entity.setIndpro(bufferData[9].trim());
			//非農就業
			entity.setPayems(bufferData[10].trim());
			//存貨銷售比
			double reverse = Double.parseDouble(bufferData[11].trim());
			reverse = 1/reverse;
			entity.setIsRatio(String.valueOf(reverse));
			//每週加班工時
			entity.setAwotman(bufferData[12].trim());
			//消費者信心指數
			entity.setUmcsent(bufferData[13].trim());
			//初領失業救濟金
			entity.setIcsa(bufferData[14].trim());
			/** 貨幣 **/
			//M1
			entity.setM1(bufferData[15].trim());
			//MZM
			entity.setMzm(bufferData[16].trim());
			/** 利率 **/
			//國庫券3個月殖利率
			entity.setTb3ms(bufferData[17].trim());
			//10年期殖利率
			entity.setGs10(bufferData[18].trim());
			//BAA殖利率
			entity.setBaa(bufferData[19].trim());
			//90天商業本票
			entity.setCfp3m(bufferData[20].trim());
			/** 通貨膨脹 **/
			//CPI
			entity.setCpi(bufferData[21].trim());
			//CRB指數
			entity.setCrb(bufferData[22].trim());
			/** 股價指數 **/
			//SP500本益比
			entity.setSp500(bufferData[23].trim());
			entity.setSpOpen(bufferData[24].trim());
			entity.setSpHigh(bufferData[25].trim());
			entity.setSpLow(bufferData[26].trim());
			entity.setSpClose(bufferData[27].trim());
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return entity;
	}
}

