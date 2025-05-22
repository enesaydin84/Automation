package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;


public class LoginPage extends BasePage {
    // Locators
    private String usernameInput = "#user-name";
    private String passwordInput = "#password";
    private String loginButton = "#login-button";
    private String errorMessage = "[data-test='error']";
    public LoginPage(Page page) {
        super(page);
    }

    public void navigateToLoginPage() {
        //zaten test başında url e yönlendirildi. burda sadece bekliyor görünüyor mu die
        waitForElementToBeVisible(usernameInput);
    }

    public void login(String username, String password) {
        fill(usernameInput, username);
        fill(passwordInput, password);
        click(loginButton);
        page.waitForLoadState(LoadState.NETWORKIDLE);


    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return page.textContent(errorMessage);
    }
} 