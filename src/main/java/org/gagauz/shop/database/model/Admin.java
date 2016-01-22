package org.gagauz.shop.database.model;

import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.gagauz.shop.database.model.enums.AccessRole;

@Entity
@Table(name = "ADMIN")
public class Admin extends AbstractUser {

    private static final long serialVersionUID = 8606790114284987539L;

    public Admin() {
        super();
        setRoles(EnumSet.of(AccessRole.ADMIN));
    }
}
