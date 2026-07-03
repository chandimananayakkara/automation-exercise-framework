package com.automationexercise.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("🚀 TEST SUITE STARTED: " + context.getName());
        System.out.println("═══════════════════════════════════════");
        ExtentReportManager.initReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("▶️ TEST STARTED: " + testName);
        ExtentReportManager.createTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("✅ TEST PASSED: " + testName);
        ExtentReportManager.getTest().log(Status.PASS, "✅ Test PASSED: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("❌ TEST FAILED: " + testName);

       ExtentReportManager.getTest().log(Status.FAIL,
                "❌ Test FAILED: " + testName);
        ExtentReportManager.getTest().log(Status.FAIL,
                "💥 Cause: " + result.getThrowable());

        try {
            String base64Screenshot = ScreenshotUtil.getScreenshotAsBase64();
            ExtentReportManager.getTest().fail("Screenshot at failure:",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            System.out.println("⚠️ Could not capture screenshot: " + e.getMessage());
        }

       ScreenshotUtil.captureScreenshot(testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("⚠️ TEST SKIPPED: " + testName);
        ExtentReportManager.getTest().log(Status.SKIP, "⚠️ Test SKIPPED: " + testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("🏁 TEST SUITE FINISHED: " + context.getName());
        System.out.println("   Total: " + (context.getPassedTests().size()
                + context.getFailedTests().size() + context.getSkippedTests().size()));
        System.out.println("   ✅ Passed: " + context.getPassedTests().size());
        System.out.println("   ❌ Failed: " + context.getFailedTests().size());
        System.out.println("   ⚠️ Skipped: " + context.getSkippedTests().size());
        System.out.println("═══════════════════════════════════════");
        ExtentReportManager.flushReports();
    }
}