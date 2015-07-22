package org.webservice.database.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.webservice.database.model.Region;
import org.webservice.database.model.RepetitorSubject;
import org.webservice.database.model.Subject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

@Service
public class RepetitorSubjectDao extends AbstractDao<Integer, RepetitorSubject> {

    public Long[] getAverages(Region region, Subject subject) {
        String sql = "SELECT " +
                "BIT_OR(rs.extras), " +
                "BIT_OR(rs.sections), " +
                "BIT_OR(rs.pupilCategories), " +
                "BIT_OR(r.degree), " +
                "MIN(r.hourPrice), " +
                "MAX(r.hourPrice), " +
                "BIT_OR(r.placePupil), " +
                "BIT_OR(r.placeRepetitor), " +
                "BIT_OR(r.placeOnline), " +
                "MIN(r.birthdate), " +
                "MAX(r.birthdate), " +
                "MIN(r.stageSince), " +
                "MAX(r.stageSince) " +
                "FROM REPETITOR_SUBJECT rs " +
                "INNER JOIN REPETITOR r ON r.id=rs.repetitor_id " +
                "WHERE r.city_id=:region";
        if (null != subject) {
            sql += " AND rs.subject_id=:subject";
        }

        Query query = createSQLQuery(sql).setInteger("region", region.getId());
        if (null != subject) {
            query.setInteger("subject", subject.getId());
        }

        Object[] values = (Object[]) query.uniqueResult();

        return new Long[] {
                ((BigInteger) values[0]).longValue(),
                ((BigInteger) values[1]).longValue(),
                ((BigInteger) values[2]).longValue(),
                ((BigInteger) values[3]).longValue(),
                ((BigDecimal) values[4]).longValue(),
                ((BigDecimal) values[5]).longValue(),
                ((BigInteger) values[6]).longValue(),
                ((BigInteger) values[7]).longValue(),
                ((BigInteger) values[8]).longValue(),
                ((Date) values[9]).getTime(),
                ((Date) values[10]).getTime(),
                ((Date) values[11]).getTime(),
                ((Date) values[12]).getTime()
        };
    }
}
