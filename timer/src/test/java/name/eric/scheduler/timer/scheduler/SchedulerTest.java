package name.eric.scheduler.timer.scheduler;

import name.eric.scheduler.timer.document.Status;
import name.eric.scheduler.timer.document.Task;
import name.eric.scheduler.timer.persistence.TaskRepository;
import name.eric.scheduler.timer.service.LoggerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerTest {

    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private TaskRepository repository;
    @Mock
    private LoggerService loggerService;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    public void run() {

        when(repository.findByStartAtAndStatus(any(String.class), eq(Status.TODO)))
                .thenReturn(List.of(prepareTask()));
        when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any(Class.class)))
                .thenReturn(prepareTask());

        // when
        scheduler.runTasks();

        // then
        verify(loggerService, times(1)).log(any(String.class));
    }

    private Task prepareTask() {
        return new Task(UUID.randomUUID(), "12:30", "test run", LocalTime.now(), Status.TODO);
    }
}