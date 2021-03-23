package name.eric.scheduler.timer.persistence;

import name.eric.scheduler.timer.document.Status;
import name.eric.scheduler.timer.document.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface TaskRepository extends MongoRepository<Task, UUID> {

    Collection<Task> findByStartAtAndStatus(String startAtCriteria, Status status);
}
