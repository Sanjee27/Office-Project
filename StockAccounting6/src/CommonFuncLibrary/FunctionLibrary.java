package CommonFuncLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;


public class FunctionLibrary
{	
	public static WebDriver startBrowser(WebDriver driver) throws Exception, Exception
	{
		
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "CommonJarFiles/chromedriver.exe");
				
				driver = new ChromeDriver();
			}
			else
				if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("IE"))
				{
					System.setProperty("webdriver.ie.driver", "CommonJarFiles/IEDriverServer.exe");
				
					driver = new InternetExplorerDriver();
				}
		
		return driver;
	}
	
	
	public static void openApplication(WebDriver driver) throws Exception, Exception
	{
		driver.manage().window().maximize();
		
		driver.get(PropertyFileUtil.getValueForKey("URL"));
	}
	
	
	public static void clickAction(WebDriver driver, String locatorType, String locatorValue)
	{		
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).click();
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).click();
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).click();
				}		
	}
	
	
	
	public static void typeAction(WebDriver driver, String locatorType, String locatorValue, String data)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(data);
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).clear();
				driver.findElement(By.name(locatorValue)).sendKeys(data);
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).clear();
					driver.findElement(By.xpath(locatorValue)).sendKeys(data);
				}
	}
	
	
	
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	
	
	
	public static void waitForElement(WebDriver driver, String locatorType, String locatorValue, String waittime)
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(waittime)); 
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				}
	}	
	
	
	
	public static void titleValidation(WebDriver driver, String validData) throws Exception
	{
		Thread.sleep(2000);
		
		String act_title = driver.getTitle();
		
		String exp_title = validData;
		
		Assert.assertEquals(act_title, exp_title);
	}
	
	
	public static String getDate()
	{
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_ss");
		
		return sdf.format(date);
	}
	
	
	public static void captureData(WebDriver driver, String locatorType, String locatorValue) throws Exception
	{
		String data = "";
		
		if(locatorType.equalsIgnoreCase("xpath"))
		{
			data = driver.findElement(By.xpath(locatorValue)).getAttribute("value");
		}
		else if(locatorType.equalsIgnoreCase("id"))
		{
			data = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		
		FileWriter fw = new FileWriter("./CapturedData/Data.txt");
		
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(data);
		
		bw.flush();
		
		bw.close();		
	}
	
	
	public static void tableValidation(WebDriver driver, String reqNum) throws Exception
	{
		FileReader fr = new FileReader("./CapturedData/Data.txt");
		
		BufferedReader br = new BufferedReader(fr);
		
		String exp_data = br.readLine();
		
		int reqNum1 = Integer.parseInt(reqNum);
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).sendKeys(exp_data);
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.btn"))).click();
			Thread.sleep(2000);
		}
		else
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.option"))).click();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).sendKeys(exp_data);
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.btn"))).click();
			Thread.sleep(2000);
		}
		
		WebElement webtable = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Webtable.path")));
		
		List<WebElement> rows = webtable.findElements(By.tagName("tr"));
		
		for(int i=1;i<=rows.size();i++)
		{
			String act_data = driver.findElement(By.xpath(".//*[@id='ewContentColumn']/div[3]/form/div/div//table[@class='table ewTable']/tbody/tr["+i+"]/td["+reqNum1+"]/div/span/span")).getText();              
		
			Assert.assertEquals(act_data, exp_data);
			
			break;		
		}		
	}
	
	
	public static void mouseActions(WebDriver driver, String locatorType, String locatorValue)
	{
		Actions action = new Actions(driver);
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			action.moveToElement(driver.findElement(By.id(locatorValue))).build().perform();
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				action.moveToElement(driver.findElement(By.name(locatorValue))).build().perform();
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					action.moveToElement(driver.findElement(By.xpath(locatorValue))).build().perform();
				}
	}
	
	
	public static void stockCUnitTableValidation(WebDriver driver, String reqNum, String exp_data) throws Exception
	{
		int reqNum1 = Integer.parseInt(reqNum);
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).sendKeys(exp_data);
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.btn"))).click();
			Thread.sleep(2000);
		}
		else
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.option"))).click();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.box"))).sendKeys(exp_data);
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.btn"))).click();
			Thread.sleep(2000);
		}
		
		WebElement webtable = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Webtable.path")));
		
		List<WebElement> rows = webtable.findElements(By.tagName("tr"));
		
		for(int i=1;i<=rows.size();i++)
		{
			String act_data = driver.findElement(By.xpath(".//*[@id='ewContentColumn']/div[3]/form/div/div//table[@class='table ewTable']/tbody/tr["+i+"]/td["+reqNum1+"]/div/span/span")).getText();              
		
			Assert.assertEquals(act_data, exp_data);
			
			break;		
		}		
	}
	
	
	public static void OK1(WebDriver driver, String locatorValue) throws Exception
	{
		Thread.sleep(2000);
		
		List<WebElement> oks = driver.findElements(By.xpath(locatorValue));
		
		for(int i=0;i<oks.size();i++)
		{
			if(oks.get(i).getText().equalsIgnoreCase("OK!"))
			{
				oks.get(i).click();
				
				break;
			}
		}
	}
	
	
	public static void OK2(WebDriver driver, String locatorValue) throws Exception
	{
		Thread.sleep(2000);
		
		List<WebElement> oks = driver.findElements(By.xpath(locatorValue));
		
		for(int i=0;i<oks.size();i++)
		{
			if(oks.get(i).getText().equalsIgnoreCase("OK"))
			{
				oks.get(i).click();
				
				break;
			}
		}
	}
	
	
	public static void uomIdValidation(WebDriver driver, String locatorType, String locatorValue, String textData) throws Exception, Exception
	{
		Thread.sleep(2000);
		
		String act_text = " ";
		
		String duplicateData = " ";
		
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			act_text = driver.findElement(By.id(locatorValue)).getText();
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				act_text = driver.findElement(By.name(locatorValue)).getText();
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					act_text = driver.findElement(By.xpath(locatorValue)).getText();
				}
		
		if(act_text.contains("Duplicate primary key"))
		{
			int min=0, max=9999999;
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Second_OK"))).click();
			
			int randomNum = (int)(Math.random() * max);
			
			duplicateData = String.valueOf(randomNum);
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("uom_id"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("uom_desc"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("uom_id"))).sendKeys(duplicateData);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("uom_desc"))).sendKeys(textData);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Add_Button"))).click();
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("First_OK"))).click();
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Second_OK"))).click();
		}		
	}
	
	
	public static void dropDown(WebDriver driver, String locatorType, String locatorValue, String data)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			new Select(driver.findElement(By.id(locatorValue))).selectByVisibleText(data);
		}
		else
			if(locatorType.equalsIgnoreCase("name"))
			{
				new Select(driver.findElement(By.name(locatorValue))).selectByVisibleText(data);
			}
			else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					new Select(driver.findElement(By.xpath(locatorValue))).selectByVisibleText(data);
				}
	}
	
	
	public static void dropDownAction(WebDriver driver, String locatorType, String locatorValue, String data) throws Exception
	{
		Actions action = new Actions(driver);
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			WebElement element = driver.findElement(By.id(locatorValue));
			
			action.moveToElement(element).perform();
			
			Thread.sleep(2000);
			
			action.click().sendKeys(data).sendKeys(Keys.ENTER).build().perform();
		}
		else
			if(locatorType.equalsIgnoreCase("xpath"))
			{
				WebElement element = driver.findElement(By.xpath(locatorValue));
				
				action.moveToElement(element).perform();
				
				Thread.sleep(2000);
				
				action.click().sendKeys(data).sendKeys(Keys.ENTER).build().perform();
			}
			else
				if(locatorType.equalsIgnoreCase("name"))
				{
					WebElement element = driver.findElement(By.name(locatorValue));
					
					action.moveToElement(element).perform();
					
					Thread.sleep(2000);
					
					action.click().sendKeys(data).sendKeys(Keys.ENTER).build().perform();
				}
	}
	
	
	public static void datePicker(WebDriver driver, String locatorValue) throws Exception
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		js.executeScript("document.getElementById('"+locatorValue+"').value='"+PropertyFileUtil.getValueForKey("datepicker.time")+"'");	
	}
}