package org.gagauz.shop.database.model;

import java.util.EnumSet;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.gagauz.shop.database.model.enums.AccessRole;

@Entity
@Table(name = "SELLER")
@AttributeOverride(name = "roles", column = @Column(updatable = false) )
public class Seller extends AbstractUser {
    private static final long serialVersionUID = 6567488918740580151L;

    public Seller() {
        super();
        setRoles(EnumSet.of(AccessRole.SELLER));
    }

}
