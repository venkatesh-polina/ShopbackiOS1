package AppFramework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class FrameWork {

    public IOSDriver getiOSDriver(AppiumDriverLocalService appiumDriverLocalService) throws IOException {
        AppiumDriverLocalService appiumService = appiumDriverLocalService;
        String appiumServiceUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service Address ************************: - " + appiumServiceUrl);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("newCommandTimeout", "45000");
//        desiredCapabilities.setCapability("fullReset", "true");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        desiredCapabilities.setCapability("useNewWDA", "true");
        desiredCapabilities.setCapability("startIWDP", "true");
        desiredCapabilities.setCapability("appium-version", "1.9.0");
        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("platformVersion", "12.0");
        desiredCapabilities.setCapability("deviceName", "iPhone XS Max");
        String AppPath = new File("src/test/resources/ShopBack.app").getCanonicalPath();
        desiredCapabilities.setCapability(MobileCapabilityType.APP, AppPath);
        IOSDriver driver = new IOSDriver(new URL(appiumServiceUrl), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver;
    }


    public AppiumDriverLocalService getAppiumDriverLocalService() {
        AppiumDriverLocalService appiumService = AppiumDriverLocalService.buildDefaultService();
        appiumService.start();
        return appiumService;
    }

    public String captureScreenShot(WebDriver browser) throws IOException {
        File scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(new File("test-output").getAbsolutePath() + File.separator
                + Reporter.getCurrentTestResult().getMethod().getMethodName() + ".jpg"));
        String scrBase64Image = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BASE64);
        Reporter.log(
                "<br> <img src=\"data:image/png;base64," + scrBase64Image + "\" height=\"700\" width=\"800\"> <br>");
        //System.out.println("<img src=�data:image/png;base64," + scrBase64Image + "�>");
        return scrBase64Image;

    }


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {


    }


    @AfterSuite(alwaysRun = true)
    public void sessionTearDown() {

    }
}