package Tests;

import Pages.P01_LoginPage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import Listeners.*;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC04_CheckoutTest {
    private final String USERNAME = DataUtils.jsonData("Login","validUsername");
    private final String PASSWORD = DataUtils.jsonData("Login","validPassword");
    private final String FIRSTNAME = DataUtils.jsonData("validCheckout","firstName") + "-" + Utility.getTimeStamp();
    private final String LASTNAME =  DataUtils.jsonData("validCheckout","lastName") + "-" + Utility.getTimeStamp();
    private final String POSTALCODE = new Faker().number().digits(5);
    @BeforeMethod
    public void setUp () throws IOException {
        setupDriver(DataUtils.propertiesData("environment","Browser"));
        LogsUtils.info("Edge is opened");
        getDriver().get(DataUtils.propertiesData("environment","LoginUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @Test
    public void validCheckout () throws IOException {
                new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton()
                .selectingRandomProducts(3,6)
                .clickOnCartPage()
                .clickingOnCheckoutButton()
                .enterFristName(FIRSTNAME)
                .enterLastName(LASTNAME)
                .enterPostalCode(POSTALCODE)
                .clickingOnContinueButton();
        Assert.assertTrue(Utility.verifyUrl(getDriver(), DataUtils.propertiesData("environment","CheckoutOverviewUrl")));

    }

    @AfterMethod
    public void quit (){
        quitDriver();
    }
}
