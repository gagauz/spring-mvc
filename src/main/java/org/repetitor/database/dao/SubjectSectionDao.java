package org.repetitor.database.dao;

import org.repetitor.database.model.Subject;
import org.repetitor.database.model.SubjectSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectSectionDao extends AbstractDao<Integer, SubjectSection> {

    public List<SubjectSection> findAllFetched() {
        return createQuery("select ss from SubjectSection ss " +
                "join fetch ss.subject s").list();
    }

    public List<SubjectSection> findAllFetched(Subject subject) {
        return createQuery("select ss from SubjectSection ss " +
                "join fetch ss.subject s where ss.subject=:subject").setEntity("subject", subject).list();
    }
}
