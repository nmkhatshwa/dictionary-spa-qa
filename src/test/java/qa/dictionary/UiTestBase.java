package qa.dictionary;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
}