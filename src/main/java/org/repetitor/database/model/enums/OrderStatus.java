package org.repetitor.database.model.enums;

import org.gagauz.utils.C;
import org.gagauz.utils.Filter;

import java.util.List;

public enum OrderStatus {

    OPEN(1, "Открыта", 2, 4, 9),
    CONFIRMED_PUPIL(2, "Подтверждена с клиентом", 3, 4, 5),
    RECOMMENDED(3, "В «Подходящих заявках»", 2, 4, 5),
    ACCEPTED(4, "Принята в «Заявках учеников»", 2, 3, 5),
    CONTACT_SENT(5, "Контактные данные переданы преподавателю", 6),
    ARRANGED(6, "Cозвонились и договорились о занятиях", 7),
    SUBJECTED_TO_PAY(7, "Заказ подлежит оплате", 8, 9),
    PAYED(8, "Оплачено"),
    CLOSED(9, "Закрыта");

    private int id;
    private String description;
    private int[] allowedNext;

    OrderStatus(int id, String description, int... allowedNext) {
        this.id = id;
        this.description = description;
        this.allowedNext = allowedNext;
    }

    public List<OrderStatus> getNextStatuses() {
        List<OrderStatus> result = C.filter(values(), new Filter<OrderStatus>() {
            @Override
            public boolean apply(OrderStatus arg0) {
                for (int aid : allowedNext) {
                    if (arg0.id == aid) {
                        return true;
                    }
                }
                return false;
            }
        });
        result.add(0, this);
        return result;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int[] getAllowedNext() {
        return allowedNext;
    }

    public boolean is(OrderStatus... statuses) {
        for (OrderStatus status : statuses) {
            if (status == this) {
                return true;
            }
        }
        return false;
    }

}
