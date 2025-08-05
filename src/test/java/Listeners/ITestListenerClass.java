package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogsUtils.info("TC "+ result.getTestName() +" started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("TC "+ result.getTestName() +" passed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtils.info("TC "+ result.getTestName() +" skipped");
    }
}
