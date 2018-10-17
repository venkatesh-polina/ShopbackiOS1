package TestCaseSuite;

import AppFramework.AppAction;
import AppFramework.CustomSoftAssert;
import AppFramework.FrameWork;
import Pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class Z_SanitySuite extends AppAction {

    @Test(groups = {"Sanity","Login"})
    public void testingMethod() throws IOException {
        FrameWork frameWork = new FrameWork();
        AppiumDriverLocalService appiumServices = frameWork.getAppiumDriverLocalService();
        IOSDriver driver = frameWork.getiOSDriver(appiumServices);
        CustomSoftAssert customSoftAssert = new CustomSoftAssert(driver);
        try {
            LoginPage loginPage = new LoginPage(driver);
            String emailId = loginPage.doSignUp();
            customSoftAssert.assertTrue(loginPage.checkLoginSuccessfully(emailId));
        } catch (Throwable e) {
            Reporter.log(e.toString(), true);
            Assert.fail();
        } finally {
            appiumServices.stop();

        }


    }

}
