✈️ Flight Search Automation

Selenium + TestNG automation suite for Cleartrip flight search, built with Page Object Model (POM) and Maven.


Tech Stack
Tech Stack

Tool                     Purpose
Java17                   Core language
Selenium 4.36.0          Browser automation
TestNG 7.7.1             Test execution & reporting
WebDriverManager 6.1.1   Auto ChromeDriver setup
Maven                    Build & dependency management
Eclipse IDE              write, compile, test, and debug Java applications


Project Structure

src/
├── main/java/
│   └── Basic/
│       └── Basic.java          # Base class – browser setup & teardown
└── test/java/
    ├── Pages/
    │   ├── HomePage.java        # Popup handling, form visibility checks
    │   ├── SearchPage.java      # City input, date selection, search trigger
    │   └── ResultPage.java      # Flight result validation, screenshot capture
    └── Test/
        └── FlightSearchTest.java  # TestNG test cases


Test Cases

TCNameWhat it doesExpected resultTC_01Home form loads after popup closeHandles login + later popups, checks form visibilityPass ✅TC_02Search valid route & print all flightsVijayawada → Pune, prints airline names and pricesPass ✅TC_03Intentional failure — expects no flightsSame route, asserts false on flight resultsFail ❌ (by design)


TC_03 is a deliberate negative test to demonstrate assertion failure behavior in TestNG reports.




Setup & Run

Prerequisites: Java 17+, Maven, Chrome browser

bash# Clone
git clone https://github.com/ShusmithaNallamothu/flight-search-automation.git
cd flight-search-automation

# Run all tests
mvn test

No manual ChromeDriver download needed — WebDriverManager handles it automatically.


Key Design Decisions


Page Object Model — Each page has its own class. Tests stay clean and logic stays reusable.
Explicit waits — WebDriverWait used throughout; no Thread.sleep().
JavascriptExecutor for SVG close icons — Cleartrip's login popup close button is an SVG element that resists standard Selenium clicks.
Case-insensitive XPath — Airline name matching uses translate() to normalize case at the DOM level.
Screenshot on result page — Saved as cleartrip_result.png in project root after TC_02 and TC_03.



Output


TestNG reports: target/surefire-reports/
Screenshot: ./cleartrip_result.png



Author

Shusmitha Nallamothu

LinkedIn
