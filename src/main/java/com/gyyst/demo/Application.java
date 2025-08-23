package com.gyyst.demo;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusMain
public class Application implements QuarkusApplication {
    @Override
    public int run(String... args) throws Exception {
        log.info("Running main method");
        Quarkus.waitForExit();
        return 0;
    }
}
