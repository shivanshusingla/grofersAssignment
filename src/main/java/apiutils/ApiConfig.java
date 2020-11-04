package apiutils;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ApiConfig {

  public static String getResponse(String apiUrl, String appendQuery, String key) {
    System.out.println("Getting response of GET api :- '" + apiUrl + "?" + appendQuery + "'");
    Response response = given().get(apiUrl).then().statusCode(200).extract().response();
    String value = response.path(key).toString(); // eg. key - "results.id[0]"
    System.out.println("Value of Key - '" + key + "' :- '" + value + "'");
    return value;
  }

}
