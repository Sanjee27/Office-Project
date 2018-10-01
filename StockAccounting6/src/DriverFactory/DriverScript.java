package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFuncLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript 
{	
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	
	public void startTest() throws Exception
	{
		ExcelFileUtil excel = new ExcelFileUtil();
		
		for(int i=1; i<=excel.rowCount("MasterTestCases");i++)
		{			
			String ModuleStatus = "";
			
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				// Define Module Name
				String TCModule = excel.getData("MasterTestCases", i, 1);
				
				report = new ExtentReports("./Reports/"+TCModule+".html "+FunctionLibrary.getDate(), true);
				
				logger = report.startTest(TCModule);
				
				int rowcount = excel.rowCount(TCModule);
				
				for(int j=1;j<=rowcount;j++)
				{
					String Description = excel.getData(TCModule, j, 0);
					
					String Object_Type = excel.getData(TCModule, j, 1);
					
					String Locator_Type = excel.getData(TCModule, j, 2);
					
					String Locator_Value = excel.getData(TCModule, j, 3);
					
					String Test_Data = excel.getData(TCModule, j, 4);
				
				try
				{
					
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("openApplication"))
					{
						FunctionLibrary.openApplication(driver);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("titleValidation"))
					{
						FunctionLibrary.titleValidation(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("captureData"))
					{
						FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("tableValidation"))
					{
						FunctionLibrary.tableValidation(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("mouseActions"))
					{
						FunctionLibrary.mouseActions(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("stockCUnitTableValidation"))
					{
						FunctionLibrary.stockCUnitTableValidation(driver, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("OK1"))
					{
						FunctionLibrary.OK1(driver, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("OK2"))
					{
						FunctionLibrary.OK2(driver, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("uomIdValidation"))
					{
						FunctionLibrary.uomIdValidation(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("dropDown"))
					{
						FunctionLibrary.dropDown(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
						
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					
					if(Object_Type.equalsIgnoreCase("datePicker"))
					{
						FunctionLibrary.datePicker(driver, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					
					excel.setData(TCModule, j, 5, "Pass");	
					
					ModuleStatus = "true";
					
					logger.log(LogStatus.PASS, Description + "    Pass");
				}
				
				catch(Exception e)
				{
					excel.setData(TCModule, j, 5, "Fail");
					
					ModuleStatus = "False";
					
					logger.log(LogStatus.FAIL, Description + "    Fail");
					
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					
					FileUtils.copyFile(scrFile, new File("./Screenshots/"+"  "+Description+"_"+FunctionLibrary.getDate()+".jpg"));
					
					String image = logger.addScreenCapture("./Screenshots/"+"  "+Description+"_"+FunctionLibrary.getDate()+".jpg");
					
					logger.log(LogStatus.FAIL, "TCModule", image);
					
					break;
				}
				
				catch(AssertionError a)
				{
					excel.setData(TCModule, j, 5, "Fail");
					
					ModuleStatus = "False";
					
					logger.log(LogStatus.FAIL, Description + "    Fail");
					
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					
					FileUtils.copyFile(scrFile, new File("./Screenshots/"+"  "+Description+"_"+FunctionLibrary.getDate()+".jpg"));
					
					String image = logger.addScreenCapture("./Screenshots/"+"  "+Description+"_"+FunctionLibrary.getDate()+".jpg");
					
					logger.log(LogStatus.FAIL, "TCModule", image);
					
					break;
				}
			}
				
				if(ModuleStatus.equalsIgnoreCase("true"))
				{
					excel.setData("MasterTestCases", i, 3, "Pass");
				}
				else
				{
					excel.setData("MasterTestCases", i, 3, "Fail");
				}
		}
			else
			{
				excel.setData("MasterTestCases", i, 3, "Not Executed");
			}	
		
		
			report.endTest(logger);
			
			report.flush();		
		}
	}	
}