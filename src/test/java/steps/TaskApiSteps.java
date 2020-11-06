package steps;

import apiutils.ApiConfig;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class TaskApiSteps {

  @Given("^User has the todo tasks$")
  public void getToDoApiResponse() {
    ApiConfig.getResponse("http://jsonplaceholder.typicode.com/todos", "", "");
  }

  @And("^User belongs to the city (.*)$")
  public void getUsersApiResponse(String cityName) {
    ApiConfig.getResponse("http://jsonplaceholder.typicode.com/users", "", cityName);
  }

  @Then("^User Completed task percentage should be greater than (.*)%$")
  public void verifyUserTaskPercentage(String taskPercentage) {
    ApiConfig.getResponse("http://jsonplaceholder.typicode.com/todos", "", "completed");
  }

}
