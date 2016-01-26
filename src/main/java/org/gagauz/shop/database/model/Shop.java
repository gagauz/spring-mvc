package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;
import org.gagauz.shop.database.model.enums.Currency;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "SHOP")
public class Shop extends UpdatableModel {
    private static final long serialVersionUID = 5210385475445363712L;
    private String name;
    private String description;
    private String host;
    private Seller seller;
    private Currency defaultCurrency;
    private List<CurrencyRate> currencyRates;
    private String offer;

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

    @Column
    @Lob
    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

}
