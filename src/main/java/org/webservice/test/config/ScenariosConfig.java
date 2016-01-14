package org.webservice.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.webservice.test.scenarios.ScenarioShop;
import org.webservice.test.scenarios.TestDataInitializer;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {ScenarioShop.class})
public class ScenariosConfig {

    @Bean
    public TestDataInitializer testDataInitializer() {
        return new TestDataInitializer();
    }

}
