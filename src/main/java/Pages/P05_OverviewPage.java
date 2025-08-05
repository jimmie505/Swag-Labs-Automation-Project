package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final By itemTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By FinishButton = By.linkText("FINISH");
    //private final By FinishButton2 = By.xpath("//a[contains(@class, 'cart_button')]");
    WebDriver driver;
    public P05_OverviewPage(WebDriver driver){
        this.driver=driver;
    }


    public Float subtotal (){
        return Float.parseFloat(Utility.getText(driver,itemTotal).replace("Item total: $",""));
    }
    public Float tax (){
        return Float.parseFloat(Utility.getText(driver,tax).replace("Tax: $",""));
    }
    public Float total (){
        LogsUtils.info("Actual Total Price : " + subtotal()+tax());

        return Float.parseFloat(Utility.getText(driver,total).replace("Total: $",""));
    }
    public String sum (){
        LogsUtils.info("Calculated Total Price : " + subtotal()+tax());
        return String.valueOf(subtotal()+tax());
    }

    public boolean comparing (){
        return String.valueOf(total()).equals(sum());
    }
    public P06_FinishPage clickOnFinishButton (){
        Utility.clickOnElement(driver,FinishButton);
        return new P06_FinishPage (driver);
    }





}
