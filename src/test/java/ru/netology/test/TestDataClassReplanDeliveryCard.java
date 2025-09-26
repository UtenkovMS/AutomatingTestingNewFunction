package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDataClassReplanDeliveryCard {

    // @BeforeEach - это аннотация в JUnit.
    // В этом разделе пишут методы, которые будут выполняться перед каждым последующим тестом.
    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }


    @Test
    void shouldReplanDeliveryCard() {

        //Объявляем язык на котором будет сгенерирован валидный пользователь "ru"
        var validUser = DataGenerator.Registration.generateUser("ru");

        //Объявляем количество дней на которое будет смещена дата
        var meetingDateShift = DataGenerator.generateDate(3);
        //Объявляем количество дней на которое будет смещена дата перепланирования
        var nextMeetingDateShift = DataGenerator.generateDate(5);

        // Заполняем поле "Город"
        $("[data-test-id='city'] input").setValue(validUser.getCity());

        // Заполняем поле "Фамилия имя"
        $("[data-test-id='name'] input").setValue(validUser.getName());

        // Заполняем поле "Телефон"
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());

        // Кликаем по чекбоксу - ставим галочку
        $("label[data-test-id='agreement']").click();

        // Кликаем по кнопке "Запланировать"
        $("span.button__text").click();

        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + (meetingDateShift)), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);

        $("[data-test-id='date'] input")

                // Метод Keys.chord (...) позволяет нажать одновременное две клавиши
                // В моем случае это (CONTROL, "A") далее мы ставим + и говорим нажать
                // клавишу (Keys.DELETE)
                .sendKeys(Keys.chord(Keys.CONTROL, "A") + (Keys.DELETE));

        // Устанавливаем дату перепланирования
        $("[data-test-id='date'] input").setValue(nextMeetingDateShift);

        // Кликаем по кнопке "Запланировать" повторно
        $("span.button__text").shouldHave(Condition.text("Запланировать")).click();

        // Кликаем по кнопке "Перепланировать"
        $("[data-test-id='replan-notification'] span.button__text")
                .shouldHave(Condition.text("Перепланировать"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible).click();

        // Проверяем условие видимоти теста и значения даты перепланирования
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + (nextMeetingDateShift)), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);

    }
}
