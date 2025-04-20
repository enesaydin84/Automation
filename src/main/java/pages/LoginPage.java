package com.qa.opencart.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    // ✅ Locators (Artık String değil, doğrudan Locator)
    private Locator emailId;
    private Locator password;
    private Locator loginButton;
    private Locator forgotPwdLink;
    private Locator logoutLink;

    // ✅ Constructor: Sayfa açıldığında Locator'ları initialize ediyoruz
    public LoginPage(Page page) {
        this.page = page;

        // Locator'lar
        emailId = page.getByPlaceholder("E-Mail Address");
        password = page.getByPlaceholder("Password");
        loginButton = page.locator("input[value='Login']");
        forgotPwdLink = page.getByText("Forgotten Password").first();
        logoutLink = page.locator("//a[@class='list-group-item'][normalize-space()='Logout']");

    }

    // ✅ Sayfa başlığını döndürür
    public String getLoginPageTitle() {
        return page.title();
    }

    // ✅ "Forgotten Password" linki var mı kontrol eder
    public boolean isForgotPwdLinkExist() {
        return forgotPwdLink.isVisible();
    }

    // ✅ Login işlemi
    public boolean doLogin(String appUsername, String appPassword) throws InterruptedException {
        System.out.println("App creds: " + appUsername + ":" + appPassword);

        emailId.fill(appUsername);
        password.fill(appPassword);
        loginButton.click();
        Thread.sleep(2000);

        // Başarı kontrolü
        if (logoutLink.isVisible()) {
            System.out.println("User is Success...:");
            return true;
        }

        System.out.println("Username or Password wrong...");
        return false;
    }
}
