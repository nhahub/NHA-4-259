package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QuestionBankPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String SUBJECTS_URL = "https://mr-ali-magdy.zakerai.org/subjects/";

    // --- Modern, Scalable Locators ---
    // Targets add buttons via sub-element icons, bypassing Tailwind styling entirely
    private final By addButtonLocator = By.cssSelector("button:has(.lucide-plus)");
    
    // Safely preserves your working bilingual text logic, isolated cleanly as a single field locator
    private final By createExamTriggerBtn = By.xpath("(//button[(contains(., 'Create Exam') or contains(., 'إنشاء اختبار')) and not(ancestor::div[@role='dialog'])])[1]");
    private final By examModal = By.cssSelector("div[role='dialog']");
    private final By titleInputField = By.id("title");
    private final By modalSubmitBtn = By.cssSelector("div[role='dialog'] button[type='submit']");

    // Constructor initializes the page state
    public QuestionBankPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Navigates to the question bank and adds a specified number of questions.
     * Bypasses explicit thread freezes by letting WebDriver execute as fast as DOM state allows.
     */
    public void addQuestions(int questionsToAdd) {
        driver.get(SUBJECTS_URL);
        
        // Wait dynamically until at least one valid question card button renders
        wait.until(ExpectedConditions.presenceOfElementLocated(addButtonLocator));
        List<WebElement> addButtons = driver.findElements(addButtonLocator);
        System.out.println("Found " + addButtons.size() + " available questions.");

        int added = 0;
        for (WebElement button : addButtons) {
            if (added >= questionsToAdd) break;
            try {
                button.click();
                added++;
            } catch (Exception e) {
                System.out.println("Could not click add button at index " + added);
            }
        }
        System.out.println("Successfully added " + added + " questions to the session selection.");
    }

    /**
     * Higher-level business action that encapsulates the lower-level clicks and waits.
     */
    public void createExam(String examTitle) {
        openExamDialog();
        fillExamTitle(examTitle);
        submitExamForm();
    }

    private void openExamDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(createExamTriggerBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(examModal));
    }

    private void fillExamTitle(String examTitle) {
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        titleInput.clear();
        titleInput.sendKeys(examTitle);
    }

    private void submitExamForm() {
        wait.until(ExpectedConditions.elementToBeClickable(modalSubmitBtn)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(examModal));
        System.out.println("Exam successfully created");
    }
}