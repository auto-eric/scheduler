package name.eric.scheduler.timer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import name.eric.scheduler.api.logger.LogDto;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class LoggerService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${scheduler.logger.url}")
    private String uri = "";

    public void log(String message) {
        log.debug("send message: {}", message);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            LogDto value = new LogDto();
            value.setMessage(message);
            request.setEntity(new StringEntity(mapper.writeValueAsString(value)));
            client.execute(request);
        } catch (IOException e) {
            log.error("failed to call logger-service", e);
        }
    }
}
