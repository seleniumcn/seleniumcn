package cn.seleniumcn.question;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.support.ui.Select;

/**
@author Bruce Gu
**/

public class SelectMethod {
	
	

	public  static final String URL ="http://book.theautomatedtester.co.uk/chapter1";
	
	WebDriver driver;
	
	
	
	@Test
	public void TestOne() throws Exception {	
		
		driver = new FirefoxDriver();
		
		driver.get(URL);

		Select select = new Select(driver.findElement(By.id("selecttype")));
		
		select.selectByValue("Selenium Code");
		
		Assert.assertTrue(driver.findElement(By.xpath("//option[@value='Selenium Code']")).isSelected());
		
		select.selectByVisibleText("Selenium Grid");
		Assert.assertTrue(driver.findElement(By.xpath("//option[@value='Selenium Grid']")).isSelected());
		
		select.selectByIndex(0);
		Assert.assertTrue(driver.findElement(By.id("selecttype")).findElements(By.tagName("option")).get(0).isSelected());
		
	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
	
}