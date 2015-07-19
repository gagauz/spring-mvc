package org.repetitor.test.scenarios;

import org.repetitor.database.dao.ManagerDao;
import org.repetitor.database.model.Manager;
import org.repetitor.database.model.enums.AccessRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScenarioManager extends DataBaseScenario {
    @Autowired
    private ManagerDao userDao;

    @Override
    @Transactional
    protected void execute() {
        for (int i = 0; i < 10; i++) {
            Manager u = new Manager();
            u.setEmail(i + "@admin.ru");
            u.setPassword("111");
            u.setUsername(u.getEmail());
            u.getRoles().add(AccessRole.ADMIN);
            userDao.save(u);
        }
    }
}
