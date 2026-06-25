package org.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void init(String reportPath) {
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest t = extent.createTest(testName);
        test.set(t);
        return t;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flush() {
        if (extent != null) extent.flush();
    }
}