package org.webservice.database.model;

import org.webservice.database.model.base.Model;

import javax.persistence.*;

@Entity
@Table(name = "REPETITOR_IMAGE")
public class RepetitorImage extends Model {
    private static final long serialVersionUID = 8316694852479518012L;
    private Repetitor repetitor;
    private String image;

    @Column(nullable = false)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Repetitor getRepetitor() {
        return repetitor;
    }

    public void setRepetitor(Repetitor repetitor) {
        this.repetitor = repetitor;
    }

}
