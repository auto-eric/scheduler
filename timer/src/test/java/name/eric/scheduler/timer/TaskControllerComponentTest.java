package name.eric.scheduler.timer;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import name.eric.scheduler.api.timer.TaskCreateDto;
import name.eric.scheduler.timer.document.Task;
import name.eric.scheduler.timer.persistence.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerComponentTest {

    private static final UUID TASK_ID = UUID.randomUUID();

    @Value("http://localhost:${local.server.port}/scheduler/task")
    private String url;

    @Autowired
    private TaskRepository repo;

    @BeforeEach
    public void setup() {
        repo.deleteAll();

        Task task = new Task();
        task.setId(TASK_ID);
        task.setMessage("test task");
        task.setStartAt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        repo.save(task);
    }

    @Test
    public void testGet() {

        //@formatter:off
        given()
            .header(new Header("Content-Type", ContentType.JSON.toString()))
            .log().all()
        .when()
            .get(url + "/" + TASK_ID)
            .prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value());
        //@formatter:on
    }

    @Test
    public void testPost() {
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setTime("13:00");
        taskCreateDto.setMessage("test creation");

        //@formatter:off
        given()
            .header(new Header("Content-Type", ContentType.JSON.toString()))
            .body(taskCreateDto)
            .log().all()
        .when()
            .post(url )
            .prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", notNullValue())
            .body("time", equalTo(taskCreateDto.getTime()))
            .body("message", equalTo(taskCreateDto.getMessage()))
        ;
        //@formatter:on

        Assertions.assertEquals(2, repo.count());
    }

    @Test
    public void testPost_validation() {
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setTime("29:00");

        //@formatter:off
        given()
            .header(new Header("Content-Type", ContentType.JSON.toString()))
            .body(taskCreateDto)
            .log().all()
        .when()
            .post(url )
            .prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.size()",equalTo(2));
        //@formatter:on

        Assertions.assertEquals(1, repo.count());
    }
}
