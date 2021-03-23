package name.eric.scheduler.timer.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.eric.scheduler.timer.document.Status;
import name.eric.scheduler.timer.document.Task;
import name.eric.scheduler.timer.persistence.TaskRepository;
import name.eric.scheduler.timer.service.LoggerService;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final MongoTemplate mongoTemplate;
    private final TaskRepository repository;
    private final LoggerService loggerService;

    @Scheduled(cron = "0 * * * * ?")
    public void runTasks() {
        log.info("run scheduler");
        String startAtCriteria = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Collection<Task> tasks = repository.findByStartAtAndStatus(startAtCriteria, Status.TODO);

        tasks.stream()
                .map(this::getAvailableTask)
                .filter(task -> task.isPresent())
                .map(Optional::get)
                .forEach(this::process);
    }

    private void process(Task t) {
        log.info("found : {}", t);
        loggerService.log(t.getMessage());
        t.setStatus(Status.TODO);
        t.setProcessedAt(LocalTime.now());
        repository.save(t);
    }

    private Optional<Task> getAvailableTask(Task task) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(task.getId()));
        query.addCriteria(Criteria.where("task.status").is(Status.TODO));

        Update update = new Update();
        update.set("task.status", Status.RUNNING);
        Task tasks = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions(), Task.class);
        return Optional.ofNullable(task);
    }

}
