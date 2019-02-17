package ru.test.task.trafficstatistics.config;

import org.hsqldb.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Server server() {
        Server hsqldb = new Server();
        hsqldb.setSilent(true);
        hsqldb.setDatabasePath(0, "mem:testdb");
        hsqldb.setDaemon(true);
        hsqldb.setNoSystemExit(true);
        hsqldb.setPort(9001);
        hsqldb.setDatabaseName(0, "testdb");
        hsqldb.setAddress("localhost");
        hsqldb.start();
        return hsqldb;
    }
}
