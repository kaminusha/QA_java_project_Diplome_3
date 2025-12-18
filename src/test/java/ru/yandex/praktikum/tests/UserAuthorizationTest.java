package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.api.user.User;
import ru.yandex.praktikum.api.user.UserStep;
import ru.yandex.praktikum.page.object.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.utils.Constants.HOST;

// Класс для проверки различных сценариев авторизации пользователя:
public class UserAuthorizationTest extends BaseTest {

    private HomePage homePage;
    private AuthorizationPage authorizationPage;
    private ProfilePage profilePage;
    private RegistrationPage registrationPage;
    private RecoverPasswordPage recoverPasswordPage;

    private User testUser;
    private UserStep userStep = new UserStep();
    private String accessToken;

    // Подготовка тестового окружения
    @Before
    public void setUp() {
        super.setUp();

        // Генерируем тестовые данные
        String name = RandomStringUtils.randomAlphanumeric(6);
        String email = RandomStringUtils.randomAlphanumeric(7) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(8);

        // Создаём объект пользователя
        testUser = new User(email, password, name, "");

        // Регистрация пользователя через API
        ValidatableResponse createResponse = userStep.createUser(testUser);
        createResponse.assertThat().statusCode(SC_OK); // Проверка успешности регистрации

        // Получаем и сохраняем токен доступа
        accessToken = userStep.extractAccessToken(createResponse);
        testUser.setAccessToken(accessToken);

        // Открываем главную страницу
        driver.get(HOST);
        homePage = new HomePage(driver);
    }

    // Тест: вход через кнопку "Войти в аккаунт" на главной странице
    @Test
    @DisplayName("позитивный тест на вход по кнопке «Войти в аккаунт» на главной")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void shouldLoginViaEnterAccountButton() {
        // Переходим к форме авторизации
        homePage.waitForEnterAccountButton();
        homePage.clickEnterAccountButton();

        // Вводим данные и авторизуемся
        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.userDataEntry(testUser.getEmail(), testUser.getPassword());
        authorizationPage.clickEnterButton();

        // Ждём загрузки элементов после входа
        homePage.waitCheckoutButton();
        homePage.enterPersonalAccountButton();

        // Проверяем email в профиле
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                testUser.getEmail().toLowerCase(), profilePage.getEmailText().toLowerCase());
    }

    // Тест: вход через кнопку "Личный кабинет" на главной странице
    @Test
    @DisplayName("позитивный тест на вход по кнопке «Личный кабинет»")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void shouldLoginViaPersonalAccountButton() {
        // Переходим к форме авторизации через «Личный кабинет»
        homePage.waitForPersonalAccountButton();
        homePage.enterPersonalAccountButton();

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();

        // Вводим данные и авторизуемся
        authorizationPage.userDataEntry(testUser.getEmail(), testUser.getPassword());
        authorizationPage.clickEnterButton();
        // Ждём загрузки элементов после входа
        homePage.waitCheckoutButton();
        homePage.enterPersonalAccountButton();
        // Проверяем email в профиле
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                testUser.getEmail().toLowerCase(), profilePage.getEmailText().toLowerCase());
    }

    // Тест: вход из формы регистрации (через ссылку "Уже зарегистрированы? Войти")
    @Test
    @DisplayName("позитивный тест на вход по кнопке в форме регистрации")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void shouldLoginFromRegistrationForm() {
        // Переходим к форме регистрации
        homePage.waitForEnterAccountButton();
        homePage.clickEnterAccountButton();

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();
        authorizationPage.clickRegistrationLink();

        registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageLoad();

        // Переходим к авторизации через ссылку
        registrationPage.clickAlreadyRegisteredLink();
        authorizationPage.waitForPageLoad();

        //  Вводим данные и авторизуемся
        authorizationPage.userDataEntry(testUser.getEmail(), testUser.getPassword());
        authorizationPage.clickEnterButton();

        //  Ждём загрузки элементов после входа
        homePage.waitCheckoutButton();
        homePage.enterPersonalAccountButton();

        // Проверяем email в профиле
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();

        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                testUser.getEmail().toLowerCase(), profilePage.getEmailText().toLowerCase());
    }

    // Тест: вход из формы восстановления пароля (через ссылку "Войти")
    @Test
    @DisplayName("позитивный тест на вход по кнопке в форме восстановления пароля")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void shouldLoginFromRecoverPasswordForm() {
        // Переходим к форме восстановления пароля
        homePage.waitForEnterAccountButton();
        homePage.clickEnterAccountButton();

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();
        authorizationPage.clickRecoverPasswordLink();

        recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.waitForPageLoad();

        // Возвращаемся к авторизации через ссылку
        recoverPasswordPage.clickRememberedPassword();
        authorizationPage.waitForPageLoad();

        // Вводим данные и авторизуемся
        authorizationPage.userDataEntry(testUser.getEmail(), testUser.getPassword());
        authorizationPage.clickEnterButton();

        // Ждём загрузки элементов после входа
        homePage.waitCheckoutButton();
        homePage.enterPersonalAccountButton();

        // Проверяем email в профиле
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();

        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                testUser.getEmail().toLowerCase(), profilePage.getEmailText().toLowerCase());
    }

    // Очистка тестового окружения
    @After
    public void tearDown() {
        if (testUser != null && testUser.getAccessToken() != null) {
            userStep.deleteUser(testUser);
        }
        super.tearDown();
    }
}