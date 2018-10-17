package AppFramework;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestListner extends TestListenerAdapter {


    public void onTestStart(ITestResult result) {
        Reporter.log("*******************************************************************", true);
        Reporter.log("TEST CASE : " + result.getName().replace("_", " "), true);
        Reporter.log("*******************************************************************", true);

    }

    }
