package org.webservice.test.setup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "org.webservice.test.scenarios" })
public class TestSetup {
}
