package steps;

import config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import manager.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v111.network.Network;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Slf4j
public class Hooks {

    private final TestConfig config;
    @Getter
    private static WebDriver driver;

    private PrintWriter out;

    public Hooks(ConfigManager configManager) {
        this.config = configManager.create();
    }

    @Before
    @SneakyThrows
    public void beforeTest() {
        switch (config.getBrowser().getName()) {
            case "Chrome" -> {
                log.info("Setting up Chrome driver...");
                WebDriverManager.chromedriver().setup();
                val options = new ChromeOptions();
                if (config.getBrowser().getHeadless().equals("true")) {
                    options.addArguments("--headless");
                }

                driver = new ChromeDriver(options);

                out = new PrintWriter("network.txt");
                val devTools = ((ChromeDriver) driver).getDevTools();
                devTools.createSession();
                devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
                devTools.addListener(Network.requestWillBeSent(),
                        request -> {
                            if (request.getRequest().getUrl().contains("alpha/jobs") && request.getRequest().getMethod().equals("POST"))
                                System.out.println("URL:" + request.getRequest().getUrl() + " METHOD: " + request.getRequest().getMethod() + " PAYLOAD: " + request.getRequest().getPostData());
                            out.println("URL:" + request.getRequest().getUrl() + " METHOD: " + request.getRequest().getMethod() + " PAYLOAD: " + request.getRequest().getPostData());
                        }
                );
            }

            case "Edge" -> {
                log.info("Setting up Edge driver...");
                WebDriverManager.edgedriver().setup();
                val options = new EdgeOptions();
                if (config.getBrowser().getHeadless().equals("true")) {
                    options.addArguments("--headless");
                }

                driver = new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException("Supported browsers are Chrome or Edge");
        }
    }

    @SneakyThrows
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            val srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            val srcBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            val path = Paths.get("screenshots");
            val scenarioName = scenario.getName().replaceAll(" ", "_");
            val timestamp = Timestamp.from(Instant.now()).toString()
                    .replaceAll(" ", "_")
                    .replaceAll(":", "")
                    .split("\\.")[0];
            val filename = scenarioName + "_" + timestamp + ".png";
            FileUtils.copyFile(srcFile, new File(path + File.separator + filename));
            log.info("Scenario failed, see screenshot {} in {}", filename, path);
            Allure.addAttachment("Failed Screenshot", new ByteArrayInputStream(srcBytes));
        }
    }

    @After(order = 0)
    public void quitDriver() {
        out.close();
        driver.quit();
    }
}
