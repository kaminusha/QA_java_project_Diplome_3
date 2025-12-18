package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.page.object.AuthorizationPage;
import ru.yandex.praktikum.page.object.HomePage;
import ru.yandex.praktikum.page.object.ProfilePage;
import ru.yandex.praktikum.page.object.RegistrationPage;
import ru.yandex.praktikum.utils.Constants;

// Класс - проверки функционала регистрации пользователей
public class UserRegistrationTest extends BaseTest {

   private HomePage homePage;
   private AuthorizationPage authorizationPage;
   private RegistrationPage registrationPage;
   private ProfilePage profilePage;

    // Генерируем случайные данные
   private final String validName = RandomStringUtils.randomAlphanumeric(10);
   private final String validEmail = RandomStringUtils.randomAlphanumeric(7) + "@gufum.com";
   private final String validPassword = RandomStringUtils.randomAlphanumeric(6);
   private final String invalidShortPassword = RandomStringUtils.randomAlphanumeric(5);

   // Тест: успешная регистрация нового пользователя
    @Test
    @DisplayName("позитивный тест на успешную регистрацию пользователя")
    @Description("Позитивный тест на создание пользователя с заполненными полями")
    public void shouldRegisterUserWithValidData() {
        //вход на главную страницу и переход в "Личный кабинет"
        homePage = new HomePage(driver);
        homePage.waitForPersonalAccountButton();
        homePage.enterPersonalAccountButton();

        //переход к форме регистрации
        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();
        authorizationPage.clickRegistrationLink();

        //заполняем форму регистрации корректными данными
        registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageLoad();
        registrationPage.fillInRegistrationForm(validName, validEmail, validPassword );

        //Ожидаем перехода на страницу входа после успешной регистрации
        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();
        Assert.assertEquals(Constants.LOGIN_PAGE, driver.getCurrentUrl());

        //вводим данные с которыми зарегистрировались
        authorizationPage.userDataEntry(validEmail, validPassword );
        authorizationPage.clickEnterButton();
        homePage.waitForPersonalAccountButton();

        //переходим в профиль
        homePage.enterPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();

        //проверяем данные
        Assert.assertEquals(validName, profilePage.getNameText());
        Assert.assertEquals("Email не совпадает (игнорируя регистр)", validEmail.toLowerCase(), profilePage.getEmailText().toLowerCase());
    }

    // Тест: попытка регистрации с некорректным паролем (менее 6 символов)
    @Test
    @DisplayName("Проверка на невозможность создание пользователя с некорректным паролем")
    @Description("Негативный тест на невозможность создания пользователя с 5 значным паролем")
    public void shouldNotRegisterUserWithShortPassword() {
        // Открываем главную страницу и переходим в "Личный кабинет"
        homePage = new HomePage(driver);
        homePage.waitForPersonalAccountButton();
        homePage.enterPersonalAccountButton();

        // Переходим к форме регистрации
        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.waitForPageLoad();
        authorizationPage.clickRegistrationLink();

        // Заполняем форму с некорректным паролем (коротким)
        registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageLoad();
        registrationPage.fillInRegistrationForm(validName, validEmail, invalidShortPassword);

        // Проверяем сообщение об ошибке под полем "Пароль"
        Assert.assertEquals("Некорректный пароль", registrationPage.getPasswordFieldErrorText());

        // Проверяем, что пользователь остался на странице регистрации
        Assert.assertEquals(Constants.REGISTER_PAGE, driver.getCurrentUrl());
    }
}