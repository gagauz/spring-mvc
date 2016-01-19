package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address extends UpdatableModel {
    private static final long serialVersionUID = -419233328100775772L;
    private Buyer buyer;
    private AddressData addressData;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Embedded
    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

}
