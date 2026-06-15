package Basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Basic {

	public WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cleartrip.com/flights");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
      
    /*This Basic class is my Selenium TestNG base class.
     *  It contains common setup and teardown methods. 
     *  Before every test method, @BeforeMethod runs and initializes ChromeDriver using WebDriverManager, opens Chrome, maximizes the window, and navigates to the Cleartrip flights page. 
     *  After every test method, @AfterMethod runs and closes the browser using driver.quit(). 
     *  I used WebDriver as the reference type for flexibility and ChromeDriver as the actual browser implementation.
     *   This structure avoids duplicate setup code and keeps each test independent
     * /
     */
	
}
