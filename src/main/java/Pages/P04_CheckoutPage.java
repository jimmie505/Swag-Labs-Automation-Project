package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P04_CheckoutPage {
    WebDriver driver;
    private static final By firstName = By.id("first-name");
    private static final By lastName = By.id("last-name");
    private static final By postalCode = By.id("postal-code");
    private static final By continueButton = By.cssSelector("[type='submit']");

    public P04_CheckoutPage (WebDriver driver){
        this.driver=driver;
    }

    public P04_CheckoutPage enterFristName (String first){
        Utility.sendData(driver,firstName, first);
        return this;
    }
    public P04_CheckoutPage enterLastName (String last){
        Utility.sendData(driver,lastName, last);
        return this;
    }
    public P04_CheckoutPage enterPostalCode (String postal){
        Utility.sendData(driver,postalCode,postal );
        return this;
    }

    public P05_OverviewPage clickingOnContinueButton(){
        Utility.clickOnElement(driver,continueButton);
        return new P05_OverviewPage(driver);
    }





}
