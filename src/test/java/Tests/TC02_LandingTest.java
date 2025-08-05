package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;
import Listeners.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC02_LandingTest {
    private final String USERNAME = DataUtils.jsonData("validLogin","username");
    private final String PASSWORD = DataUtils.jsonData("validLogin","password");
    private Set<Cookie> cookies;
    @BeforeClass
    public void login () throws IOException {
        setupDriver(DataUtils.propertiesData("environment","Browser"));
        LogsUtils.info("Edge is opened");
        getDriver().get(DataUtils.propertiesData("environment","LoginUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        cookies = Utility.getAllCookies(getDriver());
        quitDriver();
    }

    @BeforeMethod
    public void setUp () throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.propertiesData("environment","Browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
//        setupDriver(DataUtils.propertiesData("environment","Browser")); //before running with maven
        LogsUtils.info("Edge is opened");
        getDriver().get(DataUtils.propertiesData("environment","LandingPageUrl"));
        LogsUtils.info("Page is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Utility.restoreAllCookies(getDriver(),cookies);
        getDriver().navigate().refresh();
    }
    @Test
    public void checkingNumberOfSelectedProducts () {
        Allure.addAttachment("manual log", "This is a test log");
        LogsUtils.info("Hello from login test");
        new P02_LandingPage(getDriver()).addingItemsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingSelectedProductsWithAllProducts());
    }
    @Test
    public void addingRandomProducts (){

        new P02_LandingPage(getDriver()).selectingRandomProducts(3,6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingSelectedProductsWithAllProducts());

    }
    @Test
    public void clickingOnCartIcon() throws IOException {

        new P02_LandingPage(getDriver()).clickOnCartPage();
        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.propertiesData("environment","CartUrl")));

    }

    @AfterMethod
    public void quit (){
        quitDriver();
    }
    @AfterClass
    public void deleteSession(){
        cookies.clear();
    }

}



