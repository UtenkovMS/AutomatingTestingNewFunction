package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class TestingMeetingTransferFunction {

    @Test
    void sholdFillFieldsAndSubmitForm() {

        // Создаем утилиту фейкер
        Faker faker = new Faker(new Locale("ru"));

        String fullName = faker.name().fullName(); // Генерация имени
        String city = faker.address().city();
        String phoneNumber = faker.phoneNumber().phoneNumber();

        // Открываем веб-страницу http://localhost:9999
        Selenide.open("http://localhost:9999");

        // Заполняем поле "Город" c помощью фейкера
        $("[data-test-id='city'] input").setValue(city);

        SelenideElement dataInput = $("[data-test-id='date'] input");

        // Очищаем поле ввода даты
        // C помощью метода .sendKeys() и команды Keys. вводим задаем сочетание клавиш Ctr и a, чтобы выделить в поле текст
        dataInput.sendKeys(Keys.CONTROL + "a");

        // С помощью метода .sendKeys() и команды Keys. задаем нажатие клавиши Del, чтобы удалить ранее выделенный текст
        dataInput.sendKeys(Keys.DELETE);

        // LocalDate - тип данных соответствующий дате.
        // Создаем переменную date.
        // С помощью класса LocalDate задаем текущую дату с помощью метода .now()
        // И к текущей дате с помощью метода .plusDays(3) добавляем три дня вперед.

        LocalDate date = LocalDate.now().plusDays(3);

        // DateTimeFormatter - класс форматирующий дату.
        // Создаем переменную formatter.
        // С помощью класса DateTimeFormatter задаем формат даты .ofPattern("dd.MM.yyyy")

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Тип данных для переменной formatDate задается String (текстовый)
        // Метод date.format() преобразует дату из числового типа данных в строчный тип данных.

        String formatDate = date.format(formatter);

        // Устанавливаем новую дату
        dataInput.setValue(formatDate);

        // Заполняем поле "Фамилия имя" из фейкера
        $("[data-test-id='name'] input").setValue(fullName);

        // Заполняем поле "Телефон" валидным значением из фейкера
        $("[data-test-id='phone'] input").setValue(phoneNumber);

        // Кликаем по чекбоксу
        $("label[data-test-id='agreement']").click();

        // Кликаем по кнопке "Запланировать"
        $("span.button__text").click();

        // Кликаем по кнопке "Запланировать" повторно
        $("span.button__text").shouldHave(Condition.text("Запланировать")).click();

        // Кликаем по кнопке "Запланировать" повторно
        $("[data-test-id='replan-notification'] span.button__text").shouldHave(Condition.text("Перепланировать"), Duration.ofSeconds(5)).shouldBe(Condition.visible).click();

        $("[data-test-id='success-notification'] [class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + (formatDate)), Duration.ofSeconds(10)).shouldBe(Condition.visible);

    }

}
