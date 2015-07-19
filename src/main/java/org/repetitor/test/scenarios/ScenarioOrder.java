package org.repetitor.test.scenarios;

import org.repetitor.database.dao.*;
import org.repetitor.database.model.*;
import org.repetitor.database.model.enums.*;
import org.repetitor.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

@Service
public class ScenarioOrder extends ScenarioRepetitor {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PupilDao pupilDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private RepetitorDao repetitorDao;

    @Autowired
    private ScenarioSubject scenarioSubject;

    @Autowired
    private ScenarioPupil scenarioPupil;

    @Autowired
    private ScenarioRepetitor scenarioRepetitor;

    @Autowired
    private ScenarioRegion scenarioRegion;

    @Override
    @Transactional
    protected void execute() {
        int i = 0;
        A: for (Repetitor r : repetitorDao.findAll()) {
            for (int k = 0; k < 5; k++) {
                Order o = new Order();
                o.setPupil(getRandomPupil());
                o.setPupilName(getRandomName(rand.nextBoolean() ? Gender.MALE : Gender.FEMALE) + " " + getRandomSurname());
                Subject sub = getRandomSubject();
                o.setSubject(sub);
                o.setSubjectExtras(RandomUtils.getRandomSubList(sub.getExtras(), 4));
                o.setSubjectSections(RandomUtils.getRandomSubList(sub.getSections(), 4));
                o.setPlaceOnline(rand.nextBoolean());
                o.setPlacePupil(rand.nextBoolean());
                if (o.isPlacePupil()) {
                    o.setPlacePupilRegion(getRandomRegion());
                }
                o.setPlaceRepetitor(rand.nextBoolean());

                int p1 = 50 * rand.nextInt(50);
                int p2 = p1 + 50 * rand.nextInt(50);

                if (rand.nextBoolean()) {
                    o.setPriceMin(new BigDecimal(p1));
                }
                o.setPriceMax(new BigDecimal(p2));
                o.setRatio(1.0f + rand.nextInt(30) / 10.0f);

                o.setPupilCategory(RandomUtils.getRandomObject(PupilCategory.values()));
                o.setRepetitorAgeMin(rand.nextInt(30) + 20);
                o.setRepetitorAgeMax(o.getRepetitorAgeMin() + rand.nextInt(20));
                o.setRepetitorStageMin(rand.nextInt(5) + 2);
                o.setRepetitorStageMax(o.getRepetitorStageMin() + rand.nextInt(10));
                if (rand.nextBoolean()) {
                    o.setRepetitorGender(rand.nextBoolean() ? Gender.FEMALE : Gender.MALE);
                }
                o.setTotalLessonCount(TotalLessonCount.TOTAL_FROM_10);
                o.setWeekLessonCount(WeekLessonCount.WEEK_FROM_2_TO_3);
                //                o.setTotalLessonMin(rand.nextInt(3));
                //                o.setTotalLessonMax(o.getTotalLessonMin() + rand.nextInt(20));
                //                o.setWeekLessonMin(rand.nextInt(2) + 1);
                //                o.setWeekLessonMax(o.getWeekLessonMin() + rand.nextInt(2));

                if (rand.nextBoolean()) {
                    o.setRepetitorDegree(EnumSet.copyOf(RandomUtils.getRandomSubList(RepetitorDegree.values(), 3)));
                }

                orderDao.saveNoCommit(o);
                if (i < 3 || rand.nextBoolean()) {
                    o.setOwner(r);
                    o.setFinalPrice(new BigDecimal(rand.nextBoolean() ? p1 : p2));
                    o.setCommission(o.getFinalPrice().multiply(new BigDecimal(o.getRatio())));
                    o.changeStatus(rand.nextBoolean() ? OrderStatus.CONTACT_SENT : OrderStatus.CLOSED);
                } else {
                    o.setWantedRepetitors(getRandomRepetitors());
                }
            }
            if (++i > 10)
                break A;
        }
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] {scenarioSubject, scenarioPupil, scenarioRepetitor, scenarioRegion};
    }

    private List<Pupil> pupils;

    private Pupil getRandomPupil() {
        if (null == pupils) {
            pupils = pupilDao.findAll();
        }
        return RandomUtils.getRandomObject(pupils);
    }

    private List<Repetitor> repetitors;

    private Repetitor getRandomRepetitor() {
        if (null == repetitors) {
            repetitors = repetitorDao.findAll();
        }
        return RandomUtils.getRandomObject(repetitors);
    }

    private List<Repetitor> getRandomRepetitors() {
        if (null == repetitors) {
            repetitors = repetitorDao.findAll();
        }
        return RandomUtils.getRandomSubList(repetitors, 3);
    }

    private List<Subject> subjects;

    private Subject getRandomSubject() {
        if (null == subjects) {
            subjects = subjectDao.findFetchedAll();
        }
        return RandomUtils.getRandomObject(subjects);
    }

    private List<Region> regions;

    private Region getRandomRegion() {
        if (null == regions) {
            regions = regionDao.findByType(RegionType.SUBWAY);
        }
        return RandomUtils.getRandomObject(regions);
    }
}
