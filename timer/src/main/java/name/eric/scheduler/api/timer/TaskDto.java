package name.eric.scheduler.api.timer;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto extends TaskCreateDto {
    UUID id;
}
