package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class P06_FinishPage {
    private final By thanksMessage = By.xpath("//h2");
    WebDriver driver;
    public P06_FinishPage(WebDriver driver) {
        this.driver = driver;
    }

    public Boolean checkingMessegeCorrectness (){
        LogsUtils.info("the Thanks Messege : " + Utility.getText(driver,thanksMessage));
        return Utility.getText(driver,thanksMessage).equals("THANK YOU FOR YOUR ORDER");
    }

    public Boolean checkingMessegeVisibilty (){
        LogsUtils.info("the Thanks Messege : " + Utility.getText(driver,thanksMessage));
        return Utility.findWebElement(driver,thanksMessage).isDisplayed();
    }

}
