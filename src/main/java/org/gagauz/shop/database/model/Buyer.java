package org.gagauz.shop.database.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BUYER")
@AttributeOverride(name = "roles", column = @Column(updatable = false) )
public class Buyer extends AbstractUser {
    private static final long serialVersionUID = 2766400018046040033L;
}
