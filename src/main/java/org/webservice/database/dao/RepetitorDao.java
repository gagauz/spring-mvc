package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Repetitor;

import java.util.List;

@Service
public class RepetitorDao extends AbstractDao<Integer, Repetitor> {

    public Repetitor findByUsername(String username) {
        return (Repetitor) createQuery("from Repetitor r where username=:username").setParameter(
                "username", username).uniqueResult();
    }

    public Repetitor findByEmail(String email) {
        return (Repetitor) createQuery("from Repetitor r where email=:email").setParameter("email",
                email).uniqueResult();
    }

    public long countAll() {
        return (Long) createQuery("select count(*) from Repetitor r").uniqueResult();
    }

    public List<Repetitor> findAllWithData(HqlEntityFilter filter) {
        String sql = "select distinct r from Repetitor r "
                + "inner join fetch r.subjects rs "
                + "inner join fetch rs.id.subject sb "
                //                + "inner join fetch sb.sections sc "
                //                + "inner join fetch sb.extras ex "
                + "left join fetch r.placeRepetitorRegion rg";
        return findByFilter(sql, filter);
    }

    public Repetitor findByIdWithData(Integer integer) {
        return (Repetitor) createQuery(
                "select r from Repetitor r "
                        + "join fetch r.subjects rs "
                        + "left join fetch r.placeRepetitorRegion rg "
                        + "join fetch rs.id.subject sb "
                        + "left join fetch sb.sections sc "
                        + "left join fetch sb.extras ex "
                        + "where r.id=:id").setInteger("id",
                integer).uniqueResult();
    }

}
