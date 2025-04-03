package com.qa_task.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa_task.pages.HomePage;

public class BaseTest {
    private WebDriver driver;
    private WebDriverWait driverWait;
    protected HomePage homePage;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://jira.trungk18.com/project/board");
        driver.manage().window().maximize();
        homePage = new HomePage(driver, driverWait);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
