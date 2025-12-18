package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.page.object.HomePage;


// Класс - проверки на переключение между разделами («Булки», «Соусы», «Начинки»)
public class BurgerConstructorSectionSwitchingTest extends BaseTest {
   private HomePage homePage;

   // Тест - переключение с раздела "Соусы" на раздел "Булки"
    @Test
    @DisplayName("Проверка: переключение на раздел «Булки» с раздела «Соусы»")
    @Description("При открытии главной страницы на разделе «Булки» переход на раздел «Соусы» и обратно. Проверяется наличие активного CSS-класса у вкладки.")
    public void shouldSwitchToBunsSectionFromSauces() {
        // Инициализируем страницу
        homePage = new HomePage(driver);
        // Ждём появления кнопки "Войти в аккаунт"
        homePage.waitForEnterAccountButton();
        // Переходим в раздел "Соусы"
        homePage.clickSaucesLink();
        // Ждём, пока вкладка "Соусы" станет активной
        homePage.waitForSaucesActive(10);
        // Переходим в раздел "Булки"
        homePage.clickBunsLink();
        // Ждём, пока вкладка "Булки" станет активной
        homePage.waitForBunsActive(10);

        // Проверяем, что у вкладки "Булки" появился активный CSS‑класс
        Assert.assertThat(homePage.getClassNameBuns(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }

    // Тест: переключение на раздел "Соусы" из раздела "Булки"
    @Test
    @DisplayName("Переключение на раздел «Соусы»")
    @Description("Пользователь может переключиться с раздела «Булки» на раздел «Соусы». Проверяется, что вкладка «Соусы» становится активной после клика.")
    public void shouldSwitchToSaucesSection() {
        // Инициализируем страницу
        homePage = new HomePage(driver);
        // Ждём появления кнопки "Войти в аккаунт"
        homePage.waitForEnterAccountButton();
        // Переходим в раздел "Соусы"
        homePage.clickSaucesLink();
        // Ждём активации вкладки
        homePage.waitForSaucesActive(10);
        // Проверяем активный CSS‑класс вкладки "Соусы"
        Assert.assertThat(homePage.getClassNameSauces(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }

    // Тест: переключение на раздел "Начинки" из раздела "Булки"
    @Test
    @DisplayName("Переключение на раздел «Начинки»")
    @Description("Пользователь может переключиться на раздел «Начинки». Проверяется, что вкладка «Начинки» становится активной после клика.")
    public void shouldSwitchToFillingsSection() {
        // Инициализируем страницу
        homePage = new HomePage(driver);
        // Ждём появления кнопки "Войти в аккаунт"
        homePage.waitForEnterAccountButton();
        // Переходим в раздел "Начинки"
        homePage.clickFillingsLink();
        // Ждём активации вкладки
        homePage.waitForFillingsActive(10);
        // Проверяем активный CSS‑класс вкладки "Начинки"
        Assert.assertThat(homePage.getClassNameFillings(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }
}