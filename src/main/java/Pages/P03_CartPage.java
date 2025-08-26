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
    private static final By removeButton = By.xpath("//button[contains(@class,'cart_button')]");
    private static final By numberOfProductsOnCartIcon = By.xpath("//span[contains(@class,'fa-layers-counter')]");
    static List<WebElement>removeButtons;

    public P03_CartPage (WebDriver driver){
        this.driver=driver;
    }


// TODO: Calculate total price of all cart items and return as String
   public String totalPriceOfCartProducts (){
        try {
            // Find all price elements of products currently in the cart
            List<WebElement> priceOfCartProducts = driver.findElements(allCartItemsPrice);
            // Loop through each product in the cart
            for (int i = 1 ;i<=priceOfCartProducts.size();i++)
            {
                // Locate the price element of the i product
                By cartElements = By.xpath("(//button[.=\"REMOVE\"]//preceding-sibling::div[@class=\"inventory_item_price\"])[" +i+ "]");
                // Get the text of the price element
                String text = Utility.getText(driver,cartElements);
                // Remove the "$" symbol and convert the price to float, then add to totalCartPrice
                totalCartPrice += Float.parseFloat(text.replace("$",""));
            }
            LogsUtils.info("Total Cart Price : "+ totalCartPrice);
            return String.valueOf(totalCartPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
   }

// TODO: Check if total cart price is correct
   public boolean comparingPrices (String selectProductsPrice){
        return totalPriceOfCartProducts().equals(selectProductsPrice);
   }

// TODO: Click on checkout button and go to Checkout page
   public P04_CheckoutPage clickingOnCheckoutButton(){
        Utility.clickOnElement(driver,checkOutButton);
        return new P04_CheckoutPage(driver);
   }

// TODO: Get the number of products currently in the cart
   public int getCartCount (){
        String countText = Utility.getText(driver,numberOfProductsOnCartIcon);
        return Integer.parseInt(countText);
   }

// TODO: Click on remove button
   public P03_CartPage clickOnRemoveButton(){
        Utility.clickOnElement(driver,removeButton);
        return new P03_CartPage(driver);
   }

// TODO: Remove all items from the cart
   public List<WebElement> removingAllItemsFromCart (){
       // Find all "Remove from cart" buttons currently present on the page
       removeButtons = driver.findElements(removeButton);
       // Loop over the number of remove buttons initially found
       // Note: i runs from 1 to the size of the list, but we always click the first button (index 1)
       for (int i = 1 ; i<=removeButtons.size();i++) {
           By removeButton = By.xpath("(//button[contains(@class,'cart_button')])[1]");
           // Click the button using the Utility method
           Utility.clickOnElement(driver,removeButton);
       }
       // Return the list of remove buttons after all removals
       // This list should be empty if all cart items were successfully removed
      return removeButtons = driver.findElements(removeButton);
   }

}
