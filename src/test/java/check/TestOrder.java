package check;

import orderfilling.OrderFilling;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobject.HomePage;
import pageobject.OrderPage;

@RunWith(Parameterized.class)
public class TestOrder {
    public ChromeDriver driver;
    public OrderPage objOrderPage;
    public HomePage objHomePage;
    public OrderFilling objOrderFilling;
    private final String name;
    private final String surname;
    private final String address;
    private final String phoneNumber;
    private final String station;
    private final String comment;
    private final By clickOrderButton;

    public TestOrder(String name, String surname, String address, String phoneNumber, String station, String comment, By clickOrderButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.station = station;
        this.comment = comment;
        this.clickOrderButton = clickOrderButton;
    }

    @Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Иван", "Иванов", "112233, г Москва, ул Ивановская, д 11", "81234567890", "Спартак", "Комментарий 1", HomePage.orderButtonTop},
                {"Сергей", "Сергеевич", "445566, г Тюмень, ул Пушкина, д 17", "87945612322", "Беговая", " Комментарий 2", HomePage.orderButtonDown}};
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ebogdanova.OPENINTEGRATION\\WebDriver\\bin\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        objOrderPage = new OrderPage(this.driver);
        objHomePage = new HomePage(this.driver);
        objOrderFilling = new OrderFilling(this.driver);
        objHomePage.getCookie().click();
    }
    @Test
    public void orderCreation() {
        /*ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ebogdanova.OPENINTEGRATION\\WebDriver\\bin\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        objOrderPage = new OrderPage(this.driver);
        objHomePage = new HomePage(this.driver);
        objOrderFilling = new OrderFilling(this.driver);
        objHomePage.getCookie().click();*/
        objHomePage.click2(this.clickOrderButton);
        objOrderFilling.OrderFilling_part1(this.name, this.surname, this.address, this.phoneNumber, this.station);
        objHomePage.click(this.objOrderPage.getNextButton());
        objOrderFilling.OrderFilling_part2();
        objOrderPage.getColour().click();
        objOrderPage.getComment().sendKeys(new CharSequence[]{this.comment});
        objOrderPage.getOderButton().click();
        objOrderPage.getConfirmationButton().click();
        Assert.assertTrue("Отсутствует сообщение об успешном завершении заказа", this.objOrderPage.isDisplayed(this.objOrderPage.orderConfirmedHeader));
        //driver.quit();
    }

    @After
    public void teardown() {
        driver.quit();
    }
}