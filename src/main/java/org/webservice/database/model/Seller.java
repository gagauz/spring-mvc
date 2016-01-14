package org.webservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SELLER")
public class Seller extends AbstractUser {
    private static final long serialVersionUID = 6567488918740580151L;

}
