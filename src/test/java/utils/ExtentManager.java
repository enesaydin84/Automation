package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Tarih formatlı rapor ismi oluştur
            String timestamp = new SimpleDateFormat("yyyyMMdd_hh_mm").format(new Date());
            String fileName = "Test-Report-" + timestamp + ".html";
            extent = createInstance(fileName);
        }
        return extent;
    }
    
    private static ExtentReports createInstance(String fileName) {
        // Test Report klasörü oluştur
        String reportFolder = "test-output/testReport/";
        new File(reportFolder).mkdirs(); // Klasör yoksa oluştur
        // Dosya yolunu güncelle
        String fullPath = reportFolder + fileName;

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fullPath);
        htmlReporter.config().setDocumentTitle("Test Automation Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Sauce Demo Test Report - " + new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        return extent;
    }
} 