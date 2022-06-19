package lesson6;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.hasText;

public class AutomationTest {
    WebDriver driver;
    TShirtsPage tShirtsPage;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        driver = new ChromeDriver();
        tShirtsPage = new TShirtsPage(driver);
    }

    @Test
    void addToCartTest() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        new LoginPage(driver)
                .login("spartalex93@test.test", "123456")
                .navigationBlock.clickTShirtsButtonInWomenSuggest()
                .selectSize("S")
                .addTShirtToCartByText("Faded")
                .checkCorrectSummInCart("$18.51");

        Assertions.assertAll(
                () -> Assertions.assertTrue(new SuccessBlock(driver).successHeader.isDisplayed()),
                () -> assertThat(new SuccessBlock(driver).summElement, hasText("$18.51"))
        );
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

