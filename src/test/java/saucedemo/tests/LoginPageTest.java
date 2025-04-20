package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void loginPageNavigationTest() throws InterruptedException {
        Thread.sleep(1000);
        loginPage=homePage.navigateToLoginPage();
        Thread.sleep(1000);
        String actLoginPageTitle=loginPage.getLoginPageTitle();
        System.out.println("Act Page Title:"+actLoginPageTitle);
        Assert.assertEquals(actLoginPageTitle, AppConstants.LOGIN_PAGE_TITLE);
    }
    @Test(priority = 2)
    public void forgotPwdExist() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }
    @Test(priority = 3)
    public void appLoginTest() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim()));
        Thread.sleep(1000);
    }




}
