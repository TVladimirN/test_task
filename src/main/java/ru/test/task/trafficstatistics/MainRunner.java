package ru.test.task.trafficstatistics;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@Transactional
public class MainRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(MainRunner.class)
                .web(WebApplicationType.SERVLET)
                .build()
                .run(args);
        context.registerShutdownHook();
    }
}
