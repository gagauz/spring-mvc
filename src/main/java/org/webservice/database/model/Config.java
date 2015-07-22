package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.ConfigType;
import org.webservice.services.ConfigConstant;

import javax.persistence.*;

@Entity
@Table(name = "CONFIG1")
public class Config extends UpdatableModel {
    private static final long serialVersionUID = -8315218801427759237L;
    private ConfigType type;
    private ConfigConstant name;
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    public ConfigConstant getName() {
        return name;
    }

    public void setName(ConfigConstant name) {
        this.name = name;
        this.type = null != name ? name.type() : null;
    }

    @Column(name = "value1", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
