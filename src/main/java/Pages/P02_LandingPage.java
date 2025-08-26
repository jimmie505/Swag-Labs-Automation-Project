package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class P02_LandingPage {
  WebDriver driver ;
  static float totalPrice = 0;
  private static final By addToCartButtonForAllProducts = By.xpath("//button[contains(@class,'btn_inventory')]");
  private static final By numberOfProductsOnCartIcon = By.xpath("//span[contains(@class,'fa-layers-counter')]");
  private static final By numberOfSelectedProducts = By.xpath("//button[contains(@class,'btn_secondary')]");
  private static final By cartIcon = By.cssSelector("#shopping_cart_container > a > svg");
  private static final By allItemPrice = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
  private static final By filterLohi = By.xpath("//option[@value='lohi']");
  private static final By itemPrices = By.className("inventory_item_price");
  private static List<WebElement> allProduct ;
  private static List<WebElement> selectedProduct ;

  public By getiingCartIcon (){
      return cartIcon;
  }

    public P02_LandingPage(WebDriver driver) {
        this.driver=driver;
    }

    public P02_LandingPage addingItemsToCart (){
        allProduct = driver.findElements(addToCartButtonForAllProducts);
        LogsUtils.info("number of all products : " + allProduct.size());
        for (int i=1;i<=allProduct.size();i++) {
            By addToCartButtonForAllProducts = By.xpath("(//button[contains(@class,'btn_inventory')])[" +i+ "]"); //dynamic locator
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);
        }
            return this;
    }

    public String numberOfProductsOnCart (){
        try {
            LogsUtils.info("number of product on cart icon : "+ Utility.getText(driver,numberOfProductsOnCartIcon));
            return Utility.getText(driver,numberOfProductsOnCartIcon);

        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }

    }


    public String numberOfSelectedProducts(){
        try {
            selectedProduct = driver.findElements(numberOfSelectedProducts);
            LogsUtils.info("number of selected products : " + selectedProduct.size());
            return String.valueOf(selectedProduct.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }


    }
    public boolean comparingSelectedProductsWithAllProducts (){
        return numberOfProductsOnCart().equals(numberOfSelectedProducts());
    }

   public P02_LandingPage selectingRandomProducts (int numberOfProductNeeded, int upperBound){
      Set<Integer> randomProduct = Utility.generateUniqueNumbers( numberOfProductNeeded, upperBound);
      for (int random : randomProduct){
          LogsUtils.info("random number : " + random);
          By addToCartButtonForRandomProducts = By.xpath("(//button[contains(@class,'btn_inventory')])[" +random+ "]"); //dynamic locator
          Utility.clickOnElement(driver,addToCartButtonForRandomProducts );
      }
       return this;

   }
   public P03_CartPage clickOnCartPage(){
    Utility.clickOnElement(driver,cartIcon);
   return new P03_CartPage(driver);
   }

   public boolean verifyCartPageUrl(String expectedUrl){
      try {
          Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedUrl));
      } catch (Exception e) {
          return false;
      }
          return true;
   }

   public String getTotalPriceOfSelectedProducts (){
      try {
          totalPrice = 0;
          List <WebElement> allPrices = driver.findElements(allItemPrice);
          for (int i = 1 ; i <= allPrices.size() ; i++)
          {
              By elements = By.xpath("(//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price'])["+i+"]");
              String fullText = Utility.getText(driver,elements);
              totalPrice += Float.parseFloat(fullText.replace("$",""));
          }
          LogsUtils.info("Total Price : "+ totalPrice);
          return String.valueOf(totalPrice);
      } catch (Exception e) {
          LogsUtils.error(e.getMessage());
          return "0";
      }
   }
   public Boolean comparingItemsPrices(String prices){
      return getTotalPriceOfSelectedProducts().equals(prices);
   }

   public P02_LandingPage clickingOnFilterIcon (){
      Utility.clickOnElement(driver,filterLohi);
      Utility.generalWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices));
      return new P02_LandingPage(driver);
   }

   public List<Float> gettingItemsPrices(){
      List<WebElement> prices = driver.findElements(itemPrices);
      List<Float> pricesAfterFilter = new ArrayList<>();
      for (int i = 1;i<=prices.size();i++){
          By element = By.xpath("(//div [@class='inventory_item_price'])["+i+"]");
          String pp = Utility.getText(driver,element);
          float fValue = Float.parseFloat(pp.replace("$",""));
          pricesAfterFilter.add(fValue);
      }
      LogsUtils.info("Prices after filtering : "+ pricesAfterFilter.toString());
      return pricesAfterFilter;
   }


   public List<Float> sortedPrices(){
      List<WebElement>sortedPrices = driver.findElements(itemPrices);
      List<Float> sortedPricesFloat = new ArrayList<>();
       for (int i = 1;i<=sortedPrices.size();i++){
           By element = By.xpath("(//div [@class='inventory_item_price'])["+i+"]");
           String pp = Utility.getText(driver,element);
           float fValue = Float.parseFloat(pp.replace("$",""));
           sortedPricesFloat.add(fValue);
       }
      Collections.sort(sortedPricesFloat);
       LogsUtils.info("Actual Sorted Prices : "+ sortedPricesFloat.toString());
       return sortedPricesFloat;
   }

   public Boolean assertFiltering (){
      return gettingItemsPrices().equals(sortedPrices());
   }

        }









