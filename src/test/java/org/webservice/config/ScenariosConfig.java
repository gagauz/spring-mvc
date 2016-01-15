package org.webservice.config;

import org.webservice.scenarios.ScenarioShop;
import org.webservice.scenarios.TestDataInitializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {ScenarioShop.class})
public class ScenariosConfig {

    @Bean
    public TestDataInitializer testDataInitializer() {
        return new TestDataInitializer();
    }

}
