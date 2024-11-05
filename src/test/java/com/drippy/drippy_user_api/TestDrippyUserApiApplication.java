package com.drippy.drippy_user_api;

import org.springframework.boot.SpringApplication;

public class TestDrippyUserApiApplication {
    public static void main(String[] args) {
        SpringApplication.from(DrippyUserApiApplication::main).with(TestContainersConfiguration.class).run(args);
    }
}
