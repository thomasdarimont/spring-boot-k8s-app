package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Slf4j
@RestController
class IndexController {

    private final String hostName;

    public IndexController() throws UnknownHostException {
        this.hostName = InetAddress.getLocalHost().getHostName();
    }

    @GetMapping("/")
    public String index() {

        String request = String.format("Request received on '%s' %s", hostName, Instant.now());
        log.info(request);

        String response = String.format("Response from instance '%s' %s%n", hostName, Instant.now());
        return response;
    }
}
