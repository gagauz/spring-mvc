package org.repetitor.database.dao;

import org.repetitor.database.model.Pupil;
import org.springframework.stereotype.Service;

@Service
public class PupilDao extends AbstractDao<Integer, Pupil> {

    public Pupil findByEmail(String email) {
        return (Pupil) createQuery("select p from Pupil p where p.email=:email").setString("email", email).uniqueResult();
    }

}
