package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void closePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Later')]"))).click();

            System.out.println("Later popup closed");

        } catch (Exception e) {
            System.out.println("No Later popup");
        }
    }

    public void handleLoginPopup() {

        try {
            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            popupWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Login Banner']")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg[data-testid='closeIcon']")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter mobile number']"))
            ));

            Boolean clicked = popupWait.until(driver -> {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                return (Boolean) js.executeScript(
                        "var closeIcon = document.querySelector(\"svg[data-testid='closeIcon']\");" +
                        "if (closeIcon) {" +
                        "   var closeButton = closeIcon.closest('div.c-pointer') || closeIcon.parentElement;" +
                        "   closeButton.click();" +
                        "   return true;" +
                        "}" +
                        "return false;"
                );
            });

            if (clicked) {
                System.out.println("Login popup closed");

                popupWait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector("img[alt='Login Banner']")
                ));
            }

        } catch (Exception e) {
            System.out.println("No login popup or close icon not found");
        }
    }
    public boolean isSearchFormDisplayed() {

        try {
            boolean fromCity = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Where from?']"))).isDisplayed();

            boolean toCity = driver.findElement(
                    By.xpath("//input[@placeholder='Where to?']")).isDisplayed();
            
            boolean searchButton = driver.findElement(
                    By.xpath("//h4[text()='Search flights']/ancestor::button")).isDisplayed();

            System.out.println("Home/Search form loaded successfully");

            return fromCity && toCity && searchButton;

        } catch (Exception e) {
            System.out.println("Home/Search form not loaded");
            return false;
        }
    }
    
}


/*This HomePage class is designed using Page Object Model. 
 * It receives the WebDriver from the base class through the constructor and uses explicit waits to handle dynamic elements. 
 * The closePopup method closes the optional Later popup. 
 * The handleLoginPopup method waits for login popup elements and uses JavascriptExecutor to close the SVG close icon because normal Selenium click may not be reliable there. 
 * After closing, it waits for the popup to disappear. 
 * The isSearchFormDisplayed method validates whether the From city input, To city input, and Search flights button are visible. 
 * It returns a boolean value so my test class can use it directly in assertions. 
 * This improves code reusability, readability, and test stability.
 */