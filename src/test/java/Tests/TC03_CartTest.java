package Tests;

import Pages.P01_LoginPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import Listeners.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TC03_CartTest {
    private final String USERNAME = DataUtils.jsonData("Login","validUsername");
    private final String PASSWORD = DataUtils.jsonData("Login","validPassword");
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
                .clickOnCartPage()
                .totalPriceOfCartProducts();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));

    }
    @Test
    public void removingCartItem (){
      int countBefore =  new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton()
                .selectingRandomProducts(3,6)
                .clickOnCartPage().getCartCount();
        LogsUtils.info("cart count before : "+ countBefore);
      new P03_CartPage(getDriver()).clickOnRemoveButton();
      int countAfter = new P03_CartPage(getDriver()).getCartCount();
      LogsUtils.info("cart count after : "+ countAfter);
        Assert.assertEquals(countAfter,countBefore-1);

    }
    @Test
    public void removingAllCartItems (){
        List<WebElement> cartElements = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton()
                .selectingRandomProducts(3,6)
                .clickOnCartPage()
                .removingAllItemsFromCart();
        Assert.assertTrue(cartElements.isEmpty(),"Cart is not empty !!!");
    }



    @AfterMethod
    public void quit (){
        quitDriver();
    }
}
