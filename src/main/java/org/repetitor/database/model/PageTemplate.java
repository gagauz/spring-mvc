package org.repetitor.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.repetitor.database.model.base.UpdatableModel;

@Entity
@Table(name = "PAGE_TEMPLATE")
public class PageTemplate extends UpdatableModel {
    private static final long serialVersionUID = 4921762296473317435L;
    private String name;
    private String template;

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 10000)
    @Lob
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

}
