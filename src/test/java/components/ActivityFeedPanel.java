package components;

import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import java.util.List;

public class ActivityFeedPanel implements WrapsElement {

    private final WebElement element;
    private static final By ITEM = By.xpath(".//following::div[contains(@class, 'MuiExpansionPanel-rounded')]");

    public ActivityFeedPanel(WebElement element) {
        this.element = element;
    }

    public List<String> getItemsText() {
        val activityFeedItems = element.findElements(ITEM).stream().map(ActivityFeedItem::new).toList();
        return activityFeedItems.stream().map(ActivityFeedItem::getText).toList();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    private final class ActivityFeedItem implements WrapsElement {

        private final WebElement element;
        private static final By ITEM_DETAILS = By.xpath(".//following::div[contains(@class, 'MuiGrid-item')]/p");

        private ActivityFeedItem(WebElement element) {
            this.element = element;
        }

        public String getText() {
            val text = element.findElements(ITEM_DETAILS).stream().map(WebElement::getText).toList();
            return String.join(", ", text);
        }

        @Override
        public WebElement getWrappedElement() {
            return element;
        }
    }
}
