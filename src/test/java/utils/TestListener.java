//package utils;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.microsoft.playwright.Page;
//import com.aventstack.extentreports.Status;
//import factory.PlaywrightFactory;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import java.util.Base64;
//
//public class TestListener implements ITestListener {
//    private static ExtentReports extent = ExtentManager.getInstance();
//    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
//                result.getMethod().getDescription());
//        test.set(extentTest);
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//
//        try {
//            Page page = PlaywrightFactory.getPage();
//            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
//            String base64 = Base64.getEncoder().encodeToString(screenshotBytes);
//
//            test.get().log(Status.PASS, "Test passed");
//            test.get().addScreenCaptureFromBase64String(base64, "Passed Screenshot");
//        } catch (Exception e) {
//            test.get().log(Status.PASS, "Test passed, but screenshot capture failed: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//
//        try {
//            Page page = PlaywrightFactory.getPage();
//            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
//            String base64 = Base64.getEncoder().encodeToString(screenshotBytes);
//
//            test.get().fail(result.getThrowable());
//            test.get().addScreenCaptureFromBase64String(base64, "Failure Screenshot");
//        } catch (Exception e) {
//            test.get().fail("Screenshot capture failed: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//
//        test.get().skip(result.getThrowable());
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//
//        extent.flush();
//    }
//}