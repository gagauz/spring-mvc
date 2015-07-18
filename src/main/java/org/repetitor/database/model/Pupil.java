package org.repetitor.database.model;

import org.repetitor.database.model.base.UpdatableModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "PUPIL")
public class Pupil extends UpdatableModel implements Serializable {
    private static final long serialVersionUID = 8921190777920529853L;
    private String name;
    private String email;
    private String phone;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
