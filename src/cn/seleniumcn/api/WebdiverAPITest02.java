package cn.seleniumcn.api;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

/**
 * 
 * @author bruce
 * 
 */

public class WebdiverAPITest02 {

	WebDriver driver;


	@Test
	public void testWebdriverAPIs_partII() throws InterruptedException {
		driver = new ChromeDriver();
		driver.get("http://centos/selapi2.html");

		/**
		 * Test Wait implicitlyWait WebDriverWait ExpectedConditions
		 * ExpectedCondition
		 * 
		 */

		// implicitly wait
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		// Thread.sleep(3000);

		// Explicitly Wait

		WebElement btn_test_wait = driver.findElement(By
				.xpath("//input[@value='testExplicitlyWait']"));
		btn_test_wait.click();

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//button[contains(text(),'testExplicitlyWait')]")));

		driver.findElement(
				By.xpath("//button[contains(text(),'testExplicitlyWait')]"))
				.click();

		Alert alert_show_AJax_button = driver.switchTo().alert();
		Assert.assertEquals(alert_show_AJax_button.getText(), "show ajax btn");

		alert_show_AJax_button.accept();

		// define own ExpectedCondition
		// wait for class changed to "new"

		driver.findElement(By.xpath("//input[@value='change Attribute ']"))
				.click();

		WebDriverWait wait2 = new WebDriverWait(driver, 20);
		wait2.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				System.out.println(driver.findElement(
						By.id("waitAttributeChanges")).getAttribute("class"));
				return driver.findElement(By.id("waitAttributeChanges"))
						.getAttribute("class").equals("new");
			}
		});
		// ExpectedConditions

		Assert.assertEquals(driver.findElement(By.id("waitAttributeChanges"))
				.getAttribute("class"), "new");

		/**
		 * 
		 * Switch frame
		 */
		driver.get("http://centos/frame.html");

		// switch to frame by name
		driver.switchTo().frame("f1");
		driver.findElement(By.id("button_within_frame1")).click();
		driver.switchTo().alert().accept();

		// switch back to root level
		driver.switchTo().defaultContent();

		// switch to frame by id
		driver.switchTo().frame("f2");
		driver.findElement(By.id("button_within_frame2")).click();
		driver.switchTo().alert().accept();

		// switch back to root level
		driver.switchTo().defaultContent();

		driver.switchTo().frame(0);
		driver.findElement(By.id("button_within_frame1")).click();
		driver.switchTo().alert().accept();

		// switch back to root level
		driver.switchTo().defaultContent();

		// switch to frame by frame Element;
		driver.switchTo().frame(
				driver.findElement(By.xpath("//frame[@src='f3.html']")));
		driver.findElement(By.id("button_within_frame3")).click();
		driver.switchTo().alert().accept();

		/**
		 * 
		 * iframe
		 * 
		 */
		driver.get("http://centos/iframe.html");
		driver.findElement(By.id("btn_default_level")).click();
		driver.switchTo().alert().accept();

		// switch to level1
		driver.switchTo().frame("iframe1");
		driver.findElement(By.id("button_within_iframe1")).click();
		driver.switchTo().alert().accept();

		// switch to level2
		driver.switchTo().frame(
				driver.findElement(By.xpath("//iframe[@src='if2.html']")));
		driver.findElement(By.id("button_within_iframe2")).click();
		driver.switchTo().alert().accept();

		// can't switch up to parent iframe, but swtich to default content and
		// then switch again
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe1");
		driver.findElement(By.id("button_within_iframe1")).click();
		driver.switchTo().alert().accept();

		/**
		 * Switch browser windows
		 * 
		 */
		driver.get("http://centos/selapi2.html");

		String initialWindowHandler = driver.getWindowHandle();
		driver.findElement(By.id("openwindowwithname")).click();

		// window name
		driver.switchTo().window("newwindow");

		// Assert.assertEquals(driver.getTitle(),"new window");
		System.out.println(driver.getTitle());

		driver.switchTo().window(initialWindowHandler);
		System.out.println(driver.getTitle());

		// window name

		Set<String> handlers = driver.getWindowHandles();

		for (String handler : handlers) {

			driver.switchTo().window(handler);

			if (isElementPresentByLocator(By.xpath("//span[@id='newwindows']")))
				break;

		}

		System.out.println(driver.getTitle());

		driver.close();
		driver.switchTo().window(initialWindowHandler);
		System.out.println(driver.getTitle());

		/**
		 * Execute JS
		 */

		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement span_test_js = driver.findElement(By.id("testjs"));
		Assert.assertEquals(span_test_js.getText(), "test js...");
		js.executeScript("document.getElementById('testjs').innerHTML='bingo js executor'");
		Assert.assertEquals(span_test_js.getText(), "bingo js executor");

		/**
		 * Screenshot
		 */

		taskScreenshot();

	}

	public boolean isElementPresentByLocator(By by) {

		boolean find = false;
		try {

			driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
			driver.findElement(by);
			find = true;
		} catch (Exception e) {
			System.out.println("not found");
			//
		} finally {

			driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		}

		return find;
	}

	public void taskScreenshot() {

		File screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String dateString = formatter.format(new Date());
		screenshot.renameTo(new File(dateString + ".png"));
	}

	@AfterMethod
	public void teardown() {

		if (driver != null)
			driver.quit();

	}

}
