package org.webservice.database.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.webservice.database.model.base.OrderChangeStatusHandler;
import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.*;
import org.webservice.database.model.types.CollectionType;
import org.webservice.database.model.types.EnumSetType;
import org.webservice.utils.CollectionUtils;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@TypeDefs({
        @TypeDef(name = "enumSetOf.RepetitorDegree", typeClass = EnumSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "org.webservice.database.model.enums.RepetitorDegree")
        })
})
public class Order extends UpdatableModel implements Serializable {

    private static final long serialVersionUID = 1719421850725346271L;

    private static OrderChangeStatusHandler STATUS_CHANGE_HANDLER;

    private Pupil pupil;
    private Subject subject;
    private long extrasMask;
    private long sectionsMask;
    private Repetitor owner;
    private List<Repetitor> wantedRepetitors;
    private TotalLessonCount totalLessonCount = TotalLessonCount.UNKNOWN;
    private WeekLessonCount weekLessonCount = WeekLessonCount.UNKNOWN;
    private String description;
    private OrderStatus status = OrderStatus.OPEN;

    private String pupilName;
    private PupilCategory pupilCategory;
    private boolean placeRepetitor;
    private boolean placePupil;
    private Region placePupilRegion;
    private boolean placeOnline;

    private BigDecimal priceMin = BigDecimal.ZERO;
    private BigDecimal priceMax = BigDecimal.ZERO;
    // Финальная цена уточняется при созвоне с репетитором перед назначением
    // заказа
    private BigDecimal finalPrice = BigDecimal.ZERO;
    // Комиссия за предоставление заказа
    private BigDecimal commission = BigDecimal.ZERO;
    private Gender repetitorGender;
    private EnumSet<RepetitorDegree> repetitorDegree = EnumSet.noneOf(RepetitorDegree.class);
    private int repetitorAgeMin;
    private int repetitorAgeMax;
    private int repetitorStageMin;
    private int repetitorStageMax;
    private float ratio = 1.0f;
    private List<OrderLog> orderLogs;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Column(nullable = false)
    public long getExtrasMask() {
        return extrasMask;
    }

    public void setExtrasMask(long extrasMask) {
        this.extrasMask = extrasMask;
    }

    @Column(nullable = false)
    public long getSectionsMask() {
        return sectionsMask;
    }

    public void setSectionsMask(long sectionsMask) {
        this.sectionsMask = sectionsMask;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Repetitor getOwner() {
        return owner;
    }

    public void setOwner(Repetitor owner) {
        this.owner = owner;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ORDER_WANTED_REPETITORS", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "repetitor_id"))
    public List<Repetitor> getWantedRepetitors() {
        return wantedRepetitors;
    }

    public void setWantedRepetitors(List<Repetitor> repetitors) {
        this.wantedRepetitors = repetitors;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public TotalLessonCount getTotalLessonCount() {
        return totalLessonCount;
    }

    public void setTotalLessonCount(TotalLessonCount totalLessonCount) {
        this.totalLessonCount = totalLessonCount;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public WeekLessonCount getWeekLessonCount() {
        return weekLessonCount;
    }

    public void setWeekLessonCount(WeekLessonCount weekLessonCount) {
        this.weekLessonCount = weekLessonCount;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean changeStatus(OrderStatus newStatus) {
        while (null == STATUS_CHANGE_HANDLER) {
            //Wait for app initialization
        }
        if (newStatus != this.status && this.status.getNextStatuses().contains(newStatus)) {
            STATUS_CHANGE_HANDLER.handle(this, newStatus);
            this.status = newStatus;
        }
        return newStatus == this.status;
    }

    @Column
    public String getPupilName() {
        return pupilName;
    }

    public void setPupilName(String pupilName) {
        this.pupilName = pupilName;
    }

    @Column
    public PupilCategory getPupilCategory() {
        return pupilCategory;
    }

    public void setPupilCategory(PupilCategory pupilCategory) {
        this.pupilCategory = pupilCategory;
    }

    @Column
    public boolean isPlaceRepetitor() {
        return placeRepetitor;
    }

    public void setPlaceRepetitor(boolean placeRepetitor) {
        this.placeRepetitor = placeRepetitor;
    }

    @Column
    public boolean isPlacePupil() {
        return placePupil;
    }

    public void setPlacePupil(boolean placePupil) {
        this.placePupil = placePupil;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Region getPlacePupilRegion() {
        return placePupilRegion;
    }

    public void setPlacePupilRegion(Region placePupilRegion) {
        this.placePupilRegion = placePupilRegion;
    }

    @Column
    public boolean isPlaceOnline() {
        return placeOnline;
    }

    public void setPlaceOnline(boolean placeOnline) {
        this.placeOnline = placeOnline;
    }

    @Column
    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    @Column
    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(BigDecimal priceMax) {
        this.priceMax = priceMax;
    }

    @Column
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Column
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public Gender getRepetitorGender() {
        return repetitorGender;
    }

    public void setRepetitorGender(Gender repetitorGender) {
        this.repetitorGender = repetitorGender;
    }

    @Column
    @Type(type = "enumSetOf.RepetitorDegree")
    public EnumSet<RepetitorDegree> getRepetitorDegree() {
        return repetitorDegree;
    }

    public void setRepetitorDegree(EnumSet<RepetitorDegree> repetitorDegree) {
        this.repetitorDegree = repetitorDegree;
    }

    @Column
    public int getRepetitorAgeMin() {
        return repetitorAgeMin;
    }

    public void setRepetitorAgeMin(int repetitorAgeMin) {
        this.repetitorAgeMin = repetitorAgeMin;
    }

    @Column
    public int getRepetitorAgeMax() {
        return repetitorAgeMax;
    }

    public void setRepetitorAgeMax(int repetitorAgeMax) {
        this.repetitorAgeMax = repetitorAgeMax;
    }

    @Column
    public int getRepetitorStageMin() {
        return repetitorStageMin;
    }

    public void setRepetitorStageMin(int repetitorStageMin) {
        this.repetitorStageMin = repetitorStageMin;
    }

    @Column
    public int getRepetitorStageMax() {
        return repetitorStageMax;
    }

    public void setRepetitorStageMax(int repetitorStageMax) {
        this.repetitorStageMax = repetitorStageMax;
    }

    @Column(nullable = false)
    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.ALL})
    public List<OrderLog> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLog> orderLogs) {
        this.orderLogs = orderLogs;
    }

    @Transient
    public List<SubjectSection> getSubjectSections() {
        return CollectionUtils.filterByMask(subject.getSections(), sectionsMask);
    }

    @Transient
    public void setSubjectSections(Collection<SubjectSection> sections) {
        this.sectionsMask = CollectionUtils.getMask(sections);
    }

    @Transient
    public List<SubjectExtra> getSubjectExtras() {
        return CollectionUtils.filterByMask(subject.getExtras(), extrasMask);
    }

    @Transient
    public void setSubjectExtras(Collection<SubjectExtra> extras) {
        this.extrasMask = CollectionUtils.getMask(extras);
    }

    public static void setChangeStatusHandler(OrderChangeStatusHandler handler) {
        if (null == handler) {
            throw new IllegalStateException("OrderChangeStatusHandler cannot be null!");
        }
        STATUS_CHANGE_HANDLER = handler;
    }

}
