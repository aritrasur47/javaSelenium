package com.aritra.in;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoIbibo {

	public static void main(String[] args) {

		try {
			System.setProperty("webdriver.chrome.driver", "/Users/aritramac/bin/chromedriver");
			WebDriver driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get("https://www.goibibo.com/");

			WebElement roundTrip = driver.findElement(
					By.xpath("//div[@class = 'fltSwitchOpt dIF alignItemsCenter ico15'] //span[@id='roundTrip']"));
			roundTrip.click();

			driver.findElement(By.id("gosuggest_inputSrc")).sendKeys("ban");

			// from Source
			WebDriverWait src = new WebDriverWait(driver, 30);
			src.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-autosuggest-1")));

			List<WebElement> origins = driver.findElements(By.xpath("//ul[@id='react-autosuggest-1']/li"));
			// System.out.println(origins.size());

			for (WebElement origin : origins) {
				if (origin.getText().toLowerCase().contains("bengaluru")) {
					origin.click();
					break;
				}
			}

			// to Destination
			driver.findElement(By.id("gosuggest_inputDest")).sendKeys("dub");

			WebDriverWait dest = new WebDriverWait(driver, 30);
			dest.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-autosuggest-1")));

			List<WebElement> destinations = driver.findElements(By.xpath("//ul[@id='react-autosuggest-1']/li"));
			for (WebElement destination : destinations) {
				if (destination.getText().toLowerCase().contains("dub")) {
					destination.click();
					break;
				}
			}

			// departure calendar
			while (!driver.findElement(By.className("DayPicker-Caption")).getText().contains("October")) {
				driver.findElement(By
						.xpath("//div[@class='DayPicker-NavBar'] //span[@class='DayPicker-NavButton DayPicker-NavButton--next']"))
						.click();
			}

			List<WebElement> dates = driver
					.findElements(By.xpath("//div[@class='DayPicker-Body'] //div[@class='DayPicker-Day']"));
			int count1 = dates.size();

			for (int i = 0; i < count1; i++) {
				String dateText = driver.findElements(By.className("DayPicker-Day")).get(i).getText();

				if (dateText.contains("23")) {
					driver.findElements(By.className("DayPicker-Day")).get(i).click();
					break;
				}
			}

			// return calendar
			while (!driver.findElement(By.className("DayPicker-Caption")).getText().contains("November")) {
				driver.findElement(By
						.xpath("//div[@class='DayPicker-NavBar'] //span[@class='DayPicker-NavButton DayPicker-NavButton--next']"))
						.click();
			}

			List<WebElement> returnDates = driver
					.findElements(By.xpath("//div[@class='DayPicker-Body'] //div[@class='DayPicker-Day']"));
			int count2 = returnDates.size();

			for (int i = 0; i < count2; i++) {
				String returnDateText = driver.findElements(By.className("DayPicker-Day")).get(i).getText();

				if (returnDateText.contains("12")) {
					driver.findElements(By.className("DayPicker-Day")).get(i).click();
					break;
				}
			}

			// Traveler Info
			driver.findElement(By.xpath("//div[@id='pax_link_common']/span")).click();

			for (int i = 0; i < 2; i++) {
				driver.findElement(By.id("adultPaxPlus")).click();
			}

			// Travel Class
			Select select = new Select(driver.findElement(By.id("gi_class")));
			select.selectByVisibleText("Premium Economy");

			driver.findElement(By.id("pax_close")).click();

			// Search Button
			driver.findElement(By.id("gi_search_btn")).click();
			Thread.sleep(5000);

			// take the screenshot
			ScreenShot.screenPrint(driver);

			driver.quit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
