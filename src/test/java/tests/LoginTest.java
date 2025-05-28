package tests;

import Users.User;
import Users.UserDataReader;
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

        User user = UserDataReader.getUser("standard_user");

        loginPage.navigateToLoginPage();
        loginPage.login(user.getUsername(),user.getPassword());
        assertEquals(productsPage.getTitle(), "Products");
    }

    @Test(description = "Verify locked out user cannot login")
    public void lockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());

        User user = UserDataReader.getUser("locked_out_user");

        loginPage.navigateToLoginPage();
        loginPage.login(user.getUsername(),user.getPassword());

        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(description = "Verify problem user sees broken images")
    public void problemUser() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        ProductsPage productsPage = new ProductsPage(PlaywrightFactory.getPage());

        User user = UserDataReader.getUser("problem_user");

        loginPage.navigateToLoginPage();
        loginPage.login(user.getUsername(),user.getPassword());

        assertTrue(productsPage.hasBreakImage());
    }
} 