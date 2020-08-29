package com.aritra.in;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestAmazon {

	public static void main(String[] args) {

		try {
			System.setProperty("webdriver.chrome.driver", "/Users/aritramac/bin/chromedriver");
			WebDriver driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://www.amazon.in/");
			driver.manage().window().fullscreen();

			WebElement search = driver.findElement(By.id("twotabsearchtextbox"));
			search.sendKeys("oneplus 8");
			search.sendKeys(Keys.RETURN); // using keyboard enter

			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Need help?')]")));

			// Sorting the prices in ascending order
			List<String> mobilePrices = new ArrayList<String>();
			List<WebElement> searchOptions = driver
					.findElements(By.xpath("//*[contains(@class, 's-main-slot')] //span[@class='a-price-whole']"));

			for (WebElement price : searchOptions) {
				mobilePrices.add(price.getText());
			}

			Collections.sort(mobilePrices);
			System.out.println("Prices in sorted ascending order : \n" + mobilePrices);

			driver.findElement(By.xpath("//span[(@class='a-button-inner')] //span[@class='a-dropdown-label']")).click();

			WebDriverWait wait2 = new WebDriverWait(driver, 30);
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("a-popover-inner")));

			List<WebElement> sortBy = driver.findElements(By.className("a-dropdown-link"));
			for (WebElement sort : sortBy) {
				if (sort.getText().toLowerCase().contains("high to low")) {
					sort.click();
					break;
				}
			}
			Thread.sleep(3000);

			List<WebElement> items = driver.findElements(By.xpath("//*[contains(@class, 'a-size-medium')]"));
			items.get(1).click();

			// Send control to the child window
			Set<String> ids = driver.getWindowHandles();
			Iterator<String> it = ids.iterator();
			String parentId = it.next();
			String childId = it.next();
			driver.switchTo().window(childId);
			Thread.sleep(5000);
			driver.findElement(By.id("add-to-cart-button")).click();

			driver.switchTo().window(parentId);

			Thread.sleep(3000);
			driver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
