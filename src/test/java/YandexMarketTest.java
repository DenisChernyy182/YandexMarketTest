import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class YandexMarketTest {

    private WebDriver driver;

    // Устанавливаем путь к драйверу Chrome
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testYandexMarket() {

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Шаг 1: Перейти на https://market.yandex.ru
        driver.navigate().to("https://market.yandex.ru");

        // Шаг 2: Нажать на "Каталог"
        WebElement catalogLink = driver.findElement(By.className("button-focus-ring"));
        catalogLink.click();

        // Шаг 3: Навести курсор на "Зоотовары, в блоке «Для кошек» нажать на «Лакомства»
        WebElement zooproductsLink = driver.findElement(By.linkText("Зоотовары"));
        zooproductsLink.click();
        WebElement catTreatsLink = driver.findElement(By.xpath("//*[@id=\"116366253\"]/div/div/div[1]/div/a"));
        catTreatsLink.click();

        // Шаг 4a: Установить фильтр по цене: от 50 руб до 150 руб
        WebElement priceFromInput = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[5]/div/div/div/div/aside/div/div[3]/div/div/div/div/div[1]/div/fieldset/div/div/div/div[1]/span/div/div/input"));
        priceFromInput.click();
        priceFromInput.sendKeys("50");

        WebElement priceToInput = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[5]/div/div/div/div/aside/div/div[3]/div/div/div/div/div[1]/div/fieldset/div/div/div/div[2]/span/div/div/input"));
        priceToInput.click();
        priceToInput.sendKeys("150");

        // Шаг 4b: Установить фильтр способа доставки: "С учетом доставки курьером"
        WebElement deliveryCourierCheckbox = driver.findElement(By.xpath("//*[@id=\"searchFilters\"]/div/div[3]/div/div/div/div/div[6]/div/fieldset/div/div/div[1]/label/span/span[1]"));
        deliveryCourierCheckbox.click();

        // Шаг 4c: Установить фильтр производителя
        WebElement RoyalCaninCheckbox = driver.findElement(By.xpath("//*[@id=\"searchFilters\"]/div/div[3]/div/div/div/div/div[4]/div/fieldset/div/div/div[1]/div/div/div[1]/label/span/span[2]"));
        RoyalCaninCheckbox.click();

        // Шаг 5: Перейти в первый товар в списке, нажать на кнопку "Сравнить"
        WebElement firstProductLink = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[5]/div/div/div/div/div/div/div[4]/div/div/div/div/div[1]/main/div/div/div/div/div/div[2]/div/div/div[1]/article/a/div/div/div/div[1]/div/div[1]/img"));
        firstProductLink.click();

        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
        }

        WebElement compareButton = driver.findElement(By.xpath("//*[@id=\"cardContent\"]/div[3]/div[1]/div/div/div[2]/div[1]/div/div/div/span"));
        compareButton.click();

        // Шаг 6: Вернуться на предыдущую страницу

        driver.close();
        driver.switchTo().window(windowHandles.iterator().next());

        // Шаг 7: Снять галочку с производителя "Royal Canin" и установить производителя "Мнямс"
        RoyalCaninCheckbox.click();
        WebElement mnyamsCheckbox = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[5]/div/div/div/div/aside/div/div[3]/div/div/div/div/div[4]/div/fieldset/div/div/div[1]/div/div/div[2]/label/span/span[1]/span"));
        mnyamsCheckbox.click();

        // Шаг 8: Перейти во второй товар в списке, нажать на кнопку "Сравнить"
        WebElement secondProductLink = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[5]/div/div/div/div/div/div/div[4]/div/div/div/div/div[1]/main/div/div/div/div/div/div[2]/div/div/div[2]/article/a"));
        secondProductLink.click();

        Set<String> windowHandles1 = driver.getWindowHandles();

        for (String handle1 : windowHandles1) {
            driver.switchTo().window(handle1);
        }

        WebElement compareButton1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div/main/div[3]/div[1]/div/div/div[2]/div[1]/div/div/div/span"));
        compareButton1.click();

        // Шаг 9: Перейти в "Сравнение", проверить, что имена товаров в сравнении соответствуют именам товаров, добавленных на шагах 5 и 8
        WebElement comparisonLink = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div/main/div[3]/div[1]/div/div/div[2]/div[3]/div/div/div[3]/a"));
        comparisonLink.click();
        WebElement firstProductName = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div/div/div/div/div/a"));
        WebElement secondProductName = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/a"));

        if (firstProductName.getText().equals(secondProductName.getText())) {
            System.out.println("Имена товаров в сравнении соответствуют");
        } else {
            System.out.println("Имена товаров в сравнении не соответ");
        }
    }
}

