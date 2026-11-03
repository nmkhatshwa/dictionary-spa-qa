package qa.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * These tests need the live Free Dictionary API. They are tagged "live-api" so a
 * normal "mvn test" leaves them out. If the API is down, they are skipped, not failed.
 */
@Tag("live-api")
class LookupTest extends UiTestBase {

    private void lookup(String linkId, String heading, String word, boolean pressEnter) {
        openView(linkId);
        waitForHeading(heading);

        WebElement box = driver.findElement(By.id("lookup"));
        box.clear();
        box.sendKeys(word);
        if (pressEnter) {
            box.sendKeys(Keys.ENTER);
        } else {
            driver.findElement(By.cssSelector("input[type=submit]")).click();
        }
    }

    private String resultText() {
        return driver.findElement(By.id("result")).getText();
    }

    /** Wait for a word to appear. If the API gave no data, skip the test instead of failing it. */
    private void waitForWordOrSkip() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                !d.findElements(By.id("word")).isEmpty()
                        || d.findElement(By.id("result")).getText().startsWith("No result found"));

        assumeTrue(!driver.findElements(By.id("word")).isEmpty(),
                "Dictionary API did not return data: " + resultText());
    }

    private List<String> texts(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector))
                .stream().map(WebElement::getText).toList();
    }

    @Test
    @DisplayName("TC-10 Dictionary shows the full result for a valid word")
    void dictionaryValidWord() {
        lookup("dictionary", "Dictionary", "code", false);
        waitForWordOrSkip();

        assertEquals("code", driver.findElement(By.id("word")).getText());
        assertEquals("/kəʊd/", driver.findElement(By.id("phonetic")).getText());
        assertFalse(driver.findElements(By.cssSelector("audio[src]")).isEmpty(), "audio player");

        List<String> partsOfSpeech = texts(".part-of-speech");
        assertTrue(partsOfSpeech.contains("noun"), "noun section");
        assertTrue(partsOfSpeech.contains("verb"), "verb section");
        assertFalse(texts(".definition").isEmpty(), "definitions");
        assertFalse(texts(".example").isEmpty(), "examples");
    }

    @Test
    @DisplayName("TC-11 Synonyms shows related words for a valid word")
    void synonymsValidWord() {
        lookup("synonyms", "Synonyms", "greed", false);
        waitForWordOrSkip();

        assertEquals("greed", driver.findElement(By.id("word")).getText());
        assertFalse(texts(".related-word").isEmpty(), "list of related words");
    }

    @Test
    @DisplayName("TC-12 Antonyms shows related words for a valid word")
    void antonymsValidWord() {
        lookup("antonyms", "Antonyms", "hard", false);
        waitForWordOrSkip();

        assertEquals("hard", driver.findElement(By.id("word")).getText());
        assertFalse(texts(".related-word").isEmpty(), "list of related words");
    }

    @Test
    @DisplayName("TC-13 An unknown word shows a no-result message")
    void unknownWord() {
        lookup("dictionary", "Dictionary", "zzzzqx", false);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                d.findElement(By.id("result")).getText().startsWith("No result found"));

        // Only a genuine "word not found" (HTTP 404) counts. An outage skips the test.
        assumeTrue(resultText().contains("HTTP 404"),
                "Dictionary API unavailable: " + resultText());
        assertTrue(resultText().startsWith("No result found for \"zzzzqx\""));
    }

    @Test
    @DisplayName("TC-15 The Enter key submits the lookup")
    void enterKeySubmits() {
        lookup("dictionary", "Dictionary", "code", true);
        waitForWordOrSkip();

        assertEquals("code", driver.findElement(By.id("word")).getText());
    }
}