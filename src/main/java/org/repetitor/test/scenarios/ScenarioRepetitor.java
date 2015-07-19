package org.repetitor.test.scenarios;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gagauz.utils.C;
import org.gagauz.utils.Filter;
import org.repetitor.database.dao.RegionDao;
import org.repetitor.database.dao.RepetitorDao;
import org.repetitor.database.dao.SubjectDao;
import org.repetitor.database.dao.SubjectExtraDao;
import org.repetitor.database.dao.SubjectSectionDao;
import org.repetitor.database.model.Region;
import org.repetitor.database.model.Repetitor;
import org.repetitor.database.model.RepetitorSubject;
import org.repetitor.database.model.Subject;
import org.repetitor.database.model.SubjectExtra;
import org.repetitor.database.model.SubjectSection;
import org.repetitor.database.model.enums.Gender;
import org.repetitor.database.model.enums.PupilCategory;
import org.repetitor.database.model.enums.RegionType;
import org.repetitor.database.model.enums.RepetitorDegree;
import org.repetitor.utils.CollectionUtils;
import org.repetitor.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScenarioRepetitor extends DataBaseScenario {
    private static final String[][] NAMES = new String[][] {
            { "Исаак", "Авраам", "Аарон", "Иоаким", "Наум", "Иосиф", "Бен", "Моисей", "Исаия",
                    "Ной", "Натаниэль", "Самуил", "Иоганн" },
            { "Жанна", "Лия", "Мария", "Мирра", "Наоми", "Суламифь", "Ребекка", "Ноа", "Сара",
                    "Магдалина", "Динора", "Анна", "Яна" } };

    private static final String[][] PATRONIMIC = new String[][] {
            { "Исаакович", "Абрамович", "Ааронович", "Иоакимович", "Наумович", "Иосифович",
                    "Бенович", "Моисеевич", "Исаиевич", "Ноиевич", "Натаниэлевич", "Самуилович",
                    "Иоганнович" },
            { "Исааковна", "Абрамовна", "Аароновна", "Иоакимовна", "Наумовна", "Иосифовна",
                    "Беновна", "Моисеевна", "Исаиевна", "Ноиевна", "Натаниэлевна", "Самуиловна",
                    "Иоганновна" } };

    private static final String[] SURNAMES = new String[] { "Бромберг", "Вольфензон", "Лангман",
            "Кларсфельд", "Рамон", "Познер", "Ойербах", "Перельман", "Фельдман", "Гольдман",
            "Герштейн", "Гофман", "Рабинович" };

    @Autowired
    private RepetitorDao repetitorDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SubjectSectionDao subjectSectionDao;

    @Autowired
    private SubjectExtraDao subjectExtraDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private ScenarioRegion scenarioRegion;

    @Autowired
    private ScenarioSubject scenarioSubject;

    @Override
    @Transactional
    protected void execute() {
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 20; i++) {
            Repetitor u = new Repetitor();
            // ------------- CONTACT ------------------
            u.setUsername(i + "@mail.ru");
            if (RandomUtils.nextBool()) {
                u.setGender(Gender.FEMALE);
            } else {
                u.setGender(Gender.MALE);
            }
            u.setName(getRandomName(u.getGender()));
            u.setSurname(getRandomSurname());
            u.setPatronymic(getRandomPatr(u.getGender()));
            u.setEmail(i + "@mail.ru");
            u.setPhone1("+79032787746");
            cal.set(Calendar.YEAR, RandomUtils.nextInt(10) + 1990);
            u.setStageSince(cal.getTime());
            u.setDegree(RandomUtils.getRandomObject(RepetitorDegree.values()));
            cal.set(Calendar.YEAR, RandomUtils.nextInt(10) + 1970);
            u.setBirthdate(cal.getTime());
            u.setPassword("111");
            if (rand.nextBoolean()) {
                u.setImage((rand.nextInt(54) + 1) + ".jpg");
            }

            u.setHourPrice(new BigDecimal(RandomUtils.nextInt(40) * 50));
            if (RandomUtils.nextBool()) {
                u.setPlaceOnline(true);
                u.setPlaceOnlineInfo("По скайпу, хенгауту");
            }

            Region region = getRandomRegion();

            if (RandomUtils.nextBool()) {
                u.setPlacePupil(true);
                u.setPlacePupilInfo("не более " + rand.nextInt(60) + " минут от м. "
                        + region.getName());
            }
            u.setPlaceRepetitor(true);
            u.setPlaceRepetitorRegion(region);
            u.setPlaceRepetitorInfo("Улица Неизвестная, 20 мин от метро прямо, потом 100 шагов налево");

            for (Subject s : getRandomSubjects(2)) {
                RepetitorSubject rs = new RepetitorSubject(u, s);
                rs.setInfo("Комментарий репетитора к предмету");
                rs.setExtras(getRandomExtras(s));
                rs.setSections(getRandomSections(s));
                // ***********************
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.PRESCHOOL_1_3);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.PRESCHOOL_4_5);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.PRESCHOOL_6_7);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.ADULT);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.STUDENT);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.SCHOOL_1);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.SCHOOL_2);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.SCHOOL_3);
                }
                if (RandomUtils.nextBool()) {
                    rs.getPupilCategories().add(PupilCategory.SCHOOL_4);
                }

                u.getSubjects().add(rs);
            }
            u.setEducation("Московский государственный университет имени М.В. Ломоносова");
            u.setCreated(getRandomDate());
            u.setLastLogin(getRandomDate());
            repetitorDao.save(u);
        }
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] { scenarioSubject, scenarioRegion };
    }

    private List<Subject> subjects;
    private List<SubjectExtra> subjectsExtras;
    private List<SubjectSection> subjectsSections;
    private List<Region> regions;

    private List<Subject> getRandomSubjects(int k) {
        if (null == subjects) {
            subjects = subjectDao.findAll();
        }
        return RandomUtils.getRandomSubList(subjects, RandomUtils.nextInt(k) + 1);
    }

    private Region getRandomRegion() {
        if (null == regions) {
            regions = C.filter(regionDao.findAll(), new Filter<Region>() {
                @Override
                public boolean apply(Region element) {
                    return element.getType() == RegionType.SUBWAY;
                }
            });
        }
        return RandomUtils.getRandomObject(regions);
    }

    private long getRandomSections(Subject s) {
        if (null == subjectsSections) {
            subjectsSections = subjectSectionDao.findAll();
        }
        return CollectionUtils.getMask(RandomUtils.getRandomSubList(subjectsSections, 5));
    }

    private long getRandomExtras(Subject s) {
        if (null == subjectsExtras) {
            subjectsExtras = subjectExtraDao.findAll();
        }
        return CollectionUtils.getMask(RandomUtils.getRandomSubList(subjectsExtras, 5));
    }

    protected Date getRandomDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -rand.nextInt(30));
        cal.add(Calendar.DAY_OF_YEAR, -rand.nextInt(10));
        return cal.getTime();
    }

    protected String getRandomName(Gender gender) {
        return RandomUtils.getRandomObject(NAMES[gender == Gender.MALE ? 0 : 1]);
    }

    protected String getRandomPatr(Gender gender) {
        return RandomUtils.getRandomObject(PATRONIMIC[gender == Gender.MALE ? 0 : 1]);
    }

    protected String getRandomSurname() {
        return RandomUtils.getRandomObject(SURNAMES);
    }
}
