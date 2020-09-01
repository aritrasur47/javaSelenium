package com.aritra.in;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BigBasket {

	public static void main(String[] args) {
		try {
			System.setProperty("webdriver.chrome.driver", "/Users/aritramac/bin/chromedriver");
			WebDriver driver = new ChromeDriver();
			driver.get("https://www.bigbasket.com/");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			driver.findElement(By.className("close-btn")).click();
			driver.findElement(By.id("input")).sendKeys("veg");
			driver.findElement(By.id("input")).sendKeys(Keys.ENTER);

			String[] vegetables = { "Ladies Finger", "Cucumber", "Potato" };

			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@qa='product_name']")));

			List<WebElement> elems = driver
					.findElements(By.xpath("//div[@qa='product_name'] //a[@class='ng-binding']"));
			int count = 0;

			for (int i = 0; i < elems.size(); i++) {
				String[] veg = elems.get(i).getText().split("/");
				String vegname = veg[0].trim();

				List vegList = Arrays.asList(vegetables);

				if (vegList.contains(vegname)) {
					count++;
					Thread.sleep(2000);
					driver.findElements(By.xpath("//button[@qa='add']")).get(i).click();

					if (count == vegetables.length) {
						break;
					}
				}
			}

			WebDriverWait wait2 = new WebDriverWait(driver, 50);
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@qa='myBasket']")));

			Actions a = new Actions(driver);
			WebElement menu = driver.findElement(By.xpath("//a[@qa='myBasket']"));
			a.moveToElement(menu);
			a.click().build().perform();

			Thread.sleep(2000);
			ScreeningPartial.screenPrint(driver);
			driver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
