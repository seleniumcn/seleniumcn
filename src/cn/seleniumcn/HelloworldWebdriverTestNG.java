package cn.seleniumcn;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.Assert;

public class HelloworldWebdriverTestNG {
	
	public  static final String SELENIUMCN_LINK = "//td[div/span[contains(.,'seleniumcn.cn/ ')]]/h3//a";

	WebDriver driver;
	
	@Test
	public void TestOne() throws Exception {	
		
		driver = new FirefoxDriver();
		
		driver.get("http://www.baidu.com");

		
		WebElement query = driver.findElement(By.name("wd"));
		query.sendKeys("selenium 中文论坛");

		WebElement btn = driver.findElement(By.id("su"));
		btn.click();
		Thread.sleep(5000);
		

		WebElement link = driver.findElement(By
				.xpath(SELENIUMCN_LINK));
		link.click();
		Thread.sleep(5000);
		
		driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
		
		String expectedTitle ="Selenium 中文论坛, Webdriver 中文论坛, Selenium2, 软件测试,web自动化测试";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);		

	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
	
}