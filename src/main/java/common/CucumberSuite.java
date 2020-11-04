package common;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CucumberSuite {

  TestNGCucumberRunner testNGCucumberRunner;

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
