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
class CredentialsTest {

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

    private void createCredential(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // choose credentials tab
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        // click Add New Credential
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
        WebElement newCredential = driver.findElement(By.id("new-credential"));
        newCredential.click();

        // Write the credential's url
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys(url);

        // Write the credential's username
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys(username);

        // Write the credential's password
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys(password);

        // Click save changes
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-changes-credential")));
        WebElement saveChanges = driver.findElement(By.id("save-changes-credential"));
        saveChanges.click();

        // choose credentials tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTab2 = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab2.click();
    }

    private void editCredential(String newUsername) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click the edit button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-btn-credential")));
        WebElement newNote = driver.findElement(By.id("edit-btn-credential"));
        newNote.click();

        // Write the new credential's username
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.clear();
        credentialUsername.sendKeys(newUsername);

        // Click save changes
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-changes-credential")));
        WebElement saveChanges = driver.findElement(By.id("save-changes-credential"));
        saveChanges.click();

        // choose credentials tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTab2 = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab2.click();
    }

    private void deleteCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        //  click delete button of existing note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-btn-credential")));
        WebElement newNote = driver.findElement(By.id("delete-btn-credential"));
        newNote.click();

        // choose credentials tab again
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTab2 = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab2.click();
    }


    @Test
    public void testCreateCredentials() {
        createCredential("anything", "azeez", "123");

        // each credential in the list has an id which is its username which is unique
        Assertions.assertNotNull(driver.findElement(By.id("azeez")));
    }

    @Test
    public void testEditNote() {
        createCredential("anything", "azeez", "123");
        editCredential("azeez_141");

        // since the username is changed. the id of the element must be changed as well
        Assertions.assertNotNull(driver.findElement(By.id("azeez_141")));
    }

    @Test
    public void testDeleteNote() {
        createCredential("anything", "azeez", "123");
        deleteCredential();
        // The element must throws NoSuchElementException
        Assertions.assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> {
            driver.findElement(By.id("azeez"));
        });
    }

}