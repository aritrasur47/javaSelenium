package com.aritra.in;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;

public class ScreeningPartial {

	public static void screenPrint(WebDriver driver) {
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("/Users/aritramac/Downloads/" + "capture.png"));
			Thread.sleep(2000);

		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.quit();
	}
}
