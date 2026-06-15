package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void enterFromCity(String city) {
        WebElement from = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='Where from?']")));

        from.click();
        from.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        from.sendKeys(city);

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(),'" + city + "')]"))).click();
    }

    public void enterToCity(String city) {
        WebElement to = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='Where to?']")));

        to.click();
        to.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        to.sendKeys(city);

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(),'" + city + "')]"))).click();
    }

    public void selectDate() {
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@data-testid='dateSelectOnward']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))])[2]")))
            .click();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//h4[text()='Search flights']/ancestor::button"))).click();
    }
}

/*SearchPage is a Page Object class for the Cleartrip flight search form. 
 * It uses the same WebDriver instance passed from the base class and uses explicit waits for stability. 
 * The enterFromCity and enterToCity methods are reusable because they accept city names as parameters. 
 * They clear the input field, type the city, and select the matching suggestion. 
 * The selectDate method opens the calendar and selects an available non-disabled date. 
 * The clickSearch method clicks the Search flights button using the actual button ancestor. 
 * This keeps my test case simple and makes the framework more maintainable.
 * */
