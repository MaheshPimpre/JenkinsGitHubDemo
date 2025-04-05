package com.expo.test;



import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoGuruTest {

	public WebDriver driver;

	@BeforeSuite
	public void openBrowser() {
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	@BeforeTest
	public void openWebPage() {
		driver.get("https://demo.guru99.com/test/newtours/index.php");
		driver.manage().window().maximize();
	}

	@BeforeClass
	public void waitImplementation() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void signIn() {
		driver.findElement(By.linkText("SIGN-ON")).click();
	}

	@Test (dataProvider="dataProvider")
	public void login(String username, String password) {
		driver.findElement(By.name("userName")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("submit")).click();
		String text = driver.findElement(By.xpath(
				"/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/h3"))
				.getText();
		assertEquals(text, "Login Successfully");
	}


	@AfterMethod
	public void takeScreenshot() throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File file = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFileToDirectory(file, new File("D:\\Automation Testing\\DemoGuru2\\target\\screenshot"));
		driver.findElement(By.linkText("SIGN-OFF")).click();
	}

	@DataProvider
	public Object [][] dataProvider(){
		return new Object[][] {
			new Object[] {"AAAAA","aaaaa"},
			new Object[] {"BBBBB","bbbbb"},
			new Object[] {"CCCCC",""}			
		};
	}
	
	@AfterSuite
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}
