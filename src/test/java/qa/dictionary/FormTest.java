package qa.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FormTest extends UiTestBase {

    private void submitLookup(String text) {
        openView("dictionary");
        waitForHeading("Dictionary");

        WebElement box = driver.findElement(By.id("lookup"));
        box.clear();
        if (!text.isEmpty()) {
            box.sendKeys(text);
        }
        driver.findElement(By.cssSelector("input[type=submit]")).click();
    }

    @Test
    @DisplayName("TC-08 Empty input asks for a word")
    void emptyInput() {
        submitLookup("");
        waitForText(By.id("result"), "Please enter a word.");
    }

    @Test
    @DisplayName("TC-09 Spaces only asks for a word")
    void spacesOnly() {
        submitLookup("   ");
        waitForText(By.id("result"), "Please enter a word.");
    }

    @Test
    @DisplayName("TC-14 Special characters are shown as plain text, not injected as HTML")
    void specialCharactersAreNotInjectedAsHtml() {
        submitLookup("<b>test</b>");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                d.findElement(By.id("result")).getText().startsWith("No result found"));

        String message = driver.findElement(By.id("result")).getText();
        assertTrue(message.contains("<b>test</b>"), "the typed text is visible as text");
        assertTrue(driver.findElements(By.cssSelector("#result b")).isEmpty(),
                "no <b> element was created");
    }
}