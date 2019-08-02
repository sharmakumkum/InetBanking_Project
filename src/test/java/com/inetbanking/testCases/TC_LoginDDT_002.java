package com.inetbanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;

public class TC_LoginDDT_002 extends BaseClass
{
	
	@Test (dataProvider="LoginData")
	public void loginDDT(String user,String pwd) throws InterruptedException
	{
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(user);
		logger.info("User name provided");
		lp.setPassword(pwd);
		logger.info("Password provided");
		lp.clickSubmit();
		logger.info("Click button pressed");
		Thread.sleep(2000);
		
		if(isAlertPresent())
		{
			driver.switchTo().alert().accept(); //Close the alert for  wrong username/password
			driver.switchTo().defaultContent();
			//Assert.assertTrue(false);
			logger.warn("Login failed");
		}
		else
		{
			//Assert.assertTrue(true);
			logger.warn("Login passed");
			lp.clickLogout();
			Thread.sleep(2000);
			driver.switchTo().alert().accept(); //close out logout alert
			driver.switchTo().defaultContent();
		}
	}
	
	
	public boolean isAlertPresent()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path= System.getProperty("user.dir")+"/src/test/java/com/inetbanking/testdata/LoginData.xlsx";
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int colcount=XLUtils.getCellCount(path, "Sheet1", 1);
		
		String loginData[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				loginData[i-1][j]=XLUtils.getcellData(path, "Sheet1", i, j);
			}
		}
		return loginData;
	}
	
}
