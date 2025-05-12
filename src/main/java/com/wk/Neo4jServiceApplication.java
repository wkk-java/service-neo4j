package com.wk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableNeo4jRepositories
@SpringBootApplication
@EnableAsync
public class Neo4jServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Neo4jServiceApplication.class);
    }

    public static void main(String[] args)  {
        SpringApplication.run(Neo4jServiceApplication.class, args);
        log.info("启动完毕");
    }

}