package org.gagauz.shop.database.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADMIN")
public class Admin extends AbstractUser {

    private static final long serialVersionUID = 8606790114284987539L;

}
