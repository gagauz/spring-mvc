package org.repetitor.test.scenarios;

import java.util.List;

import org.gagauz.utils.C;
import org.gagauz.utils.multimap.Multimap;
import org.repetitor.database.dao.CommentDao;
import org.repetitor.database.dao.PupilDao;
import org.repetitor.database.dao.RepetitorDao;
import org.repetitor.database.model.Comment;
import org.repetitor.database.model.Pupil;
import org.repetitor.database.model.Region;
import org.repetitor.database.model.Repetitor;
import org.repetitor.database.model.Subject;
import org.repetitor.database.model.SubjectExtra;
import org.repetitor.database.model.SubjectSection;
import org.repetitor.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScenarioComment extends ScenarioRepetitor {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RepetitorDao repetitorDao;

    @Autowired
    private PupilDao pupilDao;

    @Autowired
    private ScenarioRepetitor scenarioRepetitor;

    @Autowired
    private ScenarioPupil scenarioPupil;

    @Override
    @Transactional
    protected void execute() {
        List<Comment> ccs = C.newArrayList();
        for (Repetitor r : repetitorDao.findAll()) {
            if (rand.nextBoolean()) {
                for (Pupil p : getRandomPupils(rand.nextInt(10) + 1)) {
                    Comment u = new Comment();
                    u.setAuthor(p);
                    u.setRepetitor(r);
                    u.setChecked(rand.nextBoolean());
                    u.setVisible(u.isChecked() ? rand.nextBoolean() : false);

                    u.setRating(rand.nextInt(4) + 2);
                    u.setText("За время нашего сотрудничества "
                            + r.getName()
                            + " "
                            + r.getPatronymic()
                            + " проявил себя как внимательный и требовательный педагог, радеющий за результат своей работы, ребенок хорошо ее воспринимает.");

                    ccs.add(u);
                }
            }
        }
        commentDao.save(ccs);
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] { scenarioRepetitor, scenarioPupil };
    }

    private List<Pupil> pupils;
    private Multimap<Subject, SubjectExtra> subjectsExtras;
    private Multimap<Subject, SubjectSection> subjectsSections;
    private List<Region> regions;

    private List<Pupil> getRandomPupils(int k) {
        if (null == pupils) {
            pupils = pupilDao.findAll();
        }
        return RandomUtils.getRandomSubList(pupils, RandomUtils.nextInt(k) + 1);
    }
}
