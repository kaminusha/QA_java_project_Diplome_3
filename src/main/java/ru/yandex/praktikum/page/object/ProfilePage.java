package ru.yandex.praktikum.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    WebDriver webDriver;

    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private final By profileTextLocator = By.xpath(".//*[contains(text(), 'изменить свои персональные данные')]");

    private final By nameField = By.xpath(".//li[1]//input");
    private final By emailField = By.xpath(".//li[2]//input");

    @Step("Ожидание загрузки страницы профиля")
    public void waitProfilePageLoad() {
        new WebDriverWait(webDriver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(profileTextLocator));
    }

    @Step("Получение значения поля «Email»")
    public String getEmailText() {
        return webDriver.findElement(emailField).getAttribute("value");
    }

    @Step("Получение значения поля «Имя»")
    public String getNameText() {
        return webDriver.findElement(nameField).getAttribute("value");
    }
}