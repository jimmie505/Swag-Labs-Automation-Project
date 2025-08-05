package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    WebDriver driver;
    float totalCartPrice = 0;
    private static final By allCartItemsPrice = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private static final By checkOutButton = By.linkText("CHECKOUT");
    public P03_CartPage (WebDriver driver){
        this.driver=driver;
    }

   public String totalPriceOfCartProducts (){
        try {
            List<WebElement> priceOfCartProducts = driver.findElements(allCartItemsPrice);
            for (int i = 1 ;i<=priceOfCartProducts.size();i++)
            {
                By cartElements = By.xpath("(//button[.=\"REMOVE\"]//preceding-sibling::div[@class=\"inventory_item_price\"])[" +i+ "]");
                String text = Utility.getText(driver,cartElements);
                totalCartPrice += Float.parseFloat(text.replace("$",""));
            }
            LogsUtils.info("Total Cart Price : "+ totalCartPrice);
            return String.valueOf(totalCartPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
   }

   public boolean comparingPrices (String selectProductsPrice){
        return totalPriceOfCartProducts().equals(selectProductsPrice);
   }

   public P04_CheckoutPage clickingOnCheckoutButton(){
        Utility.clickOnElement(driver,checkOutButton);
        return new P04_CheckoutPage(driver);
   }

}
