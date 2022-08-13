package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: 吴成伟
 * @date: 2022/8/7 17:42
 * @Description: TODO
 */
@SpringBootApplication
public class TSpringBoot {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TSpringBoot.class, args);
    }
}
