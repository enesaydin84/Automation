package pages;

import com.microsoft.playwright.Page;

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
        page.navigate("https://www.saucedemo.com/v1/");
        waitForElementToBeVisible(usernameInput);
    }

    public void login(String username, String password) {
        fill(usernameInput, username);
        fill(passwordInput, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return page.textContent(errorMessage);
    }
} 