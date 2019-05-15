package ca.freedommobile.api.framework.test;

import ca.freedommobile.api.framework.config.Config;
import ca.freedommobile.api.framework.domain.request.User;
import ca.freedommobile.api.framework.reports.HTMLReport;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import gherkin.deps.com.google.gson.JsonObject;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Map;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
public class AuthTest {

    RestAssured restAssured;
    Response post;
    private ExtentTest test = HTMLReport.getInstance().getReports().createTest("api-test");

    @Test
    public void setUp(){
        System.out.println(Config.getProperty("host"));
        System.out.println("PATH" + Config.getProperty("app.auth"));
    }




    @Given("^a user exists with an username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void aUserExistsWithAnUsernameAndPassword(String user, String pass) throws Throwable {
        restAssured.baseURI = Config.getProperty("host");
        System.out.println(restAssured.baseURI);
        System.out.println("PATH " + Config.getProperty("app.auth"));
        System.out.println("User is " + user);
        System.out.println("Password is " + user);
        User user1 = new User();
        user1.setUsername(user);
        user1.setPassword(pass);
        RequestSpecification specification = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON).build();
        post = restAssured.given(specification)
                .body(user1)
                .then()
                .post(Config.getProperty("app.auth"));
    }

    @When("^a user authenticate by username and password$")
    public void aUserAuthenticateByUsernameAndPassword() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            post.then().assertThat().statusCode(200);
            test.log(Status.PASS,"Status Code 200 is matched");
        }catch (AssertionError ex){
            test.log(Status.FAIL,"Status Code 200 NOT matched");
            throw ex;
        }

    }

    @And("^response includes the following$")
    public void responseIncludesTheFollowing(Map<String,String> responseFields) throws Throwable {

        try {
            Assert.assertTrue( post.then().extract().response().body().prettyPrint().contains("id_token"));
            test.log(Status.PASS,"ID_TOKEN Was present in the body");
        }catch (AssertionError ex){
            test.log(Status.FAIL, "ID_TOKEN Was missing");
            throw ex;
        }
    }

    @Then("^the status code is (\\d+)$")
    public void theStatusCodeIs(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        post.then().assertThat().statusCode(200);
    }

    @After
    public void flush(){
        System.out.println("FLUSING THE REPORT");
        HTMLReport.getInstance().getReports().flush();
    }
}
