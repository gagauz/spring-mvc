package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.SubjectExtra;

import java.util.List;

@Service
public class SubjectExtraDao extends AbstractDao<Integer, SubjectExtra> {
    public List<SubjectExtra> findAllFetched() {
        return createQuery("select ss from SubjectExtra ss " +
                "join fetch ss.subject s").list();
    }
}
