package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Subject;

import java.util.List;

@Service
public class SubjectDao extends AbstractDao<Integer, Subject> {
    public List<Subject> findParentsOnly() {
        return createQuery("select s from Subject s where s.container=false").list();
    }

    public List<Subject> findAllFetched() {
        return createQuery("select distinct s from Subject s left join fetch s.children where s.parent is null").list();
    }

    public List<Subject> findFetchedAll() {
        return createQuery("select distinct s from Subject s left join fetch s.extras ex left join fetch s.sections sc where s.container=false").list();
    }
}
