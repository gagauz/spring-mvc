package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.enums.Currency;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate extends ShopEntity {
    private static final long serialVersionUID = 1609479882191183801L;
    private Currency currency;
    private BigDecimal rate = BigDecimal.ONE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column(nullable = false, scale = 10)
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
