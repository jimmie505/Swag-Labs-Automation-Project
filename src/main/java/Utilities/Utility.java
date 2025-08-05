package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.PropertySource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
   private static final String path = "test-outputs/Screenshots/";

   public static Set <Cookie> getAllCookies (WebDriver driver){
         return    driver.manage().getCookies();
   }

   public static void restoreAllCookies (WebDriver driver, Set<Cookie> cookies){
       for (Cookie cookie : cookies)
         driver.manage().addCookie(cookie);
   }

    //TODO : clicking on elements
    public static void clickOnElement (WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions
                        .elementToBeClickable(locator));
        driver.findElement(locator).click();
    }
    //TODO : sending data to elements
    public static void sendData (WebDriver driver,By locator, String Data){
        new  WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(Data);
    }

    //TODO : getting text function
    public static String getText (WebDriver driver ,By locator){
        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //TODO : general wait
    public static WebDriverWait generalWait(WebDriver driver){
        return new WebDriverWait(driver,Duration.ofSeconds(5));
    }

    public static WebElement byToWebElement (WebDriver driver,By locator){
        return driver.findElement(locator);
    }

    public static void scrolling (WebDriver driver , By locator){
        new Actions(driver).scrollToElement(byToWebElement(driver,locator)).perform();
    }


    public static void scrollingJS (WebDriver driver, By locator){
        ((JavascriptExecutor)driver).executeScript("argument[0].scrollIntoView();",byToWebElement(driver,locator));
    }

    //TODO : taking screenshot
    public static void takingScreenshot (WebDriver driver , String screenshotName) {
        try {
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File target = new File(path + screenshotName+"-"+ getTimeStamp() + ".png");
            FileUtils.copyFile(src,target);
            Allure.addAttachment(screenshotName, Files.newInputStream(Paths.get(target.getPath())));
        }catch (Exception e){
            LogsUtils.info(e.getMessage());
        }
    }
    public static void takingFullScreenShot(WebDriver driver, By locator){
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(byToWebElement(driver,locator))
                    .save(path);
        } catch (Exception e) {
            LogsUtils.info(e.getMessage());
        }

    }

    public static String getTimeStamp(){
        return new SimpleDateFormat ("yyyy-MM-dd-h-mm-ssa").format(new Date());
    }

    public static void selectingFromDropdown(WebDriver driver,By locator,String option){
        new Select(byToWebElement(driver,locator)).selectByValue(option);
    }

    public static int creatingRandomNumber (int upperBound){
        return new Random().nextInt(upperBound)+1 ;
    }

    public static Set<Integer> generateUniqueNumbers (int numberOfProductNeeded, int upperBound){
        Set<Integer> generateRandomNumbers = new HashSet<>();
        while (generateRandomNumbers.size() < numberOfProductNeeded) {
            int randomNumber = creatingRandomNumber(upperBound);
            generateRandomNumbers.add(randomNumber);
        }
        return generateRandomNumbers;
    }

    public static boolean verifyUrl (WebDriver driver, String expectedUrl){
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static WebElement findWebElement (WebDriver driver, By locator){
        return driver.findElement(locator);
    }



    public static File getLatestFile (String folderPath){
        File folder = new File(folderPath);
        File [] files = folder.listFiles();
        assert files != null;
        if (files.length==0)
            return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }
}
