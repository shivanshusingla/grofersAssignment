package common;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = {"pretty"}, features = {"src/main/resources/featurefiles"}, glue = {
    "steps"}, monochrome = true, strict = true)
public class CucumberRunner extends CucumberSuite {

}
