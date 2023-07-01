package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        doMockSignUp("Abdulaziz", "Alsafadi", "azeez", "123");
        doLogIn("azeez", "123");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    private void createNote(String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // choose notes tab
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        // click Add New Note new-note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
        WebElement newNote = driver.findElement(By.id("new-note"));
        newNote.click();

        // Write the note's title
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys(title);

        // Write the note's description
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys(description);

        // Click save changes
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-changes")));
        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();

        // Click notes tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab2 = driver.findElement(By.id("nav-notes-tab"));
        notesTab2.click();
    }

    private void editNote(String newTitle) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click the edit button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-btn")));
        WebElement newNote = driver.findElement(By.id("edit-btn"));
        newNote.click();

        // Write the new note's title
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.clear();
        noteTitle.sendKeys(newTitle);

        // Click save changes
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-changes")));
        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();

        // Click notes tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab2 = driver.findElement(By.id("nav-notes-tab"));
        notesTab2.click();
    }

    private void deleteNote() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        //  click delete button of existing note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-btn")));
        WebElement newNote = driver.findElement(By.id("delete-btn"));
        newNote.click();

        // Click notes tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab2 = driver.findElement(By.id("nav-notes-tab"));
        notesTab2.click();
    }

    @Test
    public void testCreateNote() {
        createNote("SpringBoot", "Spring booot");

        // each note in the list has an id which is its title
        Assertions.assertNotNull(driver.findElement(By.id("SpringBoot")));
    }

    @Test
    public void testEditNote() {
        createNote("SpringBoot", "Spring booot");
        editNote("spring");

        // since the title is changed. the id of the element must be changed as well
        Assertions.assertNotNull(driver.findElement(By.id("spring")));
    }

    @Test
    public void testDeleteNote() {
        createNote("SpringBoot", "Spring booot");
        deleteNote();
        // The element must throws NoSuchElementException
        Assertions.assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {
            driver.findElement(By.id("SpringBoot"));
        });
    }

}