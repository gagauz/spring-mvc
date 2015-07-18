package org.repetitor.database.scenarios;

import org.gagauz.utils.C;
import org.repetitor.database.dao.ManagerDao;
import org.repetitor.database.dao.PaymentDao;
import org.repetitor.database.dao.RepetitorDao;
import org.repetitor.database.model.Manager;
import org.repetitor.database.model.Payment;
import org.repetitor.database.model.Repetitor;
import org.repetitor.database.model.enums.PaymentStatus;
import org.repetitor.setup.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScenarioPayment extends ScenarioRepetitor {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private RepetitorDao repetitorDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private ScenarioRepetitor scenarioRepetitor;

    @Autowired
    private ScenarioManager scenarioManager;

    @Override
    @Transactional
    protected void execute() {
        List<Payment> ccs = C.newArrayList();
        for (Repetitor r : repetitorDao.findAll()) {
            if (rand.nextBoolean()) {
                for (int i = 0; i < rand.nextInt(10) + 3; i++) {
                    Payment u = new Payment();
                    u.setRepetitor(r);
                    u.setManager(getRandomManager());
                    int a = (i == 0 ? (rand.nextInt(100) + 1) * 50 : (rand.nextInt(10) + 1) * -50);
                    u.setAmount(new BigDecimal(a));
                    u.setSubject(i == 0 ? "Пополнение счета" : "Оплата по заявке №ХХХХХ");
                    u.setStatus(i % 2 == 0 ? PaymentStatus.PENDING : PaymentStatus.ACCEPTED);
                    ccs.add(u);
                }
            }
        }
        paymentDao.save(ccs);
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] {scenarioRepetitor, scenarioManager};
    }

    private List<Manager> pupils;

    private Manager getRandomManager() {
        if (null == pupils) {
            pupils = managerDao.findAll();
        }
        return RandomUtils.getRandomObject(pupils);
    }
}
