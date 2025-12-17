package ru.yandex.praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.utils.Constants;
import ru.yandex.praktikum.utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.getNewDriver();
        driver.get(Constants.HOST);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}