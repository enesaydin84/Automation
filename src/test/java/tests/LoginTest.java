package tests;

import factory.PlaywrightFactory;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    
    @Test(description = "Verify successful login with standard user")
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        ProductsPage productsPage = new ProductsPage(PlaywrightFactory.getPage());

        loginPage.navigateToLoginPage();
        loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
        assertEquals(productsPage.getTitle(), "Products");
    }

    @Test(description = "Verify locked out user cannot login")
    public void lockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());

        loginPage.navigateToLoginPage();
        loginPage.login(prop.getProperty("locked_out_user"),prop.getProperty("password"));

        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(description = "Verify problem user sees broken images")
    public void problemUser() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        ProductsPage productsPage = new ProductsPage(PlaywrightFactory.getPage());

        loginPage.navigateToLoginPage();
        loginPage.login(prop.getProperty("problem_user"),prop.getProperty("password"));

        assertTrue(productsPage.hasBreakImage());
    }
} 