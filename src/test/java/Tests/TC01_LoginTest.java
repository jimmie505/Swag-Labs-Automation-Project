package Tests;

import Listeners.*;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.propertiesData;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC01_LoginTest {
    private final String VAlID_USERNAME = DataUtils.jsonData("Login","validUsername");
    private final String VALID_PASSWORD = DataUtils.jsonData("Login","validPassword");
    private final String INVALID_USERNAME = DataUtils.jsonData("Login","invalidUsername");
    private final String INVALID_PASSWORD = DataUtils.jsonData("Login","invalidPassword");
    @BeforeMethod (alwaysRun = true)
    public void setUp () throws IOException {

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : propertiesData("environment","Browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge is opened");
        getDriver().get(propertiesData("environment","LoginUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @Test
    public void validLogin () throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(VAlID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver())
              .assertLoginTC(propertiesData("environment","LandingPageUrl")));
    }
    @Test
    public void inValidLogin () throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(INVALID_USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickLoginButton();
        Assert.assertFalse(new P01_LoginPage(getDriver())
              .assertLoginTC(propertiesData("environment","LandingPageUrl")));
    }
    @Test
    public void inValidLogin2 () throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(VAlID_USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickLoginButton();
        Assert.assertFalse(new P01_LoginPage(getDriver())
              .assertLoginTC(propertiesData("environment","LandingPageUrl")));
    }

    @Test
    public void inValidLogin3 () throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(INVALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLoginButton();
        Assert.assertFalse(new P01_LoginPage(getDriver())
              .assertLoginTC(propertiesData("environment","LandingPageUrl")));
    }

    @AfterMethod
    public void quit (){
     quitDriver();
    }


}
