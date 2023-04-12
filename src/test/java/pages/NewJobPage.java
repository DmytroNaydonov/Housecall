package pages;

import lombok.SneakyThrows;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class NewJobPage {
    private final WebDriverWait wait;
    private static final By NEW_CUSTOMER_BUTTON = By.xpath("//button/span[. ='+ New customer']");
    private static final By NEW_ITEM_NAME_FIELD = By.xpath("//input[@id = 'item-name']");
    private static final By UNIT_QTY_FIELD = By.xpath("//input[@id = 'qty']");
    private static final By UNIT_PRICE_FIELD = By.xpath("//input[@id = 'unit-price']");
    private static final By ADD_PRIVATE_NOTE_BUTTON = By.xpath("//div/p[. = 'Private notes']");
    private static final By ADD_PRIVATE_NOTE_FIELD = By.xpath("//textarea[@data-testid = 'private-notes-textfield']");
    private static final By SAVE_JOB_BUTTON = By.xpath("//button[@data-testid = 'createJobContainer__saveBtn']");
    public NewJobPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void createNewCustomer(String firstName, String lastName) {
        wait.until(elementToBeClickable(NEW_CUSTOMER_BUTTON)).click();
        val newCustomerDialog = new NewCustomerDialog();
        newCustomerDialog.createCustomer(firstName, lastName);
    }

    public void addLineItem(String itemName, String unitQty, String unitPrice) {
        wait.until(visibilityOfElementLocated(NEW_ITEM_NAME_FIELD)).sendKeys(itemName);
        val unitQtyField = wait.until(visibilityOfElementLocated(UNIT_QTY_FIELD));
        unitQtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"), unitQty);
        val unitPriceField = wait.until(visibilityOfElementLocated(UNIT_PRICE_FIELD));
        unitPriceField.sendKeys(Keys.chord(Keys.CONTROL, "a"), unitPrice);
    }

    public void addPrivateNote(String privateNote) {
        wait.until(elementToBeClickable(ADD_PRIVATE_NOTE_BUTTON)).click();
        wait.until(visibilityOfElementLocated(ADD_PRIVATE_NOTE_FIELD)).sendKeys(privateNote);
    }

    @SneakyThrows
    public void saveNewJob() {
        //TODO investigate why line item values are not saved without this timeout
        Thread.sleep(1000);
        wait.until(elementToBeClickable(SAVE_JOB_BUTTON)).click();
    }

    private class NewCustomerDialog {

        private static final By HEADER = By.xpath("//h2[. = 'Add new customer']");
        private static final By FIRST_NAME_FIELD = By.xpath("//input[@name = 'first_name']");
        private static final By LAST_NAME_FIELD = By.xpath("//input[@name = 'last_name']");
        private static final By CREATE_CUSTOMER_BUTTON = By.xpath("//button/span[. = 'create customer']");

        public void createCustomer(String firstName, String lastName) {
            wait.until(visibilityOfElementLocated(FIRST_NAME_FIELD)).sendKeys(firstName);
            wait.until(visibilityOfElementLocated(LAST_NAME_FIELD)).sendKeys(lastName);
            wait.until(elementToBeClickable(CREATE_CUSTOMER_BUTTON)).click();
            wait.until(invisibilityOfElementLocated(HEADER));
        }
    }
}
