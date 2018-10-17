package Pages;

import AppFramework.AppAction;
import AppFramework.Locator;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.SingleTapAction;
import org.testng.Reporter;

import java.util.Random;

public class LoginPage extends AppAction {
    public LoginPage(IOSDriver driver) {
        this.driver = driver;
    }

    Locator selectRegion() {
        return new Locator(By.xpath("//android.widget.TextView[@text='Singapore']"), "Singapore");
    }

    Locator selectButton() {
        return new Locator(By.xpath("//XCUIElementTypeButton[@name='continue']"), "Select Button");
    }

    Locator emailId() {
        return new Locator(By.xpath("//XCUIElementTypeTextField[@name='email']"), "Email-Id Text Box");
    }

    Locator passwordTextBox() {
        return new Locator(By.xpath("//XCUIElementTypeSecureTextField[@name='password']"), "Password Text Box");
    }

    Locator signUpButton() {
        return new Locator(By.xpath("//android.widget.Button[contains(@resource-id,'button_account')]"), "Sign Up Button");
    }

    Locator signInOrSignUpButton() {
        return new Locator(By.xpath("//XCUIElementTypeButton[@name='login']"), "Sign In Or Sign Up Button");
    }

    Locator getStarted() {
        return new Locator(By.xpath("//XCUIElementTypeButton[@name='Get Started']"), "Get Started Button");
    }

    Locator accountPageButton() {
        return new Locator(By.xpath("//XCUIElementTypeButton[@name='account']"), "My Account Navigator");
    }

    Locator accountName() {
        return new Locator(By.xpath("//XCUIElementTypeNavigationBar//XCUIElementTypeOther"), "My Account Name");
    }

    Locator quickAccessLayout() {
        return new Locator(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'quick_access_panel')]"), "Quick Access Layout");
    }

    Locator notificationPopUp() {
        return new Locator(By.name("No thanks"), "No Thanks");
    }

/****************************************Methods ****************************************/



    public String doSignUp() throws IllegalAccessException, InstantiationException, InterruptedException {
        slectFromPickerWheel("Singapore");
        click(selectButton());
        if (waitUntilDisplayed(getStarted(), 3)) {
            click(getStarted());
        }
        Random random = new Random();
        String emailId = "venkatesh" + random.nextInt(10000) + "@gmail.com";
        EnterValue(emailId(), emailId);
        EnterValue(passwordTextBox(), "123456789");
        click(signInOrSignUpButton());
        return emailId;
    }

    public boolean checkLoginSuccessfully(String emailId) throws IllegalAccessException, InstantiationException {
        clickOnSpecificPoint(113, 379);
        click(accountPageButton());
        if (waitUntilDisplayed(notificationPopUp(), 2)) {
            click(notificationPopUp());
        }
        String accountName = getText(accountName());
        Reporter.log("Expected output :- " + emailId + " Got From Ui:- " + accountName, true);
        if (emailId.contains(accountName) && !accountName.isEmpty()) {
            return true;
        }
        return false;
    }
}
