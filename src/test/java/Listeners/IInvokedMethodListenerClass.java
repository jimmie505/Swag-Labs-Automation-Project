package Listeners;

import Pages.P02_LandingPage;
import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements org.testng.IInvokedMethodListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        Utility.takingFullScreenShot(getDriver(), new P02_LandingPage(getDriver()).getiingCartIcon());
        File logFile = Utility.getLatestFile(LogsUtils.LOGS_PATH);
        try {
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Paths.get(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus()==ITestResult.FAILURE) {
            LogsUtils.info("TC " + testResult.getTestName() + " failed");
            Utility.takingScreenshot(getDriver(), testResult.getName());
        }
    }

}
