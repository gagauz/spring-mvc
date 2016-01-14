package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.Gender;
import org.webservice.utils.CryptoUtils;

import javax.persistence.*;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractUser extends UpdatableModel {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String phone;
    private String password;
    private boolean enabled;

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

    @Transient
    public void createPassword(String password) {
        this.password = CryptoUtils.createSHA512String(password);
    }

}
