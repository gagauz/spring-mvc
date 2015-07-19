package org.repetitor.test.scenarios;

import org.hibernate.SessionFactory;
import org.repetitor.utils.HibernateSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataInitializer extends HibernateSessionManager {

    private final SessionFactory sessionFactory;

    @Autowired
    public TestDataInitializer(DataBaseScenario[] scenarios, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        execute(scenarios);
    }

    public void execute(DataBaseScenario[] scenarios) {
        openSession();
        for (DataBaseScenario scenario : scenarios) {
            scenario.run();
        }
        closeSession();
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}