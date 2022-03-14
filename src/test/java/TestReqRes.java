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

    static final List<PutPostUser> usersUnderTest = from(new File(Path.RESOURCES_TEST + "fixtures.json")).getList("users", PutPostUser.class);

    @DataProvider
    public Iterator<Object[]> usersUnderTest() {
        List<Object[]> users = new ArrayList<>();
        usersUnderTest.forEach(user -> users.add(new Object[]{user}));
        return users.iterator();
    }

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

    @Test(description = "Tests that user is successfully created", dataProvider = "usersUnderTest")
    public void verifyUserIsCreated(PutPostUser user) {
        logger.info("Execute test: " + new Object(){}.getClass().getEnclosingMethod().getName());

        given()
            .contentType("application/json; charset=utf-8")
            .body(user)
        .when()
            .post(Endpoints.USERS)
        .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .body("name", equalTo(user.getName()));
    }

    @Test(description = "Tests that user is successfully updated")
    public void verifyUserIsUpdated() {
        logger.info("Execute test: " + new Object(){}.getClass().getEnclosingMethod().getName());

        PutPostUser updateUser = new PutPostUser();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        given()
            .contentType("application/json; charset=utf-8")
            .body(updateUser)
        .when()
            .put("/users/2")
        .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .body("job", equalTo(updateUser.getJob()));
    }

    @Test(description = "Tests that the Delete request yields a 204 response status")
    public void verifyDeleteUserResponseStatus() {
        logger.info("Execute test: " + new Object(){}.getClass().getEnclosingMethod().getName());

        given()
            .contentType("application/json; charset=utf-8")
        .when()
            .delete("/users/2")
        .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
