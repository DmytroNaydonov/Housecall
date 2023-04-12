package pages;

import components.ActivityFeedPanel;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class JobPage {

    private final WebDriverWait wait;
    private static final By ACTIVITY_FEED_PANEL = By.xpath("//div/span[.='Activity feed']");

    public JobPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<String> getActivityFeedItemText() {
        val activityFeedPanel = new ActivityFeedPanel(wait.until(visibilityOfElementLocated(ACTIVITY_FEED_PANEL)));
        return activityFeedPanel.getItemsText();
    }
}
