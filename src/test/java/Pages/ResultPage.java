package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ResultPage {

    WebDriver driver;
    WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public boolean isFlightsDisplayed() {

        try {
            // Wait until result page content appears
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("results"),
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[contains(text(),'Sort') or contains(text(),'Departure') or contains(text(),'Recommended') or contains(text(),'Non-stop')]")
                    )
            ));

            // Wait for price to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'₹') or contains(text(),'INR') or contains(text(),'Rs')]")
            ));

            // Check common airline names
            List<WebElement> airlines = driver.findElements(
                    By.xpath("//*[contains(text(),'IndiGo') or contains(text(),'Air India') or contains(text(),'Akasa') or contains(text(),'SpiceJet') or contains(text(),'Alliance Air')]")
            );

            List<WebElement> prices = driver.findElements(
                    By.xpath("//*[contains(text(),'₹') or contains(text(),'INR') or contains(text(),'Rs')]")
            );

            int visibleAirlines = 0;
            int visiblePrices = 0;

            for (WebElement airline : airlines) {
                if (airline.isDisplayed()) {
                    visibleAirlines++;
                    System.out.println("Airline found: " + airline.getText());
                }
            }

            for (WebElement price : prices) {
                if (price.isDisplayed()) {
                    visiblePrices++;
                    System.out.println("Price found: " + price.getText());
                }
            }

            System.out.println("Visible airline count: " + visibleAirlines);
            System.out.println("Visible price count: " + visiblePrices);

            return visibleAirlines > 0 && visiblePrices > 0;

        } catch (Exception e) {
            System.out.println("Flights not found because locator/wait failed");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public boolean isAirlinePresent(String expectedAirline) {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'₹') or contains(text(),'INR') or contains(text(),'Rs')]")
            ));

            String airlineXpath =
                    "//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
                    expectedAirline.toLowerCase() + "')]";

            List<WebElement> airlineElements = driver.findElements(By.xpath(airlineXpath));

            for (WebElement airline : airlineElements) {
                if (airline.isDisplayed()) {
                    System.out.println("Expected airline found: " + airline.getText());
                    return true;
                }
            }

            System.out.println("Expected airline not found: " + expectedAirline);
            return false;

        } catch (Exception e) {
            System.out.println("Airline validation failed");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public boolean printAllAvailableFlightsWithPrices() {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'₹') or contains(text(),'INR') or contains(text(),'Rs')]")
            ));

            List<WebElement> prices = driver.findElements(
                    By.xpath("//*[contains(text(),'₹') or contains(text(),'INR') or contains(text(),'Rs')]")
            );

            List<WebElement> airlines = driver.findElements(
                    By.xpath("//*[contains(text(),'IndiGo') " +
                            "or contains(text(),'Air India') " +
                            "or contains(text(),'Akasa') " +
                            "or contains(text(),'SpiceJet') " +
                            "or contains(text(),'Alliance Air') " +
                            "or contains(text(),'Air India Express')]")
            );

            System.out.println("========== Available Flights ==========");

            int airlineCount = 0;
            for (WebElement airline : airlines) {
                if (airline.isDisplayed() && !airline.getText().trim().isEmpty()) {
                    airlineCount++;
                    System.out.println("Flight Airline " + airlineCount + ": " + airline.getText());
                }
            }

            int priceCount = 0;
            for (WebElement price : prices) {
                if (price.isDisplayed() && !price.getText().trim().isEmpty()) {
                    priceCount++;
                    System.out.println("Flight Price " + priceCount + ": " + price.getText());
                }
            }

            System.out.println("Total airlines found: " + airlineCount);
            System.out.println("Total prices found: " + priceCount);

            return airlineCount > 0 && priceCount > 0;

        } catch (Exception e) {
            System.out.println("Unable to print available flights with prices");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void takeScreenshot() throws Exception {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("./cleartrip_result.png");
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}

/*ResultPage is the Page Object class for validating Cleartrip search results. 
 * It uses the same WebDriver passed from the base class and a longer explicit wait because results can load slowly. 
 * The isFlightsDisplayed method waits for result page indicators and price elements, collects visible airline and price elements, prints them, and returns true only if both are found. 
 * The isAirlinePresent method checks for a specific airline using a dynamic case-insensitive XPath. 
 * The printAllAvailableFlightsWithPrices method prints all visible airlines and prices and returns a boolean for assertion.
 *  The takeScreenshot method captures the result page using Selenium’s TakesScreenshot interface. Overall, this class keeps result page validations reusable, clean, and separate from the test logic.
 */
