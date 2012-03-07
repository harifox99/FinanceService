package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bear.entity.PMIndexEntity;
import org.bear.util.ParseFile;

public class ImportPMIndex extends ParseFile {

	public ImportPMIndex(){};
	public ArrayList <PMIndexEntity> getPMIndexList()
	{
		ArrayList <PMIndexEntity> list = new ArrayList <PMIndexEntity>();
		try
		{
			reader = new BufferedReader(new FileReader("Economic/ISM.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ <= 0)
					continue;
				else
					System.out.println(this.intRowIndex);
				bufferData = readData.split(",");		
				list.add(this.getPMIndexEntity(bufferData));
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	private PMIndexEntity getPMIndexEntity(String[] bufferData)
	{
		PMIndexEntity entity = new PMIndexEntity();
		try
		{			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = bufferData[0].trim() + "-" + bufferData[1].trim();
			Date date = dateFormat.parse(strDate);
			entity.setYearMonth(date);
			entity.setYear(bufferData[0].trim());
			entity.setDate(strDate);
			entity.setSpOpen(bufferData[2].trim());
			entity.setSpHigh(bufferData[3].trim());
			entity.setSpLow(bufferData[4].trim());
			entity.setSpClose(bufferData[5].trim());
			entity.setNewOrders(bufferData[6].trim());
			entity.setProduction(bufferData[7].trim());
			entity.setEmployment(bufferData[8].trim());
			entity.setDeliveries(bufferData[9].trim());
			entity.setInventories(bufferData[10].trim());
			entity.setPmi(bufferData[11].trim());
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return entity;
	}
}
