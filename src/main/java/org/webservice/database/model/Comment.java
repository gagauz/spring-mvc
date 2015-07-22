package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "COMMENT")
public class Comment extends UpdatableModel implements Serializable {
    private static final long serialVersionUID = 8283917169897316138L;

    private boolean checked;
    private boolean visible;
    private Pupil author;
    private Repetitor repetitor;
    private Order order;
    private int rating;
    private String text;

    @Column(nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Pupil getAuthor() {
        return author;
    }

    public void setAuthor(Pupil author) {
        this.author = author;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Repetitor getRepetitor() {
        return repetitor;
    }

    public void setRepetitor(Repetitor repetitor) {
        this.repetitor = repetitor;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(nullable = false)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Column(nullable = false, length = 500)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(nullable = false)
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
