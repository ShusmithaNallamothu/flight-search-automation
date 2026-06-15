package Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import Basic.Basic;
import Pages.HomePage;
import Pages.SearchPage;
import Pages.ResultPage;

public class FlightSearchTest extends Basic {

    public void closeInitialPopups(HomePage home) {
        home.handleLoginPopup();
        home.closePopup();
        home.handleLoginPopup();
    }

    @Test(priority = 1)
    public void TC_01_verifyCleartripHomeSearchFormLoadsAfterPopupClose() {

        HomePage home = new HomePage(driver);

        closeInitialPopups(home);

        boolean isFormDisplayed = home.isSearchFormDisplayed();

        Assert.assertTrue(isFormDisplayed, "Cleartrip home/search form not loaded ❌");

        System.out.println("TC_01 Passed: Cleartrip home/search form loaded successfully ✅");
    }

    @Test(priority = 2)
    public void TC_02_searchValidRouteAndPrintAllAvailableFlightsWithPrices() throws Exception {

        HomePage home = new HomePage(driver);
        SearchPage search = new SearchPage(driver);
        ResultPage result = new ResultPage(driver);

        closeInitialPopups(home);

        search.enterFromCity("Vijayawada");
        search.enterToCity("Pune");
        search.selectDate();
        search.clickSearch();

        boolean flightsPrinted = result.printAllAvailableFlightsWithPrices();

        result.takeScreenshot();

        Assert.assertTrue(flightsPrinted, "Available flights with prices not printed ❌");

        System.out.println("TC_02 Passed: Available flight names with prices printed ✅");
    }

    @Test(priority = 3)
    public void TC_03_searchValidRouteButExpectNoFlights_IntentionalFail() throws Exception {

        HomePage home = new HomePage(driver);
        SearchPage search = new SearchPage(driver);
        ResultPage result = new ResultPage(driver);

        closeInitialPopups(home);

        search.enterFromCity("Vijayawada");
        search.enterToCity("Pune");
        search.selectDate();
        search.clickSearch();

        boolean flightsDisplayed = result.isFlightsDisplayed();

        result.takeScreenshot();

        Assert.assertFalse(
                flightsDisplayed,
                "Intentional Fail: Flights are displayed for valid route, but test expected no flights ❌"
        );

        System.out.println("TC_03 Passed unexpectedly");
    }
}
/*FlightSearchTest is my main TestNG class. 
 * It extends Basic, so every test gets browser setup and teardown automatically. 
 * I follow Page Object Model by using HomePage for popup handling and form validation, SearchPage for entering route details and clicking search, and ResultPage for validating and printing result data. 
 * TC_01 checks the home search form. 
 * TC_02 searches a valid route and prints available airlines and prices. 
 * TC_03 is intentionally designed to fail by searching a valid route but expecting no flights.
 *  This helps demonstrate assertion failure and negative test reporting in TestNG
 */

