package org.repetitor.database.model;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.repetitor.database.model.base.AbstractUser;
import org.repetitor.database.model.enums.AccessRole;
import org.repetitor.database.model.types.CollectionType;
import org.repetitor.database.model.types.EnumSetType;

@Entity
@Table(name = "MANAGER")
@TypeDefs({
        @TypeDef(name = "enumSetOf.AccessRole", typeClass = EnumSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "org.repetitor.database.model.enums.AccessRole")
        }) })
public class Manager extends AbstractUser {

    private static final long serialVersionUID = 8606790114284987539L;

    private String email;
    private String phone;
    private EnumSet<AccessRole> roles = EnumSet.noneOf(AccessRole.class);

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(nullable = false)
    @Type(type = "enumSetOf.AccessRole")
    public EnumSet<AccessRole> getRoles() {
        return roles;
    }

    public void setRoles(EnumSet<AccessRole> roles) {
        this.roles = roles;
    }

}
