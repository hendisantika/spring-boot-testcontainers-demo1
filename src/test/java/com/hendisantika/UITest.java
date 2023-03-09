package com.hendisantika;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 06:05
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = RabbitMqTestConfiguration.class)
@Testcontainers
class UITest {

    @Container
    public static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:management")
            .withExposedPorts(5672);
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    // @formatter:on
    @Container
    static GenericContainer<?> activeMQContainer = new GenericContainer<>("rmohr/activemq:latest")
            .withExposedPorts(61616);
    @LocalServerPort
    int port;
    // @formatter:off
    @Container
    BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING, new File("./target/"))
            .withCapabilities(new ChromeOptions());

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        DemoApplicationTestPropertyValues.populateRegistryFromContainers(registry, postgreSQLContainer,
                activeMQContainer, rabbitMQContainer);
    }

    @Test
    void shouldSuccessfullyPassThisTestUsingTheRemoteDriver() throws InterruptedException {

        RemoteWebDriver driver = chrome.getWebDriver();

        System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
        System.out.println("VNC URL is: " + chrome.getVncAddress());

        String url = "http://host.docker.internal:" + port + "/";
        System.out.println("Spring Boot URL is: " + url);
        driver.get(url);

        List<WebElement> results = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

        assertThat(results.size(), is(1));
        assertThat(results.get(0).getText(), containsString("Notifications"));

    }

    @Test
    void shouldFailThisTestUsingTheRemoteDriverAndGenerateAVideoRecording() throws InterruptedException {

        RemoteWebDriver driver = chrome.getWebDriver();

        System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
        System.out.println("VNC URL is: " + chrome.getVncAddress());

        String url = "http://host.docker.internal:" + port + "/";
        System.out.println("Spring Boot URL is: " + url);
        driver.get(url);

        // added for effect when viewing the video
        Thread.sleep(1000);

        List<WebElement> results = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

        assertThat(results.size(), is(2));

    }
}
