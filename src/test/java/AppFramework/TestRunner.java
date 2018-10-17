package AppFramework;

import TestCaseSuite.Z_SanitySuite;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class TestRunner {

    public static void main(String[] args) throws Throwable {
        TestListenerAdapter tla = testngPlay();

    }

    private static TestListenerAdapter testngPlay() {
        try {
            String TestcaseIds = "Sanity";
            TestNG testng = new TestNG();
            TestListner testListner = new TestListner();
            testng.addListener(testListner);
            //no of parallel testcases can be run is defined by this
            testng.setThreadCount(1);
            testng.setDataProviderThreadCount(1);
            testng.setParallel("methods");
            testng.setGroups(TestcaseIds);

            testng.setVerbose(12);
            testng.setTestClasses(new Class[]{FrameWork.class, Z_SanitySuite.class});
            testng.addListener(testListner);
            testng.run();
            return testListner;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
