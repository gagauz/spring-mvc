package org.webservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BUYER")
public class Buyer extends AbstractUser {
    private static final long serialVersionUID = 2766400018046040033L;

}
