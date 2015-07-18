package org.repetitor.database.dao;

import org.repetitor.database.model.SubjectExtra;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectExtraDao extends AbstractDao<Integer, SubjectExtra> {
    public List<SubjectExtra> findAllFetched() {
        return createQuery("select ss from SubjectExtra ss " +
                "join fetch ss.subject s").list();
    }
}
