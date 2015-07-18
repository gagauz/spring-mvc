package org.repetitor.database.scenarios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestDataInitializer {

    @Autowired
    private DataBaseScenario[] scenarios;

    @Transactional
    public void execute() {
        for (DataBaseScenario scenario : scenarios) {
            scenario.run();
        }
    }
}
