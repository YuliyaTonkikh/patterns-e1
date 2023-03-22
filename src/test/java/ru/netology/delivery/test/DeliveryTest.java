package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $("[data-test-id='date']").$("[class='input__control']").click();
        $("[data-test-id='date']").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
    }

    @Test
    void shouldRequireValidCityIfCityNotIncludeTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue(DataGenerator.generateInvalidCity());
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone']").$("[name='phone']").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldRequireValidNameIfNonCyrillicTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue(DataGenerator.generateValidCity());
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("John Snow");
        $("[data-test-id='phone']").$("[name='phone']").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldRequireAgreementCheckboxTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue(DataGenerator.generateValidCity());
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone']").$("[name='phone']").setValue(DataGenerator.generatePhone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible)
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldRequireValidCityIfEmptyCityFieldTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("");
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone']").$("[name='phone']").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRequireValidNameIfEmptyNameFieldTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue(DataGenerator.generateValidCity());
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("");
        $("[data-test-id='phone']").$("[name='phone']").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldRequireValidPhoneNumberIfEmptyPhoneFieldTest() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue(DataGenerator.generateValidCity());
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(DataGenerator.
                generateDate(DataGenerator.generateRandomDateShift()));
        $("[data-test-id= 'name']").$("[name ='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone']").$("[name='phone']").setValue("");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}