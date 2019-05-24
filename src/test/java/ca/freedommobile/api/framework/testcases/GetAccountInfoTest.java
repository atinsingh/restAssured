package ca.freedommobile.api.framework.testcases;

import ca.freedommobile.api.framework.config.Config;
import ca.freedommobile.api.framework.reports.HTMLReport;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import groovy.json.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @project api-framework
 * Created By Rsingh on 2019-05-13.
 */

//@RunWith(Cucumber.class)
public class GetAccountInfoTest {
    private ExtentTest extent;
    Response response;

    Logger logger = LogManager.getLogger(GetAccountInfoTest.class);

    @Before
    public void before(Scenario scenario) {
        logger.info("Running Scenario  - {} ", scenario.getName());
        extent = HTMLReport.getInstance().getReports().createTest(scenario.getName());
    }

    @When("user submit the Get request msisdnEncrypted \"([^\"]*)\" as path param$")
    public void user_submit_the_Get_request_msisdnEncrypted_as_path_param(String contactId) {
        contactId = StringEscapeUtils.unescapeJava(contactId);
        baseURI = Config.getProperty("host");
        response = given().pathParam("contactId", contactId)
                .get(Config.getProperty("api.accountinfo-contactid"));
        extent.log(Status.INFO, "API called with contact ID  " + contactId);
    }

    @Then("status code  should be {int} in the response")
    public void status_code_should_be_in_the_response(Integer statusCode) {
        logger.info("Status code to be matched from Scenario-  {}", statusCode);
        //System.out.println(response.body().print());
        int code = response.then().extract().statusCode();
        if (statusCode == code) {
            extent.log(Status.PASS, String.format("Response Status code is %d - it matches with the desired code %d", code, statusCode));
        } else {
            extent.log(Status.FAIL, String.format("Response Status code is %d -it does't Matches with desired code %d", code, statusCode));
        }
        response.then().assertThat().statusCode(statusCode);
    }

    /**
     * Scenario 1 Implementation-GET Retrieves the account info with invalid id.
     */
    @Given("user enter the invalid contactId with wrong api path.")
    public void user_enter_the_invalid_contactId_with_wrong_api_path() {
        baseURI = Config.getProperty("host");
    }

    @And("response code should be {string} in response body")
    public void responseCodeShouldBeInResponseBody(String errCode) {
        JsonPath path = response.then().extract().jsonPath();
        String code = null;
        if (response.then().extract().statusCode() == 500) {
            code = path.get("Messages[0].code");
        } else {
            code = path.get("messages[0].code");
        }
        if (code.equals(errCode)) {
            extent.log(Status.PASS, String.format("Code in response messages- %s  - it matches with desired Code - %s", code, errCode));
        } else {
            extent.log(Status.FAIL, String.format("Code in response messages - %s  -it doesn't matches with desired Code - %s", code, errCode));
        }

        if (response.then().extract().statusCode() == 500) {
            response.then().assertThat().body("Messages[0].code", equalTo(errCode));
        } else {
            response.then().assertThat().body("messages[0].code", equalTo(errCode));
        }
    }


    @And("response type should be {string} in response body")
    public void response_type_should_be_in_response_body(String errType) {
        JsonPath path = response.then().extract().jsonPath();

        String type = null;
        if (response.then().extract().statusCode() == 500) {
            type = path.get("Messages[0].Type");
        } else {
            type = path.get("messages[0].type");
        }

        if (type.equals(errType)) {
            extent.log(Status.PASS, String.format("Type in response - %s  - it matches with desired Type - %s", type, errType));
        } else {
            extent.log(Status.FAIL, String.format("Type in response - %s  -it doesn't matches with desired Type - %s", type, errType));
        }
        if (response.then().extract().statusCode() == 500) {
            response.then().assertThat().body("Messages[0].Type", equalTo(errType));
        } else {
            response.then().assertThat().body("messages[0].type", equalTo(errType));
        }
    }

    @And("Description of response should be {string}")
    public void description_of_response_should_be(String desc) {
        JsonPath path = response.then().extract().jsonPath();
        String description = null;
        if (response.then().extract().statusCode() == 500) {
            description = path.get("Messages[0].Description");
        } else {
            description = path.get("messages[0].description");
        }

        if (description.equals(desc)) {
            extent.log(Status.PASS, String.format("Description in reposnse - %s  - it matches with desired Description - %s", description, desc));
        } else {
            extent.log(Status.FAIL, String.format("Description in reposnse - %s  it doesn't matches with desired Description - %s", description, desc));
        }
        if (response.then().extract().statusCode() == 500) {
            response.then().assertThat().body("Messages[0].Description", equalTo(desc));
        } else {
            response.then().assertThat().body("messages[0].description", equalTo(desc));
        }

    }

    /**
     * Scenario 2 Implementation Get-AccountInfo with ContactID - 200
     */

    @Given("user enter the contact ID.")
    public void user_enter_the_contact_ID() {
        baseURI = Config.getProperty("host");
    }

    @And("response body includes the serviceItemArray")
    public void responseBodyIncludesTheServiceItemArray() {
        JsonPath path = response.then().extract().body().jsonPath();
        Object serviceItems = path.get("serviceItems");
        if (serviceItems == null) {
            extent.log(Status.FAIL, "Service Items Array is missing in the response");
        } else {
            extent.log(Status.PASS, "Service Items array is present in the response");
        }
        response.then().assertThat().body("$", hasKey("serviceItems"));
            }

    @And("response serviceItemArray should include {string}")
    public void responseServiceItemArrayShouldInclude(String serviceItems) {
        String[] items = StringUtils.split(",");
        JsonPath json = response.then().extract().body().jsonPath();
        if (items.length == 1) {
            if (json.get("serviceItems." + serviceItems) == null) {
                extent.log(Status.FAIL, "Service item - " + serviceItems + " - is missing in the response");
            } else {
                extent.log(Status.PASS, "Service item  " + "'" + serviceItems + "'"+ " is present in response.");
            }
            response.then().body("serviceItems." +  serviceItems, equalTo(serviceItems));
        }

        if (items.length == 2) {
            if (json.get("serviceItems.item[0]" + items[0]) == null ||
                    json.get("serviceItems." + items[1]) == null) {
                extent.log(Status.FAIL, String.format("Item ' %s ' and Item ' %s ' missing in the response body", items[0], items[1]));
            } else {
                extent.log(Status.PASS, String.format("Item ' %s ' and Item ' %s ' is in the response body", items[0], items[1]));

            }
            response.then().body("serviceItems." + items[0], equalTo(items[0]));
            response.then().body("serviceItems." + items[1], equalTo(items[1]));
        }
        if (items.length == 3) {
            if (json.get("serviceItems." + items[0]) == null ||
                    json.get("serviceItems." + items[1]) == null ||
                    json.get("serviceItems." + items[2]) == null
            ) {
                extent.log(Status.FAIL, String.format("Item %s and Item %s, Item %s missing in the response body", items[0], items[1], items[2]));
            } else {
                extent.log(Status.PASS, String.format("Item %s and Item %s , Item %s is in the response body", items[0], items[1], items[2]));

            }
            response.then().body("serviceItems." + items[0], equalTo(items[0]));
            response.then().body("serviceItems." + items[1], equalTo(items[1]));
            response.then().body("serviceItems." + items[2], equalTo(items[2]));
        }
    }

    @And("response serviceItem should include userType {string}")
    public void responseServiceItemShouldIncludeUserType(String userType) {
        JsonPath path = response.then().extract().body().jsonPath();
        if (path.get("userType") == null) {
            extent.log(Status.FAIL, "User type is missing in the response");
        } else {
            if (path.get("userType").equals(userType)) {
                extent.log(Status.PASS, String.format("UserType ' %s ' is present the body", userType));
                response.then().assertThat().body("userType", equalTo(userType));
            } else {
                extent.log(Status.FAIL, String.format("User type is not matched found %s , expected %s", path.get("userType"), userType));
            }
        }

    }

    //Erase previous data on the report and create a new report
    @After
    public void flush() {

        HTMLReport.getInstance().getReports().flush();
    }

}