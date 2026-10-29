package qa.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NavigationTest extends UiTestBase {

    @Test
    @DisplayName("TC-01 Default page shows the menu and the prompt")
    void defaultPage() {
        List<String> links = driver.findElements(By.cssSelector("nav a"))
                .stream().map(WebElement::getText).toList();

        assertEquals(List.of("Dictionary", "Synonyms", "Antonyms"), links);
        assertEquals("Click on a menu item.", driver.findElement(By.id("app")).getText());
    }
}