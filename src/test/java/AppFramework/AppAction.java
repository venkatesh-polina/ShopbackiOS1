package AppFramework;

import android.text.method.Touch;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AppAction {

    protected IOSDriver driver;

    static int DefaultTime = 60;

    protected String getAttribute(Locator locator, String Attribute) {
        String attributevalue;
        if (locator.getElement() == null) {
            WebElement element = driver.findElement(locator.getBy());
            attributevalue = element.getAttribute(Attribute);
        } else {
            System.out.println(locator.getBy());
            System.out.println(locator.getElement());
            WebElement element = locator.getElement().findElement(locator.getBy());
            attributevalue = element.getAttribute(Attribute);
        }
        return attributevalue;
    }

    protected void EnterValue(Locator locator, String value) {

        WebDriverWait webdriverWait = new WebDriverWait(driver, 60);
        webdriverWait.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
        if (locator.getElement() == null) {
            driver.findElement(locator.getBy()).sendKeys(value);
            //	((IOSElement)driver.findElement(locator.getBy())).setValue(value);
        } else {
            ((MobileElement) locator.getElement().findElement(locator.getBy())).setValue(value);
        }
        Reporter.log("Entered value  '" + value + "' in '" + locator.getName() + "'", true);

    }

    protected void click(Locator locator) throws InstantiationException, IllegalAccessException {

        WebDriverWait webdriverWait = new WebDriverWait(driver, DefaultTime);
        if (locator.getElement() == null) {

            webdriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            driver.findElement(locator.getBy()).click();
        } else {
            WebElement element = locator.getElement().findElement(locator.getBy());
            //       webdriverWait.until(ExpectedConditions.visibilityOf(element));
            driver.manage().timeouts().implicitlyWait(DefaultTime, TimeUnit.SECONDS);
            element.click();
        }
        Reporter.log("Clicked On " + locator.getName() + "", true);
    }

    public void clickOnDesiredElement(List<WebElement> element, int index) {
        element.get(index);
        Reporter.log("Clicked On " + index + "in the list", true);

    }

    protected String getText(Locator locator) {
        String Text = "No data";
        try {
//			WebDriverWait wait = new WebDriverWait(driver, DefaultTime);
//			wait.until(ExpectedConditions.presenceOfElementLocated(locator.getBy()));
            if (locator.getElement() == null) {
                WebElement webElement = driver.findElement(locator.getBy());
                Text = webElement.getText();
            } else {
                WebElement webElement = locator.getElement().findElement(locator.getBy());
                Text = webElement.getText();
            }
            System.out.println(Text);
            return Text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Text;
    }

    protected String getText(WebElement element) {
        String Text = "No data";
        try {
//			WebDriverWait wait = new WebDriverWait(driver, DefaultTime);
//			wait.until(ExpectedConditions.presenceOfElementLocated(locator.getBy()));
            Text = element.getText();
            System.out.println(Text);
            return Text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Text;
    }

    protected void switchToWebView(Locator locator) {
        Set<String> availableContexts = driver.getContextHandles();
        Reporter.log("Total No of Context Found After we reach to WebView = " + availableContexts.size(), true);
        for (String context : availableContexts) {
            if (context.contains("WEBVIEW")) {
                Reporter.log("Context Name is " + context, true);
                driver.context(context);
                if (waitUntilDisplayed(locator, 2)) {
                    break;
                } else {
                    switchToNativeApp();
                }
            }
        }
    }

    protected void switchToNativeApp() {
        driver.context("NATIVE_APP");
        Reporter.log("Context switched to " + "NATIVE_APP", true);
    }

    protected boolean waitUntilDisplayed(Locator locator, int Timeout) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, Timeout);
        driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
        try {
            if (locator.getElement() == null) {
                driver.findElement(locator.getBy());
                webdriverWait.until(ExpectedConditions.presenceOfElementLocated(locator.getBy()));
            } else {
                WebElement element = locator.getElement().findElement(locator.getBy());
                webdriverWait.until(ExpectedConditions.visibilityOf(element));
            }
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(DefaultTime, TimeUnit.SECONDS);
        }
    }

    public void bringElementIntoViewUp(Locator locator, int ScrollCount) {
        Dimension dimensions = driver.manage().window().getSize();

        System.out.println(dimensions);

        Double screenHeightStart = dimensions.getHeight() * 0.8;

        int scrollStart = screenHeightStart.intValue();

        Double screenHeightEnd = dimensions.getHeight() * 0.4;

        int scrollEnd = screenHeightEnd.intValue();
        int i = 1;
        while (!waitUntilDisplayed(locator, 3)) {
            //      driver.swipe(0, scrollEnd, 0, scrollStart, 1000);
            TouchAction touchAction = new TouchAction(driver);
            touchAction
                    .press(PointOption.point(0, scrollEnd))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(0, scrollStart)).release().perform();
            i++;
            if (i >= ScrollCount) {
                break;
            }
        }

    }

    public void bringElementIntoViewDown(Locator locator, int ScrollCount) throws InterruptedException, IllegalAccessException, InstantiationException {


        Dimension dimensions = driver.manage().window().getSize();

        //System.out.println(dimensions);

        Double screenHeightStart = dimensions.getHeight() * 0.9;

        int scrollStart = screenHeightStart.intValue();

        Double screenHeightEnd = dimensions.getHeight() * 0.2;

        int scrollEnd = screenHeightEnd.intValue();
        int i = 1;
        while (!waitUntilDisplayed(locator, 3)) {
            //     driver.swipe(0, scrollStart, 0, scrollEnd, 1000);
            TouchAction touchAction = new TouchAction(driver);
            touchAction
                    .press(PointOption.point(0, scrollStart))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(0, scrollEnd)).release().perform();
            System.out.println("Bringing " + locator.getName() + " into view");
            i++;
            if (i >= ScrollCount) {
                break;
            }
        }
    }

    public void bringElementIntoViewHorizontally(Locator ToBeFind, Locator ScrollableArea, int ScrollCount) throws InterruptedException, IllegalAccessException, InstantiationException {

        WebElement element = driver.findElement(ScrollableArea.getBy());
        int uppery = element.getLocation().getY();
        int lowery = uppery + element.getSize().getHeight();
        Dimension dimensions = driver.manage().window().getSize();
        int width = dimensions.getWidth();
        int y = (uppery + lowery) / 2;
        int startx = (int) (width * 0.75);
        int endx = (int) (width * 0.35);
        int i = 1;
        while (!waitUntilDisplayed(ToBeFind, 3)) {
            //    driver.swipe(startx, y, endx, y, 1000);
            TouchAction touchAction = new TouchAction(driver);
            touchAction
                    .press(PointOption.point(startx, y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endx, y)).release().perform();
            i++;
            if (i >= ScrollCount) {
                break;
            }
        }


    }

    public void waitUntilElementDisappear(Locator locator, int Timeout) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Timeout);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[@style='overflow:
            // auto;']")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getBy()));
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
    }

    protected List<WebElement> getWebElements(Locator locator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
        } catch (Exception e) {
            Reporter.log(" list of element not displayed with locator " + locator.getName() + "", true);
        }
        List<WebElement> webElementList;
        if (locator.getElement() == null) {
            webElementList = driver.findElements(locator.getBy());
        } else {
            webElementList = locator.getElement().findElements(locator.getBy());
        }
        System.out.println("size aahe " + webElementList.size());
        if (!webElementList.isEmpty()) {
            Reporter.log("Viewed all list of " + locator.getName() + "", true);
        } else {
            Reporter.log("Unable to view list of " + locator.getName() + "", true);
        }
        driver.manage().timeouts().implicitlyWait(DefaultTime, TimeUnit.SECONDS);
        return webElementList;
    }

    protected void ClearValue(Locator locator) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, 60);
        webdriverWait.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
        driver.findElement(locator.getBy()).clear();
        Reporter.log("Cleared value from '" + locator.getName() + "'", true);
    }

    protected void wait(int wait, Locator locator) throws InterruptedException {
        Thread.sleep(wait);
    }

    protected void slectFromPickerWheel(String input) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, 60);
        webdriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElementByClassName("XCUIElementTypePickerWheel")));
        driver.findElementByClassName("XCUIElementTypePickerWheel").sendKeys(input);
        Reporter.log("Selected From Picker Wheel :- " + input);

    }

    protected void clickOnSpecificPoint(int x, int y) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(PointOption.point(x, y)).release().perform();
    }

}

