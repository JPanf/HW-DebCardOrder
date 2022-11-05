package ru.netology.cardorder;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardOrderTest {

   // private WebDriver driver;

  //  @BeforeAll
  //  static void SetUp (){
     //   System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
   // }
  /*  @BeforeEach
    void BeforeEveryTest () {
        driver = new ChromeDriver();
    }
    @AfterEach
    void tearsDown(){
        driver.quit();
        driver=null;
    }*/
  /*ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--no-sandbox");
options.addArguments("--headless");*/



    @Test
    void shoulReturnSuccesMessageIfAllCorrect(){
       /*driver.get("http://localhost:9999");
        //List<WebElement> elements = driver.findElements(By.className("input__control"));
        //elements.get(0).sendKeys("Василий");
        //elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        */
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=\"order-success\"]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitIfNoName (){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldNotSubmitIfNoPhone (){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void ShouldNotSubmitIfPhoneFormatWrohg (){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+7(927)0000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void ShouldNotSubmitIfNameIsNotRussian (){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("VASILIY");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
}
