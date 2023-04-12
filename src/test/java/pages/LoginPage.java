package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final By EMAIL = By.xpath("//input[@id = 'email']");
    private static final By PASSWORD = By.xpath("//input[@id = 'password']");
    private static final By SIGN_IN = By.xpath("//button/span[. = 'Sign in']");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String url, String username, String password) {
        driver.get(url);
        wait.until(visibilityOfElementLocated(EMAIL)).sendKeys(username);
        wait.until(visibilityOfElementLocated(PASSWORD)).sendKeys(password);
        wait.until(elementToBeClickable(SIGN_IN)).click();
        wait.until(titleIs("Dashboard - HCP"));
    }
}
