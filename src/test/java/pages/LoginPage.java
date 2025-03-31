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
    }

    public void login(String username, String password) throws InterruptedException {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
        Thread.sleep(3000);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }
} 