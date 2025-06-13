package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import locators.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;

public class LoginPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    // Locators

    public LoginPage(Page page) {

        super(page);
    }

    public void navigateToLoginPage() {
        logger.info("Navigating to login page...");
        try {
            waitForElementToBeVisible(getLocator("usernameInput"));
            logger.info("Username input is visible.");
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Username input not found on the login page", e);
            throw new RuntimeException("Login page could not be loaded properly.", e);
        }
    }


    public void login(String username, String password) {
        logger.info("Attempting to login with username: {}", username);

        try {
            fill(getLocator("usernameInput"), username);
            logger.debug("Filled username.");

            fill(getLocator("passwordInput"), password);
            logger.debug("Filled password.");

            click(getLocator("loginButton"));
            logger.debug("Clicked login button.");

            page.waitForLoadState(LoadState.NETWORKIDLE);
            logger.info("Page load state: NETWORKIDLE after login attempt.");
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Login failed for user '" + username + "'", e);
            throw new RuntimeException("Login process failed", e);
        }
    }


    public String getErrorMessage() {
        logger.debug("Retrieving error message...");
        try {
            waitForElementToBeVisible(getLocator("errorMessage"));
            String message = page.textContent(getLocator("errorMessage"));
            logger.info("Error message found: {}", message);
            return message;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "No error message found after login attempt", e);
            return "";
        }
    }

} 