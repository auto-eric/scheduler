package name.eric.scheduler.timer.converter;

import name.eric.scheduler.api.timer.TaskDto;
import name.eric.scheduler.timer.document.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskDto convert(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTime(task.getStartAt());
        taskDto.setMessage(task.getMessage());
        return taskDto;
    }
}
