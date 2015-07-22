package org.webservice.database.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.webservice.database.model.base.AbstractUser;
import org.webservice.database.model.enums.Gender;
import org.webservice.database.model.enums.RegionType;
import org.webservice.database.model.enums.RepetitorDegree;

@Entity
@Table(name = "REPETITOR")
@DynamicUpdate
public class Repetitor extends AbstractUser {

    private static final long serialVersionUID = 8606790114284987539L;

    private String name;
    private String surname;
    private String patronymic;
    private String phone1;
    private String phone2;
    private String email;
    private Date birthdate;
    private Gender gender;
    private Region city;
    private boolean placeRepetitor;
    private Region placeRepetitorRegion;
    private String placeRepetitorInfo;
    private boolean placePupil;
    private String placePupilInfo;
    private boolean placeOnline;
    private String placeOnlineInfo;
    private BigDecimal hourPrice = BigDecimal.ZERO;
    private RepetitorDegree degree;
    private Date stageSince;
    private String education;
    private String additionalInfo;
    private String image = "no_image.png";

    private Set<RepetitorSubject> subjects = new LinkedHashSet<RepetitorSubject>();
    private boolean approved;
    private boolean verified;
    private int overalRating;
    private int overalRatingCount;
    private int rating;
    private int ratingCount;
    private int commentCount;
    private int orderCount;
    private int viewCount;
    private boolean showInSearch = true;
    private boolean showOnMap = true;
    private boolean notifyOrders = true;
    private boolean notifyChanges = true;
    private boolean needStudents = true;
    private Date notNeedStudentsUntil;
    private Date lastLogin;
    private float rate = 1.0f;
    private List<Order> orders;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(length = 20)
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Column(length = 20)
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Transient
    public int getAge() {
        return (int) ((System.currentTimeMillis() - birthdate.getTime()) / 31557600000L);
    }

    @Column
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    // @ForeignKey(name = "fk_Repetitor_City")
    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    @Column(nullable = false)
    public boolean isPlaceRepetitor() {
        return placeRepetitor;
    }

    public void setPlaceRepetitor(boolean placeRepetitor) {
        this.placeRepetitor = placeRepetitor;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    // @ForeignKey(name = "fk_Repetitor_Region")
    public Region getPlaceRepetitorRegion() {
        return placeRepetitorRegion;
    }

    public void setPlaceRepetitorRegion(Region placeRepetitorRegion) {
        if (null == this.city && null != placeRepetitorRegion) {
            this.city = placeRepetitorRegion.getFirstParentOfType(RegionType.CITY);
        }
        this.placeRepetitorRegion = placeRepetitorRegion;
    }

    @Column
    public String getPlaceRepetitorInfo() {
        return placeRepetitorInfo;
    }

    public void setPlaceRepetitorInfo(String placeRepetitorInfo) {
        this.placeRepetitorInfo = placeRepetitorInfo;
    }

    @Column(nullable = false)
    public boolean isPlacePupil() {
        return placePupil;
    }

    public void setPlacePupil(boolean lessonPupil) {
        this.placePupil = lessonPupil;
    }

    @Column
    public String getPlacePupilInfo() {
        return placePupilInfo;
    }

    public void setPlacePupilInfo(String placePupilInfo) {
        this.placePupilInfo = placePupilInfo;
    }

    @Column(nullable = false)
    public boolean isPlaceOnline() {
        return placeOnline;
    }

    public void setPlaceOnline(boolean placeOnline) {
        this.placeOnline = placeOnline;
    }

    @Column
    public String getPlaceOnlineInfo() {
        return placeOnlineInfo;
    }

    public void setPlaceOnlineInfo(String lessonOnlineInfo) {
        this.placeOnlineInfo = lessonOnlineInfo;
    }

    @Column(nullable = false)
    public BigDecimal getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(BigDecimal hourPrice) {
        this.hourPrice = hourPrice;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public RepetitorDegree getDegree() {
        return degree;
    }

    public void setDegree(RepetitorDegree degree) {
        this.degree = degree;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getStageSince() {
        return stageSince;
    }

    public void setStageSince(Date stageSince) {
        this.stageSince = stageSince;
    }

    @Transient
    public int getStage() {
        return Math.round((System.currentTimeMillis() - stageSince.getTime()) / 31557600000.00F);
    }

    @Column
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Column
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.repetitor", cascade = { CascadeType.ALL })
    @OrderColumn(name = "id")
    public Set<RepetitorSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<RepetitorSubject> subjects) {
        this.subjects = subjects;
    }

    @Column(nullable = false)
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Column(nullable = false)
    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Column
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(nullable = false)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Column(nullable = false)
    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    @Column(nullable = false)
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Column(nullable = false)
    public int getOveralRating() {
        return overalRating;
    }

    public void setOveralRating(int overalRating) {
        this.overalRating = overalRating;
    }

    @Column(nullable = false)
    public int getOveralRatingCount() {
        return overalRatingCount;
    }

    public void setOveralRatingCount(int overalRatingCount) {
        this.overalRatingCount = overalRatingCount;
    }

    @Column(nullable = false)
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Column(nullable = false)
    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Column
    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Column(nullable = false)
    public boolean isShowInSearch() {
        return showInSearch;
    }

    public void setShowInSearch(boolean showInSearch) {
        this.showInSearch = showInSearch;
    }

    @Column(nullable = false)
    public boolean isShowOnMap() {
        return showOnMap;
    }

    public void setShowOnMap(boolean showOnMap) {
        this.showOnMap = showOnMap;
    }

    @Column(nullable = false)
    public boolean isNotifyOrders() {
        return notifyOrders;
    }

    public void setNotifyOrders(boolean notifyOrders) {
        this.notifyOrders = notifyOrders;
    }

    @Column(nullable = false)
    public boolean isNotifyChanges() {
        return notifyChanges;
    }

    public void setNotifyChanges(boolean notifyChanges) {
        this.notifyChanges = notifyChanges;
    }

    @Column(nullable = false)
    public boolean isNeedStudents() {
        return needStudents;
    }

    public void setNeedStudents(boolean needStudents) {
        this.needStudents = needStudents;
    }

    @Column
    public Date getNotNeedStudentsUntil() {
        return notNeedStudentsUntil;
    }

    public void setNotNeedStudentsUntil(Date notNeedStudentsUntil) {
        this.notNeedStudentsUntil = notNeedStudentsUntil;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    public List<Order> getOrders() {
        return orders;
    }

    @Column(nullable = false)
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
