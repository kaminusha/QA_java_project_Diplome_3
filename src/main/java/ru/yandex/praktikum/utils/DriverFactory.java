package ru.yandex.praktikum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Класс для создания и настройки WebDriver с поддержкой Chrome и Яндекс Браузера
public class DriverFactory {

    // Статическое поле для хранения настроек из properties-файла
    private static final Properties properties = new Properties();

    static { // Статический блок инициализации — выполняется при загрузке класса
        try (InputStream input = DriverFactory.class.getResourceAsStream("/browser.properties")) {
            if (input == null) { // Проверяем, найден ли файл в ресурсах
                throw new RuntimeException(
                        "Файл browser.properties не найден в resources. Проверьте путь: src/test/resources/browser.properties"
                );
            }
            // Загружаем свойства из файла
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки browser.properties: " + e.getMessage(), e);
        }
    }

    // Основной метод для получения настроенного экземпляра WebDriver
    public static WebDriver getNewDriver() {
        // Получаем тип браузера из properties (по умолчанию — "yandex")
        String browser = properties.getProperty("browser", "yandex").trim().toLowerCase();

        System.out.println("Используемый браузер: " + browser);

        // Выбираем способ создания драйвера в зависимости от типа браузера
        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException(
                        "Неподдерживаемый браузер: '" + browser + "'. Допустимые значения: 'chrome', 'yandex'"
                );
        }
    }

    // Создаём и настраиваем драйвер для Google Chrome
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        // Применяем общие настройки для браузера
        configureCommonOptions(options);

        // Автоматически подбираем и настраиваем ChromeDriver через WebDriverManager
        WebDriverManager.chromedriver().setup();
        System.out.println("ChromeDriver успешно настроен");
        return new ChromeDriver(options);
    }

    // Создаём и настраиваем драйвер для ЯндексБраузера
    private static WebDriver createYandexDriver() {
        ChromeOptions options = new ChromeOptions();
        // Применяем общие настройки для браузера
        configureCommonOptions(options);

        // Получаем путь к исполняемому файлу Яндекс Браузера из настроек
        String yandexPath = properties.getProperty("yandex.browser.path");
        if (yandexPath == null || yandexPath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Укажите путь к ЯндексБраузеру в параметре 'yandex.browser.path' в browser.properties. Пример для macOS:\n" +
                            "/Applications/Yandex.app/Contents/MacOS/Yandex"
            );
        }
        // Указываем путь к бинарнику Яндекс Браузера
        options.setBinary(yandexPath);
        System.out.println("Путь к ЯндекcБраузеру: " + yandexPath);

        // Получаем версию браузера из настроек (по умолчанию 140)
        String browserVersion = properties.getProperty("browser.version", "140");
        WebDriverManager.chromedriver()
                .browserVersion(browserVersion)
                .setup();
        System.out.println("Версия браузера: " + browserVersion);

        return new ChromeDriver(options);
    }

    // Общие настройки для Chrome и ЯндексБраузера
    private static void configureCommonOptions(ChromeOptions options) {
        // Разрешаем кросс‑доменные запросы
        options.addArguments("--remote-allow-origins=*");
        // Отключаем песочницу (необходимо для работы в некоторых окружениях)
        options.addArguments("--no-sandbox");
        // Отключаем использование /dev/shm (актуально для Docker)
        options.addArguments("--disable-dev-shm-usage");
        // Запускаем браузер в развёрнутом окне
        options.addArguments("--start-maximized");
    }
}