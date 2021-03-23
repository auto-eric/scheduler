package name.eric.scheduler.timer;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.eric.scheduler.api.timer.TaskCreateDto;
import name.eric.scheduler.timer.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("scheduler/task")
public class TaskController {

    private final TaskService service;

    @ApiOperation(value = "get a certain timer", httpMethod = "GET", produces = APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    public TaskCreateDto get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @ApiOperation(value = "create a new timer", httpMethod = "POST", produces = APPLICATION_JSON_VALUE)
    @PostMapping
    public TaskCreateDto post(@Valid @RequestBody TaskCreateDto dto) {
        log.info("call post with DTO=[{}]", dto);
        return service.create(dto);
    }
}
