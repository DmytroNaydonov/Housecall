package components;

import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class Menu implements WrapsElement {

    private final WebElement element;
    private final WebDriverWait wait;
    private static final By OPTION = By.xpath("//ul[@role='menu']/li");
    public Menu(WebElement element, WebDriver driver) {
        this.element = element;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectByValue(String name) {
        element.click();
        val options = wait.until(visibilityOfAllElementsLocatedBy(OPTION));
        val selectedOption = options.stream().filter(o -> o.getText().equals(name)).findFirst().orElseThrow();
        selectedOption.click();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }
}
