package AppFramework;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class CustomSoftAssert extends SoftAssert {
    FrameWork framework = new FrameWork();
    IOSDriver webdriver ;

    public CustomSoftAssert(IOSDriver driver){
        webdriver=driver;
    }

    @Override
    public void onAssertSuccess(IAssert<?> iAssert) {
        Reporter.log("<p><font size=\"3\" color=\"green\">--Passed\"</font></p>",true);

    }

    @Override
    public void onAssertFailure(IAssert<?> iAssert, AssertionError assertionError) {
        try {
            Reporter.log("<p><font size=\"3\" color=\"red\">--Failed\"</font></p>", true);
            framework.captureScreenShot(webdriver);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void assertTrue(boolean condition, String message) {
        super.assertTrue(condition, message);
    }
}
