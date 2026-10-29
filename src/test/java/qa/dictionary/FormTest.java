package qa.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
}