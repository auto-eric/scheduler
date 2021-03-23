package name.eric.scheduler.api.timer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api("CRUD endpoints for scheduling")
@RestController
public interface TaskApi {

    @ApiOperation(value = "get a certain scheduling",
            produces = APPLICATION_JSON_VALUE)
    @RequestMapping(value = "scheduler/task/{id}",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    TaskCreateDto get(@PathVariable("id") UUID id);

    @ApiOperation(value = "create a certain scheduling",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @RequestMapping(value = "scheduler/task",
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE)
    TaskCreateDto post(@RequestBody TaskCreateDto dto);
}
