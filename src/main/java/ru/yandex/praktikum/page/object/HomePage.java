package ru.yandex.praktikum.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    private final By enterAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By checkoutButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By personalAccountButton = By.xpath(".//p[contains(@class, 'AppHeader_header__linkText') and contains(@class, 'ml-2') and text()='Личный Кабинет']");
    private final By saucesLink = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By bunsLink = By.xpath(".//span[text()='Булки']/parent::div");
    private final By fillingLink = By.xpath(".//*[text()='Начинки']/parent::div");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку «Войти в аккаунт»")
    public void clickEnterAccountButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(enterAccountButton));
        driver.findElement(enterAccountButton).click();
    }
    @Step("Ожидание появления кнопки «Войти в аккаунт»")
    public void waitForEnterAccountButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(enterAccountButton));
    }
    @Step("Ожидание появления кнопки «Оформить заказ»")
    public void waitCheckoutButton() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }
    @Step("Ожидание появления кнопки «Личный Кабинет»")
    public void waitForPersonalAccountButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(personalAccountButton));
    }
    @Step("Нажатие на кнопку «Личный Кабинет»")
    public void enterPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }
    @Step("Нажатие на вкладку «Булки»")
    public void clickBunsLink() {
        driver.findElement(bunsLink).click();
    }
    @Step("Нажатие на вкладку «Соусы»")
    public void clickSaucesLink() {
        driver.findElement(saucesLink).click();
    }
    @Step("Нажатие на вкладку «Начинки»")
    public void clickFillingsLink() {
        driver.findElement(fillingLink).click();
    }
    @Step("Получение атрибута class у вкладки «Булки»")
    public String getClassNameBuns() {
        return driver.findElement(bunsLink).getAttribute("class");
    }
    @Step("Получение атрибута class у вкладки «Соусы»")
    public String getClassNameSauces() {
        return driver.findElement(saucesLink).getAttribute("class");
    }
    @Step("Получение атрибута class у вкладки «Начинки»")
    public String getClassNameFillings() {
        return driver.findElement(fillingLink).getAttribute("class");
    }
    // Методы для ожидания изменения класса у элементов

    @Step("Ожидание, что вкладка «Булки» станет активной")
    public void waitForBunsActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(bunsLink, "class", "tab_tab_type_current__2BEPc"));
    }
    @Step("Ожидание, что вкладка «Соусы» станет активной")
    public void waitForSaucesActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(saucesLink, "class", "tab_tab_type_current__2BEPc"));
    }
    @Step("Ожидание, что вкладка «Начинки» станет активной")
    public void waitForFillingsActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(fillingLink, "class", "tab_tab_type_current__2BEPc"));
    }


}
