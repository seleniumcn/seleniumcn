package cn.seleniumcn.api;


import org.testng.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

/**
 * 
 * @author bruce
 *
 */

public class WebdiverAPITest01 {
	
	WebDriver driver;
	
	@Test
	public void testWebdriverAPIs() throws InterruptedException{
		
		
		
		
		/****
		 * Open browser
		 * 
		 */				
		driver =new ChromeDriver();
		/**
		 * maximize
		 */		
		driver.manage().window().maximize();
		
		/**
		 * open aut url
		 */		
		driver.get("http://centos/selapi.html");
		
		/**************************************************************************************
		 * test getTitle
		 */
		//get title 
		String actual_title=driver.getTitle();		
		//verify title
		Assert.assertEquals(actual_title, "selenium api demo");
		
		/**************************************************************************************
		 *test getCurrentUrl
		 */		
		//get url
		String current_URL = driver.getCurrentUrl();
		//verify current url 
		Assert.assertEquals(current_URL, "http://centos/selapi.html");
		
		/**************************************************************************************
		 * 
		 * test getText
		 */
		//find element label 
		WebElement label_user_name = driver.findElement(By.xpath("//div[input[@id='userName']]/label"));
		//get text
		String str_label_user_name = label_user_name.getText();
		//verify text
		Assert.assertEquals(str_label_user_name, "user name:");
		
		
		/**************************************************************************************
		 * test getAttribute value, type, class....
		 */
		// verify the value of input text field
		WebElement txt_user_name =  driver.findElement(By.id("userName"));
		String str_value= txt_user_name.getAttribute("value");
		
		Assert.assertEquals(str_value,"please input user name");
		
		//clear the text
		txt_user_name.clear(); // clear the value of textfield
		str_value=txt_user_name.getAttribute("value");
		//verify the value is empty
		Assert.assertEquals(str_value,"");
		
		
		
		//sendKeys/setText
		txt_user_name.sendKeys("test sendkeys");
		str_value=txt_user_name.getAttribute("value");
		Assert.assertEquals(str_value,"test sendkeys");

		
		//verify the type
		WebElement rd_sex_m = driver.findElement(By.xpath("//label[text()='M:']/following-sibling::input"));		
		Assert.assertEquals(rd_sex_m.getAttribute("type"),"radio");
		
		//verify the class
		Assert.assertEquals( driver.findElement(By.name("sexdiv")).getAttribute("class"), "ext-radio_group");
		
		
		/**************************************************************************************
		 * test getCSSValue
		 * 
		 */
		
		WebElement label_hidden =  driver.findElement(By.name("hiddenLabel"));
		
		String css_display= label_hidden.getCssValue("display");
		String css_width= label_hidden.getCssValue("width");
		Assert.assertEquals(css_display,"none");
		
		System.out.println("width of hidden lable is:"+css_width );
		
		
		/**************************************************************************************
		 * test isDisplayed
		 */
		
		WebElement label_displayed = driver.findElement(By.name("displayedLabel"));
		
		//verify displayed
		Assert.assertTrue(label_displayed.isDisplayed());
		
		//verify not displayed
		Assert.assertFalse(label_hidden.isDisplayed());
		
		
		/**************************************************************************************
		 * test isSelected , click , select
		 * 
		 * 
		 */
		//verify radio M is selected
		Assert.assertTrue(rd_sex_m.isSelected());
		
		//verify radio F is not selected
		WebElement rd_sex_f= driver.findElement(By.xpath("//label[text()='F:']/following-sibling::input"));
		Assert.assertFalse(rd_sex_f.isSelected());
		
		//click the radio to make the radio selected
		rd_sex_f.click();
		//verify after click
		Assert.assertFalse(rd_sex_m.isSelected());
		Assert.assertTrue(rd_sex_f.isSelected());
		
		
		
		
		//verify checkbox remember is not selected/checked
		WebElement chk_remember = driver.findElement(By.xpath("//div[label[text()='remember']]/input[contains(@class,'checkbox')]"));
		Assert.assertFalse(chk_remember.isSelected());
		
		//click the checkbox to select the checkbox
		chk_remember.click();
		Assert.assertTrue(chk_remember.isSelected());
		
		WebElement opt_red = driver.findElement(By.name("select")).findElement(By.xpath("//option[text()='red']"));
		WebElement opt_blue = driver.findElement(By.name("select")).findElement(By.xpath("//option[text()='blue']"));
		Assert.assertTrue(opt_red.isSelected());
		Assert.assertFalse(opt_blue.isSelected());
		
		Select select_color= new Select(driver.findElement(By.name("select")));
		
		//select action
		select_color.selectByVisibleText("blue");
		//select_color.selectByIndex(index);  other select method
		//select_color.selectByValue(value);  other select method
		Assert.assertFalse(opt_red.isSelected());
		Assert.assertTrue(opt_blue.isSelected());
		
		
		/**************************************************************************************
		 * 
		 * test alert, 
		 * 
		 */
		
		WebElement btn_test_alert=  driver.findElement(By.xpath("//input[@value='testAlert']"));
		btn_test_alert.click();
		
		Alert alert = driver.switchTo().alert();
		
		String str_alert = alert.getText(); // get the text of the alert
		
		Assert.assertEquals(str_alert, "this is an alert test");
		
		alert.accept();
		
		/**
		 * test confirm
		 */
		
		WebElement btn_test_confirm  =driver.findElement(By.xpath("//input[@value='testConfirm']"));
		btn_test_confirm.click();
		
		//swith to confirm
		Alert confirm= driver.switchTo().alert();	
		//verify confirm msg
		Assert.assertEquals(confirm.getText(), "it is a confirm");
		//click ok on the confirm
		confirm.accept();		
		WebElement span_show = driver.findElement(By.id("alertSpan"));
		Assert.assertEquals(span_show.getText(), "OK selected");
		
		btn_test_confirm.click();
		//cancle the confirm
		driver.switchTo().alert().dismiss();
		Assert.assertEquals(span_show.getText(), "cancle selected");
		
		
		/**
		 * test prompt
		 */
		WebElement btn_test_testPrompt  =driver.findElement(By.xpath("//input[@value='testPrompt']"));
		btn_test_testPrompt.click();
		
		Alert prompt= driver.switchTo().alert();	
		prompt.sendKeys("i'm bruce");
		prompt.accept();
		
		Assert.assertEquals(span_show.getText(), "i'm bruce");
		
		/**
		 * 
		 * Action ...
		 * 		click                 X
		 * 		double click          
		 * 		sendkeys              X
		 *      keyDown, keyUp
		 *      clickAndHold
		 *      release
		 *      moveToElement
		 *      contextClick
		 *      dragAndDrop
		 */
		
		//double click
		WebElement div_double_click =driver.findElement(By.id("doubleClickDiv"));
		
		Actions actions= new Actions(driver);
		actions.doubleClick(div_double_click).build().perform();
		
		Assert.assertEquals(driver.findElement(By.id("showdoubleclick")).getText(), "double click action performened");
		
		
		//mouse move to element
		
		WebElement div_mouse_move =driver.findElement(By.id("testmouseover"));
		WebElement div_display_after_mouse_over = driver.findElement(By.id("showmouseover"));
		
		//verify div_display_after_mouse_over is not displayed before moveToElement
		Assert.assertFalse(div_display_after_mouse_over.isDisplayed());
		//perform moveToElement
		actions.moveToElement(div_mouse_move).build().perform();
		
		//verify div_display_after_mouse_over is  displayed after moveToElement
		Assert.assertTrue(div_display_after_mouse_over.isDisplayed());
		
		
		
		// ClickAndHold, Release
		
		WebElement div_mouse_clickAndHold_release =driver.findElement(By.id("testClickAndHold"));
		WebElement div_display_after_clickAndHold = driver.findElement(By.id("showClickAndHold"));
		
		//verify div_display_after_clickAndHold is not displayed before clickAndHold 
		Assert.assertFalse(div_display_after_clickAndHold.isDisplayed());
		
		//perform clickAndHold
		actions.clickAndHold(div_mouse_clickAndHold_release).build().perform();
		//verify div_display_after_clickAndHold is displayed
		Assert.assertTrue(div_display_after_clickAndHold.isDisplayed());
		
		//perform release
		actions.release(div_mouse_clickAndHold_release).build().perform();
		//verify div_display_after_clickAndHold disappear after release
		Assert.assertFalse(div_display_after_clickAndHold.isDisplayed());
		
		
		
		// keydown keyup
		
				
		WebElement txt_test_key_event = driver.findElement(By.id("test_key_event"));
		txt_test_key_event.click();
		actions.keyDown(txt_test_key_event,Keys.SHIFT).sendKeys(txt_test_key_event, "w").keyUp(txt_test_key_event, Keys.SHIFT).perform();
		//txt_test_key_event.sendKeys("w");
		//actions.keyUp(txt_test_key_event,Keys.SHIFT).build().perform();
		
		Assert.assertEquals(txt_test_key_event.getAttribute("value"), "W");
		
		
		//dragAndDrop
		
		driver.get("http://centos/drag.html");
		
		WebElement from = driver.findElement(By.id("item1"));
		WebElement to = driver.findElement(By.id("drop"));
						
		actions.dragAndDrop(from, to).perform();		
		
		Assert.assertFalse(from.isDisplayed());
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='drop']/p[text()='11111dropped']")).isDisplayed());
		
		
		/**
		 * back, forward
		 * 
		 * 
		 */		
		driver.navigate().back();
		Assert.assertEquals(driver.getCurrentUrl(), "http://centos/selapi.html");
		driver.navigate().forward();
		Assert.assertEquals(driver.getCurrentUrl(), "http://centos/drag.html");
	
		
		
		/************************************************************************************************************************
		 * 
		 * 
		 * 
		 * Next section
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		
		
		/**
		 * Test Wait
		 *	    implicitlyWait
		 *		WebDriverWait 
		 *		ExpectedConditions
		 *		ExpectedCondition
		 * 
		 */
		
		/**
		 * 
		 * Switch frame/iframe
		 */
		
		/**
		 * Switch browser windows
		 * 
		 */
		
		/**
		 *  Execute JS
		 */
		
		/**
		 *  Screenshot
		 */
		
		
	}
	
	
	@AfterClass
	
	public void teardown(){
		
		
		
		if (driver!=null)
			driver.quit();
		
	}

}
