package com.genasys.scripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.genasys.api.Implementation;
import com.genasys.locators.AllGeneralTabs;
import com.genasys.locators.MTALocators;
import com.genasys.utils.DomainList;
import com.genasys.utils.DataSheetRead;
import com.genasys.utils.NextGenUtilities;
import com.genasys.utils.WriteToExcel;
import com.genasys.webdriver.WillisWebdriver;
import com.genasys.pages.*;



public class TC01_M3_R16_MTA_Referral_SimpleTax_Validation extends Implementation
{
	public static MTALocators locators;
	private WebDriver driver;
	private String clientName;
	private int row = 1;
	private int column = 1;
	private Implementation Implementation;
	private WriteToExcel writeExcel;
	private AllGeneralTabs allGeneralTabsObj;
	private Client_MTA clientMTA;
	private long start;  
	String firefoxProfilePath;
	String chrome;
	String InternetExplorer;
	String waitingTime;
	String browsername;  
	String prevBrowserName;
	public int  flag = 0;
	int incr = 1;//Validation from test case 1st to 10th
	
	@BeforeClass
	public void setupSelenium() throws UnknownHostException, MalformedURLException {
		waitingTime=WillisWebdriver.getWaitingTime();
		chrome=WillisWebdriver.getchrome();
		firefoxProfilePath=WillisWebdriver.getfirefoxProfilePath();
		InternetExplorer=WillisWebdriver.getInternetExplorer();
	}
	
	@BeforeMethod
	public void beforeTest(){
		start = Reporter.getCurrentTestResult().getStartMillis();
	}
	
	@AfterMethod
	public void afterTest(){
		long end = Reporter.getCurrentTestResult().getEndMillis();
		String execTime =DurationFormatUtils.formatDurationHMS((end-start));
		System.out.println("Total Test Execution time - millisec: " + execTime);
		System.out.println("Total Test Execution time - HMS: " + DurationFormatUtils.formatDurationHMS((end-start)));
		writeExcel = WillisWebdriver.getWriteExcel();
		NextGenUtilities.writeExcel(writeExcel,row,column,clientName,execTime,browsername);
	}
	
	@Test(dataProviderClass=com.genasys.utils.DomainDataProvider.class,dataProvider="DomainListData")
    public void testHomePage(DomainList domainListItem) {
		browsername = domainListItem.getBrowserName();
		clientName= domainListItem.getDomainName();
		WillisWebdriver.clientName=clientName;
		WillisWebdriver.createWebDriverObject(browsername);
		driver=WillisWebdriver.getDriverObject();
		locators=PageFactory.initElements(driver, MTALocators.class);		
		Implementation = PageFactory.initElements(driver, Implementation.class);			
		allGeneralTabsObj=PageFactory.initElements(driver, AllGeneralTabs.class);
		clientMTA = PageFactory.initElements(driver, Client_MTA.class);
		prevBrowserName=browsername;
		NextGenUtilities.htmlLog = "";
		NextGenUtilities.failStep=0;
		NextGenUtilities.excelLog="";
		NextGenUtilities.appendClientHeader(clientName.toUpperCase());
		NextGenUtilities.appendTestCaseHeader("TC1_M2_R16_MTA_Referral_SimpleTax_Validation");
		
		try
		  {
	 		
			Properties name = loadPropertyFile("PC_Path");
 			String pc_sheet_path =  name.getProperty("pc_sheet_path");
 			String pc_sheet_name =  "Product";
 			DataSheetRead DataWorkBook = null;
 			String StartDate = ""; 
 			String SearchDateFrom = "";
 			String SearchDateTo = "";
 			String Transaction = "";
 			String Type = "";
 			
 			
 			 try{
					DataWorkBook = new DataSheetRead(pc_sheet_path);
					StartDate = DataWorkBook.readCellString(pc_sheet_name, "Product_Creation", "StratDate");
					SearchDateFrom = DataWorkBook.readCellString(pc_sheet_name, "MTA_Client", "SearchDateFrom");
					SearchDateTo = DataWorkBook.readCellString(pc_sheet_name, "MTA_Client", "SearchDateTo");
					Transaction = DataWorkBook.readCellString(pc_sheet_name, "MTA_Client", "Transactiony");
					Type = DataWorkBook.readCellString(pc_sheet_name, "MTA_Client", "Type");
					
						 
		 		}catch(Exception e){
			 		e.printStackTrace();
			 		System.out.println("***********Exception while reading Data sheet*********");
			 	}finally{
			 		DataWorkBook.close();
			 	}
 			
 			//Launch the browser as well application
	 		Implementation.launchClientURL(driver, clientName, browsername);
	 		
	 				 			
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		finally{
			NextGenUtilities.writeToLog(clientName);
		}  
	}
}