package cn.seleniumcn;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
@author Bruce Gu
**/
public class HelloWorldWebdriver {

	
	public  static final String SELENIUMCN_LINK = "//div[@id='content_left']/table[@id='1']//h3/a";

	public static void main(String args[]) throws Exception {
		
		WebDriver driver = new FirefoxDriver();

		
		driver.get("http://www.baidu.com");

		
		WebElement query = driver.findElement(By.name("wd"));
		query.sendKeys("selenium ÷–Œƒ¬€Ã≥");

		WebElement btn = driver.findElement(By.id("su"));
		btn.click();
		Thread.sleep(5000);
				//baidu searching result UI changes, so updated the following XPath
		// WebElement link = driver.findElement(By
		// .xpath("//a[@href='http://seleniumcn.cn/']"));

		WebElement link = driver.findElement(By
				.xpath(SELENIUMCN_LINK));
		
		link.click();
		Thread.sleep(5000);
		
		driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
		
		System.out.println(driver.getTitle());
		
		driver.quit();

	}
}