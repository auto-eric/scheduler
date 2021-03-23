package name.eric.scheduler.timer.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document()
public class Task {

    @Id
    private UUID id;
    private String startAt;
    private String message;

    private LocalTime processedAt;
    private Status status;
}
