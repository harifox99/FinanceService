package org.bear.datainput;
import java.io.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.bear.entity.*;
import org.bear.util.ParseFile;
/**
 * @author edward
 * 儲存台灣總體經濟指標
 */
public class ImportMacroEconomic extends ParseFile
{
	public ImportMacroEconomic(){};
	public ArrayList <MacroEconomicEntity> getMacroEconomicList()
	{
		ArrayList <MacroEconomicEntity> list = new ArrayList <MacroEconomicEntity>();
		try
		{
			reader = new BufferedReader(new FileReader("data/Macro.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ == 0)
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
	private MacroEconomicEntity getMacroEconomicEntity(String[] bufferData)
	{
		MacroEconomicEntity entity = new MacroEconomicEntity();
		try
		{			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = bufferData[0].trim() + "-" + bufferData[1].trim();
			Date date = dateFormat.parse(strDate);
			entity.setYearMonth(date);
			entity.setYear(bufferData[0].trim());
			entity.setMonth(bufferData[1].trim());
			entity.setTwseOpen(bufferData[2].trim());
			entity.setTwseHigh(bufferData[3].trim());
			entity.setTwseLow(bufferData[4].trim());
			entity.setTwseClose(bufferData[5].trim());
			entity.setM1bAverage(bufferData[6].trim());
			entity.setM1bEnd(bufferData[7].trim());
			entity.setM2Average(bufferData[8].trim());
			entity.setM2End(bufferData[9].trim());
			entity.setGeneralIndex(bufferData[10].trim());
			entity.setLightSignal(bufferData[14].trim());
			entity.setInventoryIndex(bufferData[11].trim());
			entity.setOverworkTime(bufferData[12].trim());
			entity.setSemiIndex(bufferData[13].trim());
			entity.setStockMoneyIndex(Integer.parseInt(bufferData[15].trim()));
			entity.setSixMonthLeadIndex(bufferData[16].trim());
			entity.setDemandDeposits(bufferData[17].trim());
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return entity;
	}
}
