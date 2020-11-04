package steps;

import apiutils.ApiConfig;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class TestSteps {

  @Given("^User has the todo tasks$")
  public void getToDoApiResponse() {
    ApiConfig.getResponse("http://jsonplaceholder.typicode.com/todos", "", "");
  }

  @And("^User belongs to the city (.*)$")
  public void getUsersApiResponse(String cityName) {
    ApiConfig.getResponse("http://jsonplaceholder.typicode.com/todos", "", "");
  }

}
