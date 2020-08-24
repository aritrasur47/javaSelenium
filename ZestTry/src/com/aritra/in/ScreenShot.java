package com.aritra.in;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

//Take fullpage screenshot

public class ScreenShot {

	public static void screenPrint(WebDriver driver) {
		try {
			Screenshot screenshot = new AShot()
					.shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(2.0f), 1000))
					.takeScreenshot(driver);

			final BufferedImage image = screenshot.getImage();
			ImageIO.write(image, "PNG", new File("/Users/aritramac/Downloads/" + "fullscn.png"));
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
