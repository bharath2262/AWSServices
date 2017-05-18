package framework;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {
    protected static ExtentReports report;
    public String testName;
    protected ExtentTest testReporter;
    @BeforeMethod
    public void beforeMethod(Method caller) {
        //TODO : Get the test name & description from Excelsheet and pass it to the getTest method
        testName = caller.getName();
        if (report == null) {
            report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
        }
        testReporter = report.startTest(testName);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            testReporter.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            testReporter.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            testReporter.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        }
        report.endTest(testReporter);
    }

    @BeforeSuite
    public void initReport() {
        BaseTest.report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
        report.loadConfig(new File("./extent-config.xml"));
    }

    @AfterSuite
    public void endReport() {
        if (report != null) {
            report.flush();
            // report.close();
        }
    }

    public void logStep(LogStatus status, String expected, String actual) {
        testReporter.log(status, expected, actual);
    }

    public void logStep(LogStatus status, String result) {
        testReporter.log(status, result);
    }

    public void logStepWithResponse(LogStatus status, String expected, String actual, String response) {

        testReporter.log(status, expected, actual + response);
    }

    public static Properties loadConfig(String filePath) {
        Properties properties = new Properties();
        try {
            InputStream in = new FileInputStream(filePath);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading " + filePath + ".properties");
        }
        return properties;
    }

}
