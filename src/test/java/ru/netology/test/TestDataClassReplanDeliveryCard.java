package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        Faker faker = new Faker (new Locale("ru"));


        //Объявляем язык на котором будет сгенерирован пользователь "ru"
        var languageUser = DataGenerator.Registration.generateUser("ru");

        //Объявляем количество дней на которое будет смещена дата
        var meetingDateShift = DataGenerator.generateDate(3);
        var nextMeetingDateShift = DataGenerator.generateDate(4);

        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity(faker));

        // Заполняем поле "Фамилия имя" из фейкера
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName(faker));

        // Заполняем поле "Телефон" валидным значением из фейкера
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone(faker));

        // Кликаем по чекбоксу
        $("label[data-test-id='agreement']").click();

        // Кликаем по кнопке "Запланировать"
        $("span.button__text").click();

        // Кликаем по кнопке "Запланировать" повторно
        $("span.button__text").shouldHave(Condition.text("Запланировать")).click();

        // Кликаем по кнопке "Запланировать" повторно
        $("[data-test-id='replan-notification'] span.button__text").shouldHave(Condition.text("Перепланировать"), Duration.ofSeconds(10)).shouldBe(Condition.visible).click();

        $("[data-test-id='success-notification'] [class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + (meetingDateShift)), Duration.ofSeconds(10)).shouldBe(Condition.visible);

    }
}
