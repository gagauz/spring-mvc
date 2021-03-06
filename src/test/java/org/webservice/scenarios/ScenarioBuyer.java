package org.webservice.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webservice.database.dao.BuyerDao;
import org.webservice.database.model.Buyer;
import org.webservice.database.model.enums.Gender;

@Service
public class ScenarioBuyer extends DataBaseScenario {

    @Autowired
    private BuyerDao buyerDao;

    @Override
    protected void execute() {
        Buyer buyer = new Buyer();
        buyer.setFirstName("BuyerFirstname");
        buyer.setLastName("BuyerLastname");
        buyer.setEnabled(true);
        buyer.setGender(Gender.MALE);
        buyer.setPhone("89023453434");
        buyer.setEmail("2@mail.ru");
        buyer.createPassword("111");
        buyerDao.save(buyer);
    }
}
