package ons.group8.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTests {

    @Autowired
    private WebDriver driver;

    private WebDriver login(String emailInput, String passwordInput) {
        driver.get("https://localhost:8443/login");

        WebElement email = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement login = driver.findElement(By.name("login"));

        email.sendKeys(emailInput);
        password.sendKeys(passwordInput);
        login.submit();

        return driver;
    }

    @BeforeEach
    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(handlingSSL);
    }

    @AfterEach
    public void endDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void verify_login_title() {
        driver.get("https://localhost:8443/login");

        assertEquals(driver.getTitle(), "ONS Onboarding");
    }

    @Test
    public void verify_register_title() {
        driver.get("https://localhost:8443/sign-up/register");

        assertEquals(driver.getTitle(), "Registration Page");
    }

    @Test
    public void login_to_user() {
        driver = login("picard@cardiff.ac.uk", "earlgreyhot");
        assertEquals(driver.getTitle(), "Home");
    }

    @Test
    public void view_my_checklists_page() {
        driver = login("picard@cardiff.ac.uk", "earlgreyhot");

        WebElement myChecklists = driver.findElement(By.id("myChecklists"));
        myChecklists.click();

        assertEquals(driver.getTitle(), "Personal Checklists");
        assertEquals(driver.getCurrentUrl(), "https://localhost:8443/user/personal-checklist-list");
    }

    @Test
    public void view_specific_checklist() {
        driver = login("picard@cardiff.ac.uk", "earlgreyhot");

        driver.navigate().to("https://localhost:8443/user/personal-checklist-list");
        List<WebElement> href = driver.findElements(By.tagName("a"));
        href.get(href.size() - 1).click();

        assertEquals(driver.getTitle(), "View Checklist");
        assertEquals(driver.getCurrentUrl(), "https://localhost:8443/user/personal-checklist/3");
    }

    @Test
    public void save_completed_items_on_checklist() {
        driver = login("picard@cardiff.ac.uk", "earlgreyhot");

        driver.navigate().to("https://localhost:8443/user/personal-checklist/3");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        for (int i = 0; i < inputs.size(); i = i + 2) {
            if (!inputs.get(i).isSelected())
            inputs.get(i).click();
        }
        WebElement submit = driver.findElement(By.id("submit"));
        submit.submit();

        assertEquals(driver.getTitle(), "View Checklist");
        assertEquals(driver.getCurrentUrl(), "https://localhost:8443/user/personal-checklist/3/save");
        assertEquals(driver.findElement(By.id("checkedCount")).getText(), driver.findElement(By.id("checkItemsCount")).getText());
    }

    @Test
    public void view_authored_checklists() {
        driver = login("sameer@cardiff.ac.uk", "sampass");

        driver.navigate().to("https://localhost:8443/author/view-checklist-templates");

        assertEquals(driver.getTitle(), "My Checklist Templates");
        assertEquals(driver.getCurrentUrl(), "https://localhost:8443/author/view-checklist-templates");
    }

    @Test
    public void view_authored_checklist() {
        driver = login("sameer@cardiff.ac.uk", "sampass");

        driver.navigate().to("https://localhost:8443/author/view-checklist-templates");
        WebElement href = driver.findElement(By.id("checklistTemplateLink2"));
        href.click();

        assertEquals(driver.getTitle(), "View Checklist");
        assertEquals(driver.getCurrentUrl(), "https://localhost:8443/author/view-checklist-template/2");
    }


}
