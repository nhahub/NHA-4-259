package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Step 1: pointing at local edgedriver...");
        System.setProperty("webdriver.edge.driver", "H:\\DEPI_team\\NHA-4-259\\automation\\drivers\\msedgedriver.exe");
        
        System.out.println("Step 2: launching Edge...");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        try {
            // Instantiate the Login Page Object and execute login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("mralimagdy_admin", "59wnqgF2e3Mo");

            // Instantiate the Question Bank Page Object and execute workflows
            QuestionBankPage questionBank = new QuestionBankPage(driver);
            questionBank.addQuestions(2);
            questionBank.createExam("My Automated Exam");

        } catch (Exception e) {
            System.out.println("SOMETHING FAILED:");
            e.printStackTrace();
        } finally {
            Thread.sleep(3000);
            driver.quit();
        }
    }
}