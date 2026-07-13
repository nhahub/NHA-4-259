package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String LOGIN_URL = "https://mr-ali-magdy.zakerai.org/login";

    // Clean, private locators isolated to just this page
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By submitBtn = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String username, String password) {
        driver.get(LOGIN_URL);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitBtn).click();

        // Ensure navigation out of /login completed
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        System.out.println("Login successful. Current URL: " + driver.getCurrentUrl());
    }
}