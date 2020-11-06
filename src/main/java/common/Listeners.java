package common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

/**
 * <h1>Listeners Class</h1> <B>Main purpose of Listeners Class is for extend
 * test reporting beforetest, aftertest reporting in html file which will generated in test-output
 * folder This class is implemented in testng.xml file in Suite tag It will generate reports and
 * give timings before test case starts and after test case ends and total time of test case
 * run.</B>
 *
 * @author Shivanshu Singla
 * @version 1.0
 * @since 05-11-2020
 */

public class Listeners implements ITestListener, IInvokedMethodListener, IReporter {

  private static Date startDate;
  private static File screenshotPath = new File(
      System.getProperty(Constants.USER_DIR) + File.separator
          + "Screenshots" + File.separator + BaseUtils
          .getCurrentDateTime(Constants.DATE_TIME_FORMAT) + ".png");
  private ExtentReports extent;
  private ExtentTest test;

  /**
   * <h2>onStart Method</h2> <B>Main purpose of this method is to check internet
   * connection and jdk is installed in the system or not and give automation start date and
   * time.</B>
   */

  @Override
  public void onStart(ITestContext arg0) {
    // check Internet Connection (terminate the suite if Internet is not
    // connected on machine)
    System.out.println("Running before suite......");
    boolean isInternetConnected = BaseUtils.netIsAvailable();
    if (isInternetConnected) {
      Reporter.log("Internet connection is fine...", true);
      if (System.getProperty(Constants.JAVA_VERSION).contains("1.")) {
        System.out.println("JDK is installed in your System & Java Version is - "
            + System.getProperty(Constants.JAVA_VERSION));
      } else {
        System.out.println("JDK is not installed in your System");
      }
    } else {
      Reporter.log("Internet is not connected....", true);
      Reporter.log("Hence... terminating the suite", true);
      onFinish((ITestContext) this);
    }
    startDate = BaseUtils.getCurrentDateTime(Constants.DATE_TIME_FORMAT);
    String message = "Automation Execution started on " + startDate + " on Thread id:- "
        + Thread.currentThread().getId();

    System.out.println(message);
    message = "<font style=\"color:green\">" + message + "</font></br>";
    Reporter.log(message, true);
  }

  /**
   * <h2>onFinish Method</h2> <B>Main purpose of this method is to give how much
   * time automation suite taking to run whole whole suite in seconds.</B>
   */

  @Override
  public void onFinish(ITestContext arg0) {
    DateFormat dateFormat = new SimpleDateFormat();
    Date endDate = new Date();
    double diff = (endDate.getTime() - startDate.getTime()) / 60000.00;
    String message = "";
    if (diff < 1) {
      message = "Automation Execution finished after :- "
          + (endDate.getTime() - startDate.getTime()) / 1000.00
          + " seconds. At " + dateFormat.format(endDate) + " on Thread id:- "
          + Thread.currentThread().getId();
    } else {
      message = "Automation Execution finished after :- " + diff + " minutes. At " + dateFormat
          .format(endDate)
          + " on Thread id:- " + Thread.currentThread().getId();
    }

    System.out.println(message);
    message = "<font style=\"color:green\">" + message + "</font></br>";
    Reporter.log(message, true);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
  }

  /**
   * <h2>onTestFailure Method</h2> <B>Main purpose of this method is to give how
   * much time automation suite taking to run whole whole suite in seconds.</B>
   */

  @Override
  public void onTestFailure(ITestResult testResult) {
    Reporter.log("***************EXECUTION OF TESTCASE ENDS HERE at : "
        + BaseUtils.getCurrentDateTime(Constants.DATE_TIME_FORMAT) + " ***************", true);
  }

  /**
   * <h2>onTestSkipped Method</h2> <B>Main purpose of this method is to give user
   * a information that test cases are skipped.</B>
   */

  @Override
  public void onTestSkipped(ITestResult testResult) {
    String message = "Test case skipped - '" + testResult.getName() + "'";
    System.out.println(message);
    message = "<font style=\"color:orange\">" + message + "</font></br>";
    Reporter.log(message, true);

  }

  /**
   * <h2>onTestStart Method</h2> <B>Main purpose of this method is to give user a
   * information that test cases are now started at particular date and time.</B>
   */

  @Override
  public void onTestStart(ITestResult testResult) {
    Reporter.setCurrentTestResult(testResult);
    Reporter.log("<B>Test '" + testResult.getName() + "' Started on '" + startDate + "'</B>", true);
  }

  /**
   * <h2>onTestSuccess Method</h2> <B>Main purpose of this method is to give user
   * a information of test case is passed and successed at particular date and time</B>
   */

  @Override
  public void onTestSuccess(ITestResult testResult) {
    Reporter.setCurrentTestResult(testResult);
    List<String> reporterOutput = Reporter.getOutput(testResult);
    DateFormat dateFormat = new SimpleDateFormat();
    Date endDate = new Date();
    Reporter.log(
        "<B>Test '" + testResult.getName() + "' Passed on '" + dateFormat.format(endDate) + "'</B>",
        true);
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  /**
   * <h2>generateReport Method</h2> <B>Main purpose of this method is to generate
   * extent reports in test-output folder which is located at base directory. and will send as a
   * attachment along with the mail. It will give a whole test case status and expections details in
   * mail.</B>
   */

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
      String outputDirectory) {
    extent = new ExtentReports(outputDirectory + File.separator + "ExtentReportTestNG.html", true);

    for (ISuite suite : suites) {
      Map<String, ISuiteResult> result = suite.getResults();

      for (ISuiteResult r : result.values()) {
        ITestContext context = r.getTestContext();

        buildTestNodes(context.getPassedTests(), LogStatus.PASS);
        buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
        buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
      }
    }
    extent.flush();
    extent.close();
  }

  private void buildTestNodes(IResultMap tests, LogStatus status) {
    if (tests.size() > 0) {
      for (ITestResult result : tests.getAllResults()) {
        test = extent.startTest(result.getMethod().getMethodName());
        if (result.getStatus() == ITestResult.FAILURE) {
          String file = test.addScreenCapture(screenshotPath.getAbsolutePath());
          test.log(LogStatus.FAIL, "<b style=\"color:red\">'" + result.getMethod().getMethodName()
              + "' - Test method is failed</b>", file);
        }
        for (String message : Reporter.getOutput(result)) {
          test.log(LogStatus.INFO, message);
        }
        for (String group : result.getMethod().getGroups()) {
          test.assignCategory(group);
        }
        String message;
        if (status.toString().toLowerCase().contains("pass")) {
          message = "<b style=\"color:green\">Test " + status.toString().toLowerCase() + "ed</b>";
        } else if (status.toString().toLowerCase().contains("fail")) {
          message = "<b style=\"color:red\">Test " + status.toString().toLowerCase() + "ed</b>";
        } else {
          message = "<b style=\"color:yellow\">Test " + status.toString().toLowerCase() + "ed</b>";
        }
        if (result.getThrowable() != null) {
          message = result.getThrowable().getMessage();
        }
        test.log(status, message);
        extent.endTest(test);
      }
    }
  }

}