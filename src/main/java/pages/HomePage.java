package com.qa.opencart.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private Page page;

    // ✅ Locators (String değil, doğrudan Playwright Locator)
    private Locator search;
    private Locator searchIcon;
    private Locator searchPageHeader;
    private Locator loginLink;
    private Locator myAccountLink;

    // ✅ Constructor: Sayfa ile gelen tüm Locator'ları initialize ediyoruz
    public HomePage(Page page) {
        this.page = page;

        // Spesifik locator'lar
        search = page.locator("//input[@class='form-control input-lg']");
        searchIcon = page.locator("//button[@class='btn btn-default btn-lg']");
        searchPageHeader = page.locator("div[id='content'] h1");
        loginLink = page.locator("a:text('Login')").first();
        myAccountLink = page.locator("a[title='My Account']").first();

    }

    // ✅ Sayfa başlığı
    public String getHomePageTitle() {
        String title = page.title();
        System.out.println("Page title: " + title);
        return title;
    }

    // ✅ Sayfa URL
    public String getHomePageUrl() {
        String url = page.url();
        System.out.println("Page URL: " + url);
        return url;
    }

    // ✅ Arama işlemi
    public String doSearch(String productName) {
        search.fill(productName);
        searchIcon.click();
        String header = searchPageHeader.textContent();
        System.out.println("Search header: " + header);
        return header;
    }

    // ✅ Login sayfasına gitme
    public LoginPage navigateToLoginPage() {
        myAccountLink.click();
        loginLink.click();
        return new LoginPage(page);
    }
}
