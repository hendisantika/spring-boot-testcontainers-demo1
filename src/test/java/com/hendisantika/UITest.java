package com.hendisantika;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.selenium.BrowserWebDriverContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.time.Duration;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = RabbitMqTestConfiguration.class)
@Testcontainers
class UITest {

    // the host port must be exposed before the browser container starts so it can reach the app
    private static final int PORT = findFreePort();

    // multi-arch (amd64 + arm64) image, so the test also runs on Apple Silicon
    private static final DockerImageName CHROMIUM_IMAGE = DockerImageName.parse("selenium/standalone-chromium:latest")
            .asCompatibleSubstituteFor("selenium/standalone-chrome");

    static {
        org.testcontainers.Testcontainers.exposeHostPorts(PORT);
    }

    @Container
    public static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:management")
            .withExposedPorts(5672);
    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");
    @Container
    static GenericContainer<?> activeMQContainer = new GenericContainer<>("apache/activemq-classic:latest")
            .withExposedPorts(61616);
    // @formatter:off
    @Container
    BrowserWebDriverContainer chrome = new BrowserWebDriverContainer(CHROMIUM_IMAGE)
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING, new File("./target/"));
    // @formatter:on

    RemoteWebDriver driver;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> PORT);
        DemoApplicationTestPropertyValues.populateRegistryFromContainers(registry, postgreSQLContainer,
                activeMQContainer, rabbitMQContainer);
    }

    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @BeforeEach
    void setUp() {
        driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSuccessfullyPassThisTestUsingTheRemoteDriver() {

        System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
        System.out.println("VNC URL is: " + chrome.getVncAddress());

        String url = "http://host.testcontainers.internal:" + PORT + "/";
        System.out.println("Spring Boot URL is: " + url);
        driver.get(url);

        List<WebElement> results = new WebDriverWait(driver, Duration.ofMinutes(15))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

        assertThat(results.size(), is(1));
        assertThat(results.get(0).getText(), containsString("Notifications"));

    }

    @Test
    @Disabled("Intentionally failing demo that produces a VNC video recording in ./target; enable it locally to try it")
    void shouldFailThisTestUsingTheRemoteDriverAndGenerateAVideoRecording() throws InterruptedException {

        System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
        System.out.println("VNC URL is: " + chrome.getVncAddress());

        String url = "http://host.testcontainers.internal:" + PORT + "/";
        System.out.println("Spring Boot URL is: " + url);
        driver.get(url);

        // added for effect when viewing the video
        Thread.sleep(1000);

        List<WebElement> results = new WebDriverWait(driver, Duration.ofMinutes(15))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

        assertThat(results.size(), is(2));

    }
}
