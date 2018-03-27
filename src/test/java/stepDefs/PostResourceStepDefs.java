package stepDefs;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PostResourceStepDefs {

    private Response response;
    private RequestSpecification request;
    private String ENDPOINT = "https://jsonplaceholder.typicode.com";

    @Before
    public void setUp() {
        RestAssured.baseURI = ENDPOINT;
        request = RestAssured.given();
    }

    @When("^User send GET to \"([^\"]*)\"$")
    public void userSendGETTo(String req) {
        response = request.get(req);
        //System.out.println("response: " + response.prettyPrint());
    }

    @When("^User send PUT to \"([^\"]*)\"$")
    public void userSendPUTTo(String req) {
        response = request.put(req);
    }

    @When("^User send PATCH to \"([^\"]*)\"$")
    public void userSendPATCHTo(String req) {
        response = request.patch(req);
    }

    @When("^User send DELETE to \"([^\"]*)\"$")
    public void userSendDELETETo(String req) {
        response = request.delete(req);
    }

    @Then("^Status code is (\\d+)$")
    public void statusCodeIs(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("^Response Mime Type is \"([^\"]*)\"$")
    public void responseMimeTypeIs(String mimeType) {
        response.then().contentType(mimeType);
    }

    @Then("^The body response contains:$")
    public void theBodyResponseContains(DataTable table) {
        List<List<String>> data = table.raw();

        for (int i = 1; i < data.size(); i++) {
            response.then().assertThat().body(data.get(i).get(0), equalTo(data.get(i).get(1)));
        }
    }

    @Then("^Request body matches \"([^\"]*)\" Schema \"([^\"]*)\"$")
    public void requestBodyMatchesSchema(String format, String schema) {
        if (format.equals("JSON")) {
            response.then().assertThat().contentType(ContentType.JSON).and()
                    .body(matchesJsonSchemaInClasspath(schema));
        }
    }
}