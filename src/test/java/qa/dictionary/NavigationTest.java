package qa.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NavigationTest extends UiTestBase {

    @Test
    @DisplayName("TC-01 Default page shows the menu and the prompt")
    void defaultPage() {
        List<String> links = driver.findElements(By.cssSelector("nav a"))
                .stream().map(WebElement::getText).toList();

        assertEquals(List.of("Dictionary", "Synonyms", "Antonyms"), links);
        assertEquals("Click on a menu item.", driver.findElement(By.id("app")).getText());
    }

    @ParameterizedTest(name = "{1} view shows a heading, a text box and a button")
    @CsvSource({"dictionary, Dictionary", "synonyms, Synonyms", "antonyms, Antonyms"})
    @DisplayName("TC-02 to TC-04 Each view shows the lookup form")
    void viewShowsLookupForm(String linkId, String heading) {
        openView(linkId);
        waitForHeading(heading);

        assertTrue(driver.findElement(By.id("lookup")).isDisplayed());
        assertEquals("Look up",
                driver.findElement(By.cssSelector("input[type=submit]")).getAttribute("value"));
    }

    @Test
    @DisplayName("TC-05 Switching views does not reload the page")
    void switchingViewsDoesNotReload() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.reloadMarker = 'still here';");

        openView("dictionary");
        waitForHeading("Dictionary");
        openView("synonyms");
        waitForHeading("Synonyms");
        openView("antonyms");
        waitForHeading("Antonyms");

        // The marker only survives if the page was never reloaded
        assertEquals("still here", js.executeScript("return window.reloadMarker;"));
    }

    @Test
    @DisplayName("TC-06 A view can be opened directly by its address")
    void openViewByAddress() {
        driver.get("about:blank");
        driver.get(BASE_URL + "/#/synonyms");
        waitForHeading("Synonyms");
    }

    @Test
    @DisplayName("TC-07 The Back button returns to the previous view")
    void backButton() {
        openView("dictionary");
        waitForHeading("Dictionary");
        openView("synonyms");
        waitForHeading("Synonyms");

        driver.navigate().back();

        waitForHeading("Dictionary");
    }
}