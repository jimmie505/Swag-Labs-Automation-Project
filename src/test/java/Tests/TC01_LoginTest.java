package Tests;

import DriverFactory.DriverFactory;
import Listeners.*;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.IInvokedMethodListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC01_LoginTest {
    private final String USERNAME = DataUtils.jsonData("validLogin","username");
    private final String PASSWORD = DataUtils.jsonData("validLogin","password");
    @BeforeMethod
    public void setUp () throws IOException {

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.propertiesData("environment","Browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge is opened");
        getDriver().get(DataUtils.propertiesData("environment","LoginUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @Test
    public void validLogin () throws IOException {
        LogsUtils.info("Hello from login test");
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(DataUtils.propertiesData("environment","LandingPageUrl")));
    }

    @AfterMethod
    public void quit (){
     quitDriver();
    }


}
