package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    
    @Test(description = "Verify successful login with standard user")
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        ProductsPage productsPage = new ProductsPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        assertEquals(productsPage.getTitle(), "Products");
    }

    @Test(description = "Verify locked out user cannot login")
    public void lockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login("locked_out_user", "secret_sauce");

        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(description = "Verify problem user sees broken images")
    public void problemUser() {
        LoginPage loginPage = new LoginPage(page);
        ProductsPage productsPage = new ProductsPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login("problem_user", "secret_sauce");

        assertTrue(productsPage.hasBreakImage());
    }
} 