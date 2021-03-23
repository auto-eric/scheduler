package name.eric.scheduler.timer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.eric.scheduler.api.timer.TaskCreateDto;
import name.eric.scheduler.timer.converter.TaskConverter;
import name.eric.scheduler.timer.document.Status;
import name.eric.scheduler.timer.document.Task;
import name.eric.scheduler.timer.persistence.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskConverter converter;

    public TaskCreateDto get(UUID id) {
        Optional<Task> byId = repository.findById(id);
        TaskCreateDto dto = new TaskCreateDto();
        BeanUtils.copyProperties(byId, dto);
        return dto;
    }

    public TaskCreateDto create(TaskCreateDto createDto) {
        Task s = new Task();
        s.setId(UUID.randomUUID());
        s.setMessage(createDto.getMessage());
        s.setStartAt(createDto.getTime());
        s.setStatus(Status.TODO);

        Task saved = repository.save(s);
        return converter.convert(saved);
    }
}
