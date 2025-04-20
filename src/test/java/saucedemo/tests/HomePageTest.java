package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class HomePageTest extends BaseTest {


    @Test(priority = 1)
    public void homePageTitleTest() throws InterruptedException {
        Thread.sleep(5000);
        String actualTitle=homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test(priority = 1)
    public void homePageUrlTest() throws InterruptedException {
        Thread.sleep(5000);
        String actualUrl=homePage.getHomePageUrl();
        Assert.assertEquals(actualUrl,prop.getProperty("url"));
    }

    @DataProvider
    public Object[][] getProductData(){
        return new Object[][]{
                {"Macbook"},
                {"iMac"},
                {"Samsung"}
        };
    }


    @Test(dataProvider = "getProductData",priority = 2)
    public void doSearchTest(String productName) throws InterruptedException {
        Thread.sleep(5000);
       String actualSearchHeader=homePage.doSearch(productName);
       Assert.assertEquals(actualSearchHeader,"Search - "+productName);
    }


}
