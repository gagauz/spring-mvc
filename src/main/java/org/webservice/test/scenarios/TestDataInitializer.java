package org.webservice.test.scenarios;

import org.webservice.database.dao.TransactionWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestDataInitializer {

    @Autowired
    private DataBaseScenario[] scenarios;

    @Autowired
    private TransactionWrapper transactionWrapper;

    @PostConstruct
    public void init() {
        transactionWrapper.wrap(new Runnable() {
            @Override
            public void run() {
                execute();
            }
        });
    }

    public void execute() {
        for (DataBaseScenario scenario : scenarios) {
            scenario.run();
        }
    }

}
