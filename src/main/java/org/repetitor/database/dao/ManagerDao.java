package org.repetitor.database.dao;

import org.repetitor.database.model.Manager;
import org.springframework.stereotype.Service;

@Service
public class ManagerDao extends AbstractDao<Integer, Manager> {

    public Manager findByUsername(String username) {
        return (Manager) createQuery("select m from Manager m where username=:username").setParameter("username", username).uniqueResult();
    }

}
