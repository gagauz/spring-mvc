package org.gagauz.shop.database.model;

import java.util.EnumSet;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.gagauz.shop.database.model.enums.AccessRole;

@Entity
@Table(name = "BUYER")
@AttributeOverride(name = "roles", column = @Column(updatable = false) )
public class Buyer extends AbstractUser {
    private static final long serialVersionUID = 2766400018046040033L;

    public Buyer() {
        super();
        setRoles(EnumSet.of(AccessRole.BUYER));
    }
}
