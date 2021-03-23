package name.eric.scheduler.timer.persistence;

import name.eric.scheduler.timer.document.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    public void testDocumentSave() {
        UUID id = UUID.randomUUID();

        Task newTask = new Task();
        newTask.setId(id);
        newTask.setMessage("component test");
        newTask.setStartAt("13:04");
        Task saved = repository.save(newTask);

        assertEquals(newTask.getId(), saved.getId());
        assertEquals(newTask.getMessage(), saved.getMessage());
        assertEquals(newTask.getStartAt(), saved.getStartAt());

        saved = repository.findById(id).get();
        assertEquals(newTask.getId(), saved.getId());
        assertEquals(newTask.getMessage(), saved.getMessage());
        assertEquals(newTask.getStartAt(), saved.getStartAt());
    }
}
