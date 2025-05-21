//package utils;
//
//import org.testng.IRetryAnalyzer;
//import org.testng.ITestResult;
//
//public class RetryAnalyzer implements IRetryAnalyzer {
//
//    private int retryCount = 0;
//    private final int maxRetryCount = 2; // Maksimum tekrar sayısı
//
//    @Override
//    public boolean retry(ITestResult result) {
//        if (retryCount < maxRetryCount) {
//            retryCount++;
//            return true; // Test tekrar denenecek
//        }
//        return false; // Artık tekrar yok
//    }
//}
