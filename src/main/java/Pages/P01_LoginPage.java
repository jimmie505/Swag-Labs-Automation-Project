package Pages;

import DriverFactory.DriverFactory;
import Utilities.Utility;
import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static DriverFactory.DriverFactory.getDriver;

public class P01_LoginPage {
private WebDriver driver;
public P01_LoginPage (WebDriver driver){
    this.driver=driver;
}
    private static final By username = By.id("user-name");
    private static final By password = By.id("password");
    private static final By loginButton = By.id("login-button");




    public P01_LoginPage enterUsername (String  usernameText){
        Utility.sendData(driver,username,usernameText);
        return this;
    }
    public P01_LoginPage enterPassword (String  passwordText){
        Utility.sendData(driver,password,passwordText);
        return this;
    }
    public P02_LandingPage clickLoginButton (){
        Utility.clickOnElement(driver,loginButton);
        return new P02_LandingPage(getDriver());
    }
    public boolean assertLoginTC (String expectedValue){
      return   driver.getCurrentUrl().equals(expectedValue);
    }
}
