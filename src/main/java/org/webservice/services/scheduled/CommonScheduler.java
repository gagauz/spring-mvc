package org.webservice.services.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webservice.database.dao.RepetitorDao;
import org.webservice.database.dao.RepetitorViewDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class CommonScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(CommonScheduler.class);

    @Autowired
    private RepetitorViewDao repetitorViewDao;

    @Autowired
    private RepetitorDao repetitorDao;

    private Date lastUpdateDateComment = new Date(0);
    private Date lastUpdateDateOrder = new Date(0);
    private Date lastUpdateDateView = new Date(0);
    private Date lastUpdateRating = new Date(0);

    private ReentrantLock repetitorLock = new ReentrantLock();

    // Updates every 5 min
    @Scheduled(initialDelay = 60000, fixedDelay = 300000)
    @Transactional
    public void updateCommentsCount() {
        try {
            if (repetitorLock.tryLock()) {
                try {
                    int count = repetitorDao
                            .createSQLQuery(
                                    "UPDATE `REPETITOR` _r_, "
                                            + "(SELECT _c_.repetitor_id AS id, "
                                            + "SUM(_c_.rating) AS ratingSum, "
                                            + "COUNT(_c_.id) AS ratingCount, "
                                            + "SUM(IF(_c_.visible,1,0)) AS commentCount, "
                                            + "MAX(_c_.updated) AS maxUpdated "
                                            + "FROM `COMMENT` _c_ WHERE _c_.checked=true GROUP BY _c_.repetitor_id) _i_ "
                                            + "SET _r_.rating=_i_.ratingSum/_i_.ratingCount, _r_.ratingCount=_i_.ratingCount, _r_.commentCount=_i_.commentCount "
                                            + "WHERE _i_.id=_r_.id and _i_.maxUpdated > :date")
                            .setTimestamp("date", lastUpdateDateComment).executeUpdate();

                    LOG.info("execute updateCommentsCount(): {}", count);
                    if (count > 0) {
                        lastUpdateDateComment = new Date();
                    }
                } finally {
                    repetitorLock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Updates every 5 min
    @Scheduled(initialDelay = 65000, fixedDelay = 300000)
    @Transactional
    public void updateOrdersCount() {
        try {
            if (repetitorLock.tryLock()) {
                try {

                    int count = repetitorDao
                            .createSQLQuery(
                                    "UPDATE `REPETITOR` _r_, "
                                            + "(SELECT _o_.owner_id AS id, SUM(_o_.id) AS orderCount, MAX(_o_.updated) AS maxUpdated "
                                            + "FROM `ORDERS` _o_ GROUP BY _o_.owner_id) _i_ SET _r_.orderCount=_i_.orderCount "
                                            + "WHERE _i_.id=_r_.id and _i_.maxUpdated > :date")
                            .setTimestamp("date", lastUpdateDateOrder).executeUpdate();

                    LOG.info("execute updateOrdersCount(): {}", count);
                    if (count > 0) {
                        lastUpdateDateOrder = new Date();
                    }
                } finally {
                    repetitorLock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Updates every 5 min
    @Scheduled(initialDelay = 70000, fixedDelay = 300000)
    @Transactional
    public void updateRepetitorViewsCount() {
        try {
            if (repetitorLock.tryLock()) {
                try {
                    int count = repetitorDao
                            .createSQLQuery(
                                    "UPDATE `REPETITOR` _r_, "
                                            + "(SELECT _v_.repetitorId AS id, COUNT(_v_.ip)as viewCount, MAX(_v_.created) AS maxCreated "
                                            + "FROM `REPETITOR_VIEW` _v_ GROUP BY _v_.repetitorId) _i_ SET _r_.viewCount=_i_.viewCount "
                                            + "WHERE _i_.id=_r_.id and _i_.maxCreated > :date")
                            .setTimestamp("date", lastUpdateDateView).executeUpdate();

                    LOG.info("execute updateOrederViewCount(): {}", count);
                    if (count > 0) {
                        lastUpdateDateView = new Date();
                    }
                } finally {
                    repetitorLock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO May be not optimal
    @SuppressWarnings("unchecked")
    @Scheduled(initialDelay = 60000, fixedDelay = 600000)
    @Transactional
    public void updateOveralRating() {
        try {
            if (repetitorLock.tryLock()) {
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -1);
                    Date month = cal.getTime();
                    System.out.println("Cleanup less " + month);
                    List<Integer> ids = repetitorDao
                            .createSQLQuery(
                                    "SELECT _r_.id FROM `REPETITOR` _r_ ORDER BY _r_.rating DESC, _r_.orderCount DESC, _r_.id ASC")
                            .list();
                    int i = 0;
                    int c = ids.size();
                    for (int id : ids) {
                        i++;
                        repetitorDao.createSQLQuery(
                                "UPDATE `REPETITOR` set overalRating=" + i + ", overalRatingCount="
                                        + c + " WHERE id=" + id).executeUpdate();
                    }
                    if (ids.size() > 0) {
                        lastUpdateRating = new Date();
                    }
                    System.out.println("execute cleanupRepetitorViews(): " + ids.size());
                } finally {
                    repetitorLock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Updates every day at 4:00
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void cleanupRepetitorViews() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date month = cal.getTime();
        System.out.println("Cleanup less " + month);
        int count = repetitorViewDao
                .createSQLQuery("DELETE FROM `REPETITOR_VIEW` WHERE created < :date")
                .setDate("date", month).executeUpdate();

        System.out.println("execute cleanupRepetitorViews(): " + count);
    }

}
