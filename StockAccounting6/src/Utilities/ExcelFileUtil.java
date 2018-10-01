package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{	
	Workbook wb;
	CellStyle style;
	Font font;
	
	// It will load all the excel sheet & it will instantiate that particular workbook
	public ExcelFileUtil() throws Exception
	{
		FileInputStream fis = new FileInputStream("./TestInputs/InputSheet.xlsx");
	
		wb = WorkbookFactory.create(fis);
	}
	
	
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	
	
	public int colCount(String sheetname, int rowNo)
	{
		return wb.getSheet(sheetname).getRow(rowNo).getLastCellNum();
	}
	
	
	public String getData(String sheetname, int row, int column)
	{
		String data = "";
		
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType() == Cell.CELL_TYPE_NUMERIC)
		{
			int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	
	
	public void setData(String sheetname, int row, int column, String data) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		Row rownum = sh.getRow(row);
		
		Cell cell = rownum.createCell(column);
		
		cell.setCellValue(data);
		
		if(data.equalsIgnoreCase("PASS"))
		{
			style = wb.createCellStyle();
			
			font = wb.createFont();
			
			// Apply color to the text
			font.setColor(IndexedColors.GREEN.getIndex());
			
			// Apply Bold to the text
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			style.setFont(font);
			
			rownum.getCell(column).setCellStyle(style);			
		}
		else
			if(data.equalsIgnoreCase("FAIL"))
			{
				style = wb.createCellStyle();
				
				font = wb.createFont();
				
				// Apply color to the text
				font.setColor(IndexedColors.RED.getIndex());
				
				// Apply Bold to the text
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				
				style.setFont(font);
				
				rownum.getCell(column).setCellStyle(style);
			}
			else
				if(data.equalsIgnoreCase("Not Executed"))
				{
					style = wb.createCellStyle();
					
					font = wb.createFont();
					
					// Apply color to the text
					font.setColor(IndexedColors.BLUE.getIndex());
					
					// Apply Bold to the text
					font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					
					style.setFont(font);
					
					rownum.getCell(column).setCellStyle(style);
				}
		
		FileOutputStream fos = new FileOutputStream("./TestOutput/OutputSheet.xlsx");
		
		wb.write(fos);
		
		fos.flush();
		
		fos.close();
	}	
}