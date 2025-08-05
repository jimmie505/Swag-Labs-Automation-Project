package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import Listeners.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC03_CartTest {
    private final String USERNAME = DataUtils.jsonData("validLogin","username");
    private final String PASSWORD = DataUtils.jsonData("validLogin","password");
    @BeforeMethod
    public void setUp () throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.propertiesData("environment","Browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
       // setupDriver(DataUtils.propertiesData("environment","Browser")); //before running with maven
        LogsUtils.info("Edge is opened");
        getDriver().get(DataUtils.propertiesData("environment","LoginUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
      @Test
    public void copmaringPricesTC (){
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton()
                .selectingRandomProducts(3,6)
                .clickOnCartPage().totalPriceOfCartProducts();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));

    }

    @AfterMethod
    public void quit (){
        quitDriver();
    }
}
