package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Pupil;

@Service
public class PupilDao extends AbstractDao<Integer, Pupil> {

    public Pupil findByEmail(String email) {
        return (Pupil) createQuery("select p from Pupil p where p.email=:email").setString("email", email).uniqueResult();
    }

}
