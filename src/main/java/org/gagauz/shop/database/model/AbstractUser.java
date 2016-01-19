package org.gagauz.shop.database.model;

import org.gagauz.hibernate.types.CollectionType;
import org.gagauz.hibernate.types.EnumSetType;
import org.gagauz.shop.database.model.base.UpdatableModel;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.database.model.enums.Gender;
import org.gagauz.shop.utils.CryptoUtils;
import org.gagauz.tapestry.security.api.User;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

import java.util.EnumSet;

@SuppressWarnings("serial")
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "enumSetOf.AccessRole", typeClass = EnumSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "org.gagauz.shop.database.model.enums.AccessRole")
        })})
public class AbstractUser extends UpdatableModel implements User {

    private String firstName;

    private String lastName;

    private Gender gender;

    private String email;

    private String phone;

    private String password;

    private boolean enabled;

    private EnumSet<AccessRole> roles = EnumSet.noneOf(AccessRole.class);

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Type(type = "enumSetOf.AccessRole")
    @Column(nullable = false)
    public EnumSet<AccessRole> getRoles() {
        return roles;
    }

    public void setRoles(EnumSet<AccessRole> roles) {
        this.roles = roles;
    }

    @Transient
    public void createPassword(String password) {
        this.password = CryptoUtils.createSHA512String(password);
    }

    @Transient
    public boolean checkPassword(String password) {
        return this.password.equals(CryptoUtils.createSHA512String(password));
    }

    @Transient
    public boolean hasRole(AccessRole[] roles) {
        if (null != roles) {
            for (AccessRole role : roles) {
                if (getRoles().contains(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}
