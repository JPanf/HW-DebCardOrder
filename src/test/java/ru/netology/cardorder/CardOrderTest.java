package ru.netology.cardorder;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class CardOrderTest {

    @BeforeEach
    void openURLBeforeEveryTest() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen=true;
    }
    @Test
    void shouldReturnSuccessMessageIfAllCorrect() {
        $("[data-test-id=name] input").setValue("Василий");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=\"order-success\"]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitIfNoName() {
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitIfNoPhone() {
        $("[data-test-id=name] input").setValue("Василий");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitIfPhoneFormatWrong() {
        $("[data-test-id=name] input").setValue("Василий");
        $("[data-test-id=phone] input").setValue("+7(927)0000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitIfNameIsNotRussian() {
        $("[data-test-id=name] input").setValue("VASILIY");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitIfCheckBoxNotClicked() {
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("button").click();
        $("[data-test-id=agreement].input_invalid").should(exist);
    }
}
