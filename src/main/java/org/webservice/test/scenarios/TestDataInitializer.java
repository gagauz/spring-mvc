package org.webservice.test.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DependsOn("sessionFactory")
public class TestDataInitializer /* extends HibernateSessionManager */{

	@Autowired
	private DataBaseScenario[] scenarios;

	@Transactional
	public void execute() {
		// openSession();
		for (DataBaseScenario scenario : scenarios) {
			scenario.run();
		}
		// closeSession();
	}

	// @Override
	// protected SessionFactory getSessionFactory() {
	// return sessionFactory;
	// }
}
