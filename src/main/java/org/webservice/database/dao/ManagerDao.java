package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Manager;

@Service
public class ManagerDao extends AbstractDao<Integer, Manager> {

    public Manager findByUsername(String username) {
        return (Manager) createQuery("select m from Manager m where username=:username").setParameter("username", username).uniqueResult();
    }

}
