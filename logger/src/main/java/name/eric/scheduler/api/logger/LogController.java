package name.eric.scheduler.api.logger;

import lombok.extern.slf4j.Slf4j;
import name.eric.scheduler.api.LogDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("logger")
public class LogController {

    @PostMapping
    public void post(@RequestBody LogDto dto) {
      log.info(dto.getMessage());
    }
}
