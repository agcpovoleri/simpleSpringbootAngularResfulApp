
package com.example.forumapp.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.forumapp.repository")
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.forumapp.entity"})
public class RepositoryConfig {

}