package org.repetitor.test.setup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "org.repetitor.test.scenarios" })
public class TestSetup {
}
