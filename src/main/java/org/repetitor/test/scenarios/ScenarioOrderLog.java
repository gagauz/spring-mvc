package org.repetitor.test.scenarios;

import org.gagauz.utils.C;
import org.repetitor.database.dao.OrderDao;
import org.repetitor.database.dao.OrderLogDao;
import org.repetitor.database.model.Order;
import org.repetitor.database.model.OrderLog;
import org.repetitor.database.model.enums.OrderLogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScenarioOrderLog extends ScenarioRepetitor {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderLogDao orderLogDao;

    @Autowired
    private ScenarioOrder scenarioOrder;

    @Override
    @Transactional
    protected void execute() {
        List<OrderLog> logs = C.newArrayList();
        int l = 0;
        for (Order p : orderDao.findAssigned()) {
            for (int i = 0; i < 10; i++) {
                OrderLog o = new OrderLog();
                o.setOrder(p);
                o.setVisible(rand.nextBoolean());
                o.setType(rand.nextBoolean() ? OrderLogType.REPETITOR : OrderLogType.MANAGER);
                if (rand.nextBoolean()) {
                    o.setMessage("Созвонились, но не договорились о дате первого занятия. Дата следующего созвона - 15.10.2014 Комментарии - 'Для уточнения информации.' (репетитор)");
                } else if (rand.nextBoolean()) {
                    o.setMessage("Договорились о первом занятии (или ознакомительной встрече) на 21.10.2014 Комментарии - '' (репетитор)");
                } else if (rand.nextBoolean()) {
                    o.setMessage("Договорились о первом занятии (или ознакомительной встрече) на 21.10.2014 Комментарии - '' (репетитор)");
                } else if (rand.nextBoolean()) {
                    o.setMessage("Первое занятие перенесено на 29.10.2014 Комментарии репетитора - '' (репетитор)");
                } else if (rand.nextBoolean()) {
                    o.setMessage("Первое занятие отменено. Комментарии - 'Звонок репетитору:' (администратор)");
                } else if (rand.nextBoolean()) {
                    o.setMessage("Проведено первое занятие с клиентом 21.10.2014 Стоимость первого занятия - 600. Комментарии - 'Звонок клиенту:' (администратор)");
                } else {
                    o.setMessage("Дальнейших занятий не будет. Комментарии - 'Звонок клиенту: было 2 занятия : стоимость 600 + 900 =1500. Больше не будет.' (администратор)");
                }
                logs.add(o);
            }
            if (l++ > 10) {
                break;
            }
        }
        orderLogDao.save(logs);
    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] {scenarioOrder};
    }

}
