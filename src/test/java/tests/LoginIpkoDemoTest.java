package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.PageFactory.initElements;

public class LoginIpkoDemoTest {

    WebDriver driver;

    @FindBy(xpath = "//button//span[text()='Dalej']")
    WebElement NEXT_BUTTON;

    @FindBy(xpath = "//button//span[text()='Zaloguj']")
    WebElement LOGIN_BUTTON;

    @FindBy(xpath = "//a[text()='WYLOGUJ']")
    WebElement LOGOUT_BUTTON;

    @Test
    public void loginIpkoDemoTest() {
        initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON));
        NEXT_BUTTON.click();
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        LOGIN_BUTTON.click();
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
        LOGOUT_BUTTON.click();
    }

    @BeforeTest
    public void beforeTest() {
        //run Wiremock - standalone with mappings from iPKO site or initiate WireMock here
        System.setProperty("webdriver.chrome.driver", "/home/m.marcinek/IdeaProjects/libs/chromedriver");
        driver = new ChromeDriver();
        //driver.get("http://demo.ipko.pl/login.html");
        driver.get("localhost:8080");
    }
}
