package steps;

import config.TestConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.val;
import manager.ConfigManager;
import net.datafaker.Faker;
import org.openqa.selenium.WebDriver;
import pages.Dashboard;
import pages.JobPage;
import pages.LoginPage;
import pages.NewJobPage;

import java.util.Base64;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static steps.Hooks.*;


public class JobSteps {

    private final TestConfig config;
    private final WebDriver driver;

    public JobSteps(ConfigManager configManager) {
        this.config = configManager.create();
        this.driver = getDriver();
    }

    @Given("user is logged in to the application")
    public void userIsLoggedIn() {
        val username = config.getCredentials().getUsername();
        val password = new String(Base64.getDecoder().decode(config.getCredentials().getPassword()));
        val loginPage = new LoginPage(driver);
        loginPage.login(config.getUrl(), username, password);
    }

    @When("user creates new job")
    public void userCreatesNewJob(Map<String, String> params) {
        val dashboard = new Dashboard(driver);
        dashboard.openNewJobPage();
        val newJobPage = new NewJobPage(driver);
        val faker = new Faker();
        val firstName = faker.name().firstName();
        val lastName = faker.name().lastName();
        newJobPage.createNewCustomer(firstName, lastName);
        val itemName = params.get("item name") + "_" + randomAlphabetic(5);
        newJobPage.addLineItem(itemName, params.get("unit qty"), params.get("unit price"));
        newJobPage.addPrivateNote(params.get("private note"));
        newJobPage.saveNewJob();
        val jobPage = new JobPage(driver);
        val createdJobs = jobPage.getActivityFeedItemText().stream().filter(i -> i.contains("Job created")).toList();
        assertThat("There were no Activity feed items with 'Job created' text", createdJobs, is(not(empty())));
    }
}
