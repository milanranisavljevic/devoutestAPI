import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import config.BaseConfig;
import constants.Endpoints;
import constants.Path;
import constants.TestPriorities;
import model.PutPostUser;
import model.GetUser;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.describedAs;

public class TestReqRes extends BaseConfig {

    private static Logger logger = Logger.getLogger(TestReqRes.class);

    @BeforeClass
    public void setBaseUri() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Tests that GET /users  returns valid user", priority = TestPriorities.HIGH)
    public void verifyUserFirstNameAndId() {
        logger.info("Execute test: " + new Object(){}.getClass().getEnclosingMethod().getName());

        GetUser user =
                from(given()
                    .when()
                        .get("/users/2")
                    .then()
                        .log().all()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response().asString()
                ).getObject("data", GetUser.class);

//        assertThat(user.getFirstName(), equalTo("Janet"));

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(user.getFirstName(), "Janet", "user.first_name should be 'Janet'");
        soft.assertEquals(user.getId().toString(), "2", "user.id should be '2'");
        soft.assertAll();

    }

}
