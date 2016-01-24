package org.gagauz.shop.database.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gagauz.shop.database.model.base.UpdatableModel;
import org.gagauz.shop.database.model.enums.Currency;

@Entity
@Table(name = "SHOP")
public class Shop extends UpdatableModel {
    private String name;
    private String description;
    private String host;
    private Seller seller;
    private Currency defaultCurrency;
    private List<CurrencyRate> currencyRates;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Column(nullable = false)
    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(List<CurrencyRate> currencyRates) {
        this.currencyRates = currencyRates;
    }

}
