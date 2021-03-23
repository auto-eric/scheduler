package name.eric.scheduler.api.logger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logger")
public interface LogController {

    @PostMapping
    void post(@RequestBody LogDto dto);
}
