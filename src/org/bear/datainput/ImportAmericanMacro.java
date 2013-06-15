package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bear.entity.AmericanMacroEntity;
import org.bear.util.ParseFile;

public class ImportAmericanMacro extends ParseFile
{
	public ImportAmericanMacro(){};
	public ArrayList <AmericanMacroEntity> getMacroEconomicList()
	{
		ArrayList <AmericanMacroEntity> list = new ArrayList <AmericanMacroEntity>();
		try
		{
			reader = new BufferedReader(new FileReader("Economic/America.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ <= 2)
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
	private AmericanMacroEntity getMacroEconomicEntity(String[] bufferData)
	{
		AmericanMacroEntity entity = new AmericanMacroEntity();
		try
		{			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = bufferData[0].trim() + "-" + bufferData[1].trim();
			Date date = dateFormat.parse(strDate);
			entity.setYearMonth(date);
			entity.setYear(bufferData[0].trim());
			entity.setDate(strDate);
			entity.setNfp(bufferData[2].trim());
			entity.setSpOpen(bufferData[3].trim());
			entity.setSpHigh(bufferData[4].trim());
			entity.setSpLow(bufferData[5].trim());
			entity.setSpClose(bufferData[6].trim());
			entity.setIndpro(bufferData[7].trim());
			entity.setIndproYoy(bufferData[8].trim());
			entity.setDgOrder(bufferData[9].trim());
			entity.setTcu(bufferData[10].trim());
			entity.setTcuRate(bufferData[11].trim());
			/**/
			double reverse = Double.parseDouble(bufferData[12].trim());
			reverse = 1/reverse;
			entity.setIsRatio(String.valueOf(reverse));
			entity.setAwhman(bufferData[13].trim());
			entity.setAwotman(bufferData[14].trim());
			entity.setUnrate(bufferData[15].trim());
			entity.setUmcsent(bufferData[16].trim());
			entity.setIcsa(bufferData[17].trim());
			entity.setHoust(bufferData[18].trim());
			entity.setCpi(bufferData[19].trim());
			entity.setCrb(bufferData[20].trim());
			entity.setPermitnsa(bufferData[21].trim());		
			entity.setNeworder(bufferData[22].trim());
			entity.setGs10(bufferData[23].trim());
			entity.setGs3m(bufferData[24].trim());
			entity.setBaa(bufferData[25].trim());
			entity.setM1sl(bufferData[26].trim());
			entity.setMzmsl(bufferData[27].trim());
			entity.setSp500(bufferData[28].trim());
			entity.setRsxfs(bufferData[29].trim());
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return entity;
	}
}
