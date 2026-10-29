package qa.dictionary;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class UiTestBase {
    static final String BASE_URL = System.getProperty("baseUrl", "http://localhost:5050");

    protected WebDriver driver;

    @BeforeEach
    void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--window-size=1280,900");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL + "/");
    }

    @AfterEach
    void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Click a menu link by its id: dictionary, synonyms or antonyms. */
    protected void openView(String linkId) {
        driver.findElement(By.id(linkId)).click();
    }

    /** Wait (up to 5 seconds) until the element's text equals the expected text. */
    protected void waitForText(By locator, String expected) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBe(locator, expected));
    }

    protected void waitForHeading(String expected) {
        waitForText(By.tagName("h2"), expected);
    }
}