package ru.yandex.praktikum.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoverPasswordPage {
    WebDriver webDriver;

    public RecoverPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private final By restoreFormHeading = By.xpath(".//*[text()='Восстановление пароля']");

    private final By rememberedPasswordLink = By.xpath(".//a[text()='Войти']");

    @Step("Ожидание загрузки страницы «Восстановление пароля»")
    public void waitForPageLoad() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(restoreFormHeading));
    }

    @Step("Нажатие на ссылку «Войти» под формой восстановления")
    public void clickRememberedPassword() {
        webDriver.findElement(rememberedPasswordLink).click();
    }
}