package common;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import java.io.File;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CucumberSuite {

  TestNGCucumberRunner testNGCucumberRunner;


  FileDataReader fileDataReader = new FileDataReader();

  /**
   * <p>
   * This method is used to delete logs folder files which will run before suite.As logs file is
   * generated every time when suite runs which will create lot of memory consumption after months.
   * So, this method is used to delete logs .html files which was not modified from more than 15
   * days.
   * </p>
   */

  @BeforeSuite(description = "Delete 15 days old logs files", enabled = true) // In logs folder
  public void deleteLogs() {
    File projectDirectory = new File(System.getProperty(Constants.USER_DIR));
    File logsDirectory = new File(
        projectDirectory + File.separator + FileDataReader.getPropertyValue(Constants.LOGS_PATH));
    // Giving the permissions to file
    File[] listOfFiles = projectDirectory.listFiles();
    for (int i = 0; i < listOfFiles.length; i++) {
      listOfFiles[i].setWritable(true);
      listOfFiles[i].setExecutable(true);
      listOfFiles[i].setReadable(true);
    }
    // printing the permissions associated with the file
    System.out.println(
        "Logs Folder Permissions - Executable: " + logsDirectory.canExecute() + " , Readable: "
            + logsDirectory.canRead() + ", Writable: " + logsDirectory.canWrite() + "");
    if (logsDirectory.canWrite()) {
      System.out.println(
          "Log file is created with filename - " + FileDataReader.getLastModifiedFile(logsDirectory)
              .getName() + "");
    } else {
      System.out.println(
          "Directory does not have permissions write on files. Please change the permission access!");
    }
    FileDataReader.deleteFiles(logsDirectory, 15);
  }

  /**
   * <p>
   * This method is used to send Test Reports in email after every suite to users It will send email
   * in both passed and failed test cases except test cases skipped
   * </p>
   */

  @AfterSuite(description = "Sending Mail", enabled = true)
  public void sendEMail() {
    sendMail(fileDataReader.getExcelData(Constants.SHEET_CREDENTIALS_PATH, "MailIds", 1, 1),
        fileDataReader.getExcelData(Constants.SHEET_CREDENTIALS_PATH, "MailIds", 1, 2));
  }

  public void sendMail(String to, String cc) {
    String subject = "Grofers Assignment Test Report";
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        SendMail.sendTestReportByGMail(
            fileDataReader.getExcelData(Constants.SHEET_CREDENTIALS_PATH, "GmailID", 1, 0),
            fileDataReader.getExcelData(Constants.SHEET_CREDENTIALS_PATH, "GmailID", 1, 1), to, cc,
            subject,
            new StringBuilder()
                .append("</br></br><body align=\"center\"><h2>Test results Details</h2>")
                .append(fileDataReader
                    .readFile(new File(System.getProperty(Constants.USER_DIR) + File.separator
                        + fileDataReader
                        .getPropertyValue(Constants.MailConstants.MAIL_REPORT_PATH))))
                .append("</body>").toString()

        );
      }
    });
  }

  @BeforeClass
  public void abs() {
    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
  }

  @Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
  public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature)
      throws Throwable {
    testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
  }

  /**
   * @return returns two dimensional array of {@link CucumberFeatureWrapper} objects.
   */
  @DataProvider
  public Object[][] scenarios() {
    return testNGCucumberRunner.provideScenarios();
  }

}
