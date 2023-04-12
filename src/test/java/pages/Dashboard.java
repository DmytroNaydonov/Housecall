package pages;

import components.Menu;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Dashboard {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By NEW_BUTTON = By.xpath("//button/span[. = 'New']");
    public Dashboard(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openNewJobPage() {
        val dropdown = new Menu(wait.until(visibilityOfElementLocated(NEW_BUTTON)), driver);
        dropdown.selectByValue("Job");
        wait.until(titleIs("Add job - HCP"));
    }
}
