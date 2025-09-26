package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


// Данный класс является утилитным
// В нем размещается приватный конструктор и статичные методы, которые отвечают за обработку данных
public class DataGenerator {

    // Конструктор DataGenerator1 объявлен приватным и пустым.
    // Чтобы нельзя было создать методы класса извне, этот способ обеспечивает безопасность.
    // У конструктора такое же название, как и у класса.
    // Данный конструктор нужен просто пустым для обеспечения безопасности данных.
    private DataGenerator() {
    }

    // Параметр int shift (сдвиг даты в днях) задается извне в тесте
    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        return date;
    }

    public static String generateCity() {
        // String city = faker.address().city(); // Генерация названия города через фейкер

        // Создаем переменную random класса Random, который позволяет использовать методы для генерации случайных чисел
        Random random = new Random();
        // Создаем массив с названиями городов
        String city[] = {"Казань", "Нижний Новгород", "Самара", "Саратов", "Волгоград", "Астрахань"};
        // создаем переменную rand, которой передаем случайное значение массива city с помощью переменной random и
        // метода .nextInt(city.length)
        int rand = random.nextInt(city.length);
        return city[rand];
    }

    public static String generateName(Faker faker) {
        // String name = faker.name().fullName(); // Генерация имен и фамилий через фейкер, у данного метода есть
        // недостаток, нельзя контролировать порядок написания имени и фамилии
        // поэтому будем использовать метод приведенный ниже.

        // В данном методе мы сначала генерируем фамилию faker.name().lastName()
        String name = faker.name().lastName();
        // А затем к фамилии добавляем генерацию имени faker.name().firstName()
        name = name + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone(Faker faker) {
        String phone = faker.phoneNumber().phoneNumber(); // Генерация номера телефона
        return phone;
    }

    // Класс для генерации пользователей, в классе мы пишем слово static.
    // Чтобы класс был независимым от внешнего класса
    public static class Registration {

        // Создаем приватную переменную faker
        private static Faker faker;

        // Создаем приватный конструктор для предотвращения доступа к методам класса и изменения объектов класса
        private Registration() {
        }

        // Метод для генерации пользователя
        // В метод generateUser мы передаем параметр locale - отвечающий за настройку языка
        public static UserInfo generateUser(String locale) {

            // Создаем объект new Faker (...) - необходимое условие генерации данных.
            // В объект new Faker (...) передаем параметр new Locale(locale) - который отвечает за
            // формирование данных на определенном языке.
            faker = new Faker(new Locale(locale));

            // Вызывается метод generateCity() с объектом faker в качестве аргумента.
            // Метод генерирует случайное название города, которое сохраняется в переменной city.
            String city = generateCity();
            // Вызывается метод generateName() с объектом faker в качестве аргумента.
            // Метод генерирует случайное название города, которое сохраняется в переменной name.
            String name = generateName(faker);
            // Вызывается метод generatePhone() с объектом faker в качестве аргумента.
            // Метод генерирует случайное название города, которое сохраняется в переменной phone.
            String phone = generatePhone(faker);

            // Создаётся новый объект класса new UserInfo, куда передаются ранее полученные значения (city, name, phone)

            return new UserInfo(city, name, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
