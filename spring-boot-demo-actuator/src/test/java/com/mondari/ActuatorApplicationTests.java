package com.mondari;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class ActuatorApplicationTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgtPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToController() {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hello", Object.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() {
        ResponseEntity<Object> endpointsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor", Object.class);
        ResponseEntity<Object> infoReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/info", Object.class);
        ResponseEntity<Object> healthReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/health", Object.class);
        ResponseEntity<Object> diskSpaceHealthReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/health/diskSpace", Object.class);
        ResponseEntity<Object> beansReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/beans", Object.class);
        ResponseEntity<Object> mappingsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/mappings", Object.class);
        ResponseEntity<Object> conditionsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/conditions", Object.class);
        ResponseEntity<Object> envReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/env", Object.class);
        ResponseEntity<Object> configpropsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/configprops", Object.class);
        ResponseEntity<Object> cachesReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/caches", Object.class);
        ResponseEntity<Object> scheduledTasksReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/scheduledtasks", Object.class);
        ResponseEntity<Object> loggersReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/loggers", Object.class);
        ResponseEntity<Object> rootLoggersReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/loggers/ROOT", Object.class);
        ResponseEntity<Object> metricsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/metrics", Object.class);
        ResponseEntity<Object> jvmTreadsStatesMetricsReq = this.testRestTemplate.getForEntity("http://localhost:" + this.mgtPort + "/monitor/metrics/jvm.threads.states", Object.class);
        Assertions.assertEquals(HttpStatus.OK, endpointsReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, infoReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, healthReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, diskSpaceHealthReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, beansReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, mappingsReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, conditionsReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, envReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, configpropsReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, cachesReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, scheduledTasksReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, loggersReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, rootLoggersReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, metricsReq.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, jvmTreadsStatesMetricsReq.getStatusCode());
    }

}
