package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        // Автоматическая настройка ChromeDriver для разных ОС
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        // Настройки для Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless"); // Запуск без графического интерфейса

        // Создаем экземпляр браузера
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        // Закрываем браузер после каждого теста
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная отправка формы заказа карты")
    public void shouldSubmitFormSuccessfully() {
        // Открываем страницу приложения
        driver.get("http://127.0.0.1:9999");

        // Заполняем поле "Фамилия и имя"
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иван Петров-Сидоров");

        // Заполняем поле "Телефон"
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79123456789");

        // Ставим галочку "Согласие"
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        // Нажимаем кнопку "Продолжить"
        driver.findElement(By.cssSelector("button.button"))
                .click();

        // Проверяем, что появилось сообщение об успехе
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']"))
                .getText()
                .trim();

        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(expectedMessage, actualMessage,
                "Сообщение об успехе не соответствует ожидаемому");
    }
}