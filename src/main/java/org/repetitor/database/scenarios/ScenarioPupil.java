package org.repetitor.database.scenarios;

import java.util.List;

import org.repetitor.database.dao.PupilDao;
import org.repetitor.database.model.Pupil;
import org.repetitor.database.model.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.gagauz.utils.C;

@Service
public class ScenarioPupil extends ScenarioRepetitor {

    @Autowired
    private PupilDao pupilDao;

    @Override
    @Transactional
    protected void execute() {
        List<Pupil> ccs = C.newArrayList();
        for (int i = 0; i < 500; i++) {
            Pupil u = new Pupil();
            u.setName(getRandomName(rand.nextBoolean() ? Gender.MALE : Gender.FEMALE) + " " + getRandomSurname());
            u.setEmail(i + "@mail.ru");
            u.setPhone("89025454412");
            ccs.add(u);
        }
        pupilDao.save(ccs);
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] {};
    }
}
