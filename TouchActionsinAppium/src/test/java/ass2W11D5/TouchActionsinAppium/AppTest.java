package ass2W11D5.TouchActionsinAppium;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverAction;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.http.HttpMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private AndroidDriver driver;

	@BeforeSuite
	public void setUp() throws MalformedURLException, InterruptedException {

		// Setting up desire caps using DesireCapabilities class
		// Create an object for Desired Capabilities
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		// Set capabilities
		desiredCapabilities.setCapability("appium:app",
				"C:\\Users\\lo0ol\\Downloads\\QA_class_app_resources.zip, attachment\\QA class app resources\\ApiDemos-debug.apk");
		desiredCapabilities.setCapability("appium:deviceName", "23b9cb400c1c7ece");
		desiredCapabilities.setCapability("appium:platformName", "Android");
		desiredCapabilities.setCapability("appium:platformVersion", "10");
		desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
		desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
		desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
		// Java package of the Android app you want to run
		desiredCapabilities.setCapability("appium:appPackage", "io.appium.android.apis");
		// Activity name for the Android activity you want to launch from your package
		desiredCapabilities.setCapability("appium:appActivity", "io.appium.android.apis.ApiDemos");

		System.out.println("Finshed: desiredCapabilities");

		// Initialize the driver object with the URL to Appium Server and
		// passing the capabilities
		URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver(remoteUrl, desiredCapabilities);
		System.out.println("Finshed: driver");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}
	

	/**
	 * Test Drag and Drop
	 */
	@Test(priority = 1)
	public void TestDragandDrop() throws InterruptedException {	
		// Perform the action on the element
		// click on "View"
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		System.out.println("Finshed: Views");
		Thread.sleep(2000);

		// click on "Drag and Drop" using Actions
		WebElement elementDragandDrop = driver.findElement(AppiumBy.accessibilityId("Drag and Drop"));
		Actions action = new Actions(driver);
		action.moveToElement(elementDragandDrop).click().build().perform();
		System.out.println("Finshed: Gallery action");
		Thread.sleep(2000);
		
		// drag and drop 
		((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
			    "startX", 212,
			    "startY",552,
			    "endX", 615,
			    "endY", 556
			));
		
		System.out.println("Finshed: dragAndDrop");
        Thread.sleep(15000);

        if(driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_result_text")).isDisplayed()==true) {
    		System.out.println("Finshed: isDisplayed");
        }
        
		// back to "My account" Page
		Navigation navigate = driver.navigate();
		navigate.back();
		System.out.println("Finshed: back to \"My account\" Page");
		Thread.sleep(3000);
		

	}

	/**
	 * Test Swipe
	 */
	@Test(priority = 2, dependsOnMethods = { "TestDragandDrop" })
	public void TestSwipe() throws InterruptedException {
		// click on Gallery
		driver.findElement(AppiumBy.xpath("//*[contains(@text,'Gallery')]")).click();
		System.out.println("Finshed: click on Gallery");
		Thread.sleep(5000);
		
		// click on "Photos" using Actions
		WebElement elementPhotos = driver.findElement(AppiumBy.accessibilityId("1. Photos"));
		Actions action = new Actions(driver);
		action.moveToElement(elementPhotos).click().build().perform();
		System.out.println("Finshed: Photos action");
		Thread.sleep(5000);

		// take image xpath
		RemoteWebElement image = (RemoteWebElement) driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.Gallery/android.widget.ImageView[3]"));
		System.out.println("Finshed: image");

		// swipe right to the photo number 2
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
				ImmutableMap.builder().put("direction", "right").put("elementId", image.getId()).put("maxSwipes", 10)
						.put("percent", 0.0).build());
		System.out.println("Finshed: swipe right");
		Thread.sleep(2000);

		// click on image
		image.click();
		System.out.println("Finshed: click on image");
		Thread.sleep(2000);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		System.out.println("Finshed: quit");
	}
}
