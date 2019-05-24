package ca.freedommobile.api.framework.testcases;

import ca.freedommobile.api.framework.config.Config;
import ca.freedommobile.api.framework.reports.HTMLReport;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import groovy.json.StringEscapeUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * @project api-framework
 * Created By Rsingh on 2019-05-15.
 */

//@RunWith(Cucumber.class)
//@CucumberOptions(FeaturesFiles = "classpath:FeaturesFiles")
public class GetPlanFeaturesAddonsTest {
    RestAssured restAssured;
    Response response;
    private ExtentTest extent;
    Logger logger = LogManager.getLogger(GetPlanFeaturesAddonsTest.class);

    @Before
    public void before(Scenario scenario) {
        //System.out.println(scenario.getName());
        logger.log(Level.INFO, "Running Scenario {}", scenario.getName());
        extent = HTMLReport.getInstance().getReports().createTest(scenario.getName());

    }

    @Given("user enter the MSISDN.")
    public void userEnterTheMSISDN() {
        restAssured.baseURI = Config.getProperty("host");

    }

    @When("user submit the Get request contactID \"([^\"]*)\" as path param$")
    public void user_submit_the_Get_request_contactID_as_path_param(String msisdnEncrypted) {
        logger.log(Level.INFO, "Calling API {}",Config.getProperty("api.plan-addons") );
        msisdnEncrypted = StringEscapeUtils.unescapeJava(msisdnEncrypted);
        response = restAssured.given().pathParam("msisdnEncrypted", msisdnEncrypted)
                .get(Config.getProperty("api.plan-addons"));

        extent.log(Status.INFO, "API called with msisdnEncrypted ID  " + msisdnEncrypted);
    }

    @Then("status code  should be {int} in response")
    public void statusCodeShouldBeStatus_codeInResponse(Integer statusCode) {
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

    @And("response body addOnsArray size is {int}")
    public void responseBodyAddOnsArraySizeIsSize(Integer addonssize) {
        int addon = response.jsonPath().getList("addOns").size();
        // System.out.println(addon);

        if (addonssize == addon) {
            extent.log(Status.PASS, String.format("Response Status size is %d - it matches with the desired size %d", addon, addonssize));
        } else {
            extent.log(Status.FAIL, String.format("Response Status size is %d -it does't Matches with desired size %d", addon, addonssize));
        }
        response.then().assertThat().body("addOns.size()", is(6));

    }

    @And("response addOnsArray should include sku {string}")
    public void responseAddOnsArrayShouldIncludeSku(String sku) {
        JsonPath path = response.then().extract().jsonPath();

        String skuone = path.get("addons[0].sku");
        if (skuone.equals(sku)) {
            extent.log(Status.PASS, String.format("sku in response - %s  - it matches with desired sku - %s", skuone, sku));
        } else {
            extent.log(Status.FAIL, String.format("sku in response - %s  - it does't matches with desired sku - %s", skuone, sku));
        }
        response.then().assertThat().body("addOns[0].sku", contains("Unlimited LD calling and text to USA"));
    }

    @And("response addOnsArray should include {int}")
    public void responseAddOnsArrayShouldIncludePrice(Integer price) {
        JsonPath path = response.then().extract().jsonPath();

        int priceone = path.get("addons[0].price");
        if (priceone == price) {
            extent.log(Status.PASS, String.format("price in response - %s  - it matches with desired price- %s", priceone, price));
        } else {
            extent.log(Status.FAIL, String.format("price in response - %s  - it does't matches with desired sku - %s", priceone, price));
        }
        //response.then().assertThat().body("addOns[0].price", contains("Unlimited LD calling and text to USA"));

    }

    @And("response addOnsArray should isEnabled {string}")
    public void responseAddOnsArrayShouldIsEnabled(String isenabled) {
        JsonPath path = response.then().extract().jsonPath();

       // String isenabledon = null;
        String isEnabledonen = path.get("addons[0].isEnabled");
        if (isEnabledonen.equals(isenabled)) {

            extent.log(Status.PASS, String.format("isenabled in response - %s  - it matches with desired isenabled - %s", isEnabledonen, isenabled));
        } else {
            extent.log(Status.FAIL, String.format("isenabled in response - %s  - it does't matches with desired isenabled - %s", isEnabledonen, isenabled));
        }
       // response.then().assertThat().body("addons[0].isEnabled", contains("Unlimited LD calling and text to USA"));

    }

    @And("response addOnsArray should isRemoveable {string}")
    public void responseAddOnsArrayShouldIsRemoveable(String isremoveable) {
        JsonPath path = response.then().extract().jsonPath();

        String isremoveableone = null;
        isremoveableone = path.get("addons[0].isRemoveable");
        if (isremoveableone.equals(isremoveable)) {

            extent.log(Status.PASS, String.format("isremoveable item in response - %s  - it matches with desired isremoveable item - %s", isremoveableone, isremoveable));
        } else {
            extent.log(Status.FAIL, String.format("isremoveable item in response - %s  - it does't matches with desired isremoveable item - %s", isremoveableone, isremoveable));
        }
      //  response.then().assertThat().body("addOns.sku[0]", contains("Unlimited LD calling and text to USA"));

    }

    @When("User calls account info with  {string} as path param")
    public void userCallsAccountInfoWithAsPathParam(String arg0) {
    }
}


