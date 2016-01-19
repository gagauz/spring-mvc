package org.gagauz.shop.database.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SELLER")
@AttributeOverride(name = "roles", column = @Column(updatable = false) )
public class Seller extends AbstractUser {
    private static final long serialVersionUID = 6567488918740580151L;

}
