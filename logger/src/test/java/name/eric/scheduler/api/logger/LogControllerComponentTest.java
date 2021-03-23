package name.eric.scheduler.api.logger;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import name.eric.scheduler.api.LogDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogControllerComponentTest {

    @Value("http://localhost:${local.server.port}/logger")
    private String url;

    @Test
    public void testLogging() {
        LogDto message = new LogDto("simple test message");
        //@formatter:off
        given()
            .header(new Header("Content-Type", ContentType.JSON.toString()))
            .body(message)
            .log().all()
        .when()
            .post(url)
            .prettyPeek()
            .then()
            .statusCode(HttpStatus.OK.value());
        //@formatter:on
    }
}
