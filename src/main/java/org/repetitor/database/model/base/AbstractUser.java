package org.repetitor.database.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.repetitor.utils.CryptoUtils;

@MappedSuperclass
public abstract class AbstractUser extends UpdatableModel implements Serializable {

    private String username;
    private String passwordHash;

    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 128, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    @Transient
    public String getPassword() {
        return passwordHash;
    }

    @Transient
    public void setPassword(String password) {
        this.passwordHash = CryptoUtils.createSHA512String(password);
    }
}
