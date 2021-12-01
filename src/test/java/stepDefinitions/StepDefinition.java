package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data = new TestDataBuild();


    @Given("Add Place Payload {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {

       res= given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
    }
    @When("user calls {string} with {string} request")
    public void user_calls_with_post_request(String resource, String method) throws IOException {
        // Write code here that turns the phrase above into concrete actions

        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("POST")) {
            //response = res.when().post(getGlobalValue("postEndpoint"))
            response = res.when().post(resourceAPI.getResource());
        }
        else if (method.equalsIgnoreCase("GET")){
            response = res.when().get(resourceAPI.getResource());
        }
    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(response.getStatusCode(), 200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        // Write code here that turns the phrase above into concrete actions
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(keyValue).toString(), expectedValue);
    }


}
