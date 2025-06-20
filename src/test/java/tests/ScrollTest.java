package tests;

import Users.User;
import Users.UserDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import factory.PlaywrightFactory;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.Random;

public class ScrollTest extends BaseTest {
    
    @Test(description = "Verify page scrolling functionality")
    public void validateScrollFunctionality() {
        BasePage basePage = new BasePage(PlaywrightFactory.getPage());
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());

        User user = UserDataReader.getUser("standard_user");

        loginPage.navigateToLoginPage();
        loginPage.login(user.getUsername(),user.getPassword());
        
        // Get initial position (should be at top)
        Object initialPosition = PlaywrightFactory.getPage().evaluate("window.pageYOffset");
        Assert.assertEquals(initialPosition.toString(), "0", "Page should start at the top");
        
        // Scroll to bottom
        basePage.scrollToBottom();
        
        // Wait briefly for scroll to complete
        PlaywrightFactory.getPage().waitForTimeout(1000);
        
        // Get position after scrolling to bottom
        Object bottomPosition = PlaywrightFactory.getPage().evaluate("window.pageYOffset");
        double bottomOffset = Double.parseDouble(bottomPosition.toString());
        Assert.assertTrue(bottomOffset > 0, "Page should be scrolled down");
        
        // Scroll back to top
        basePage.scrollToTop();
        
        // Wait briefly for scroll to complete
        PlaywrightFactory.getPage().waitForTimeout(1000);
        
        // Verify we're back at the top
        Object finalPosition = PlaywrightFactory.getPage().evaluate("window.pageYOffset");
        Assert.assertEquals(finalPosition.toString(), "0", "Page should be back at the top");
    }
    @Test(description = "Add to Cart Control")
    public void addProductControl() {
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        ProductsPage productsPage=new ProductsPage(PlaywrightFactory.getPage());

        User user = UserDataReader.getUser("standard_user");

        loginPage.navigateToLoginPage();
        loginPage.login(user.getUsername(),user.getPassword());

        Random random = new Random();
        int productIndex = random.nextInt(5) + 1;
        System.out.println(productIndex);
        productsPage.addProductToCart(productIndex);
        if(productsPage.getCartItemCount().equals("1")){
            System.out.println(productsPage.getProductsName(productIndex)+"->Add to cart successful");
        }
        else
            System.out.println(productsPage.getProductsName(productIndex)+"->Could not add to cart");

    }
} 