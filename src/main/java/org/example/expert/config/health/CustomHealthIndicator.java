package org.example.expert.config.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private static final String SERVER_STATUS_KEY = "server";
    private static final String SERVER_RUNNING = "running";
    private static final String SERVER_STOPPED = "stopped";
    private static final String SERVER_PORT_KEY= "port";

    private final ApplicationContext applicationContext;

    @Override
    public Health health() {
        try {
            int port = ((ServletWebServerApplicationContext) applicationContext).getWebServer().getPort();
                return Health.up()
                        .withDetail(SERVER_STATUS_KEY, SERVER_RUNNING)
                        .withDetail(SERVER_PORT_KEY, "server " + port + " is open")
                        .build();
        } catch (Exception e) {
            log.error("Health Check failed", e);
            return Health.down()
                    .withDetail(SERVER_STATUS_KEY, SERVER_STOPPED)
                    .build();
        }
    }
}
