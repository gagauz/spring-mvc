package org.gagauz.shop.database.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gagauz.hibernate.model.Model;
import org.gagauz.shop.database.model.enums.Currency;

@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate extends Model {
    private Shop shop;
    private Currency currency;
    private BigDecimal rate = BigDecimal.ONE;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

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
