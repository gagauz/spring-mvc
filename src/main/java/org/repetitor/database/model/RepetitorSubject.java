package org.repetitor.database.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.repetitor.database.model.enums.PupilCategory;
import org.repetitor.database.model.types.CollectionType;
import org.repetitor.database.model.types.EnumSetType;
import org.repetitor.utils.CollectionUtils;
import org.repetitor.utils.Filters;

import javax.persistence.*;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.repetitor.utils.CollectionUtils.filterByMask;
import static org.repetitor.utils.CollectionUtils.getMask;

@Entity
@Table(name = "REPETITOR_SUBJECT")
@TypeDefs({
        @TypeDef(name = "bitSet", typeClass = BitSet.class),
        @TypeDef(name = "enumSetOf.PupilCategory", typeClass = EnumSetType.class, parameters = {@Parameter(name = CollectionType.CLASS, value = "org.repetitor.database.model.enums.PupilCategory")})})
@AssociationOverrides({@AssociationOverride(name = "id.repetitor", joinColumns = @JoinColumn(name = "repetitor_id"))})
@AttributeOverrides({@AttributeOverride(name = "id.subject", column = @Column(name = "subject_id"))})
public class RepetitorSubject {

    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = -852739246393583334L;
        private Repetitor repetitor;
        private Subject subject;

        public Id() {

        }

        public Id(Repetitor repetitor, Subject subject) {
            this.repetitor = repetitor;
            this.subject = subject;
        }

        @ManyToOne(fetch = FetchType.LAZY)
        // @ForeignKey(name = "fk_RepetitorSubjectId_Repetitor")
        public Repetitor getRepetitor() {
            return repetitor;
        }

        public void setRepetitor(Repetitor repetitor) {
            this.repetitor = repetitor;
        }

        @ManyToOne(fetch = FetchType.LAZY)
        // @ForeignKey(name = "fk_RepetitorSubjectId_Subject")
        public Subject getSubject() {
            return subject;
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        @Override
        public int hashCode() {
            return null == repetitor || null == subject ? 0 : (repetitor.hashCode() << 32)
                    * subject.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj || !(obj instanceof Id)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return ((Id) obj).repetitor == repetitor && ((Id) obj).subject == subject;
        }

        @Override
        public String toString() {
            return "ID: {repetitor=" + repetitor.getId() + ", subject=" + subject.getId() + "}";
        }
    }

    private Id id = new Id();
    private long extras;
    private long sections;
    private EnumSet<PupilCategory> pupilCategories = EnumSet.noneOf(PupilCategory.class);
    private String info;

    public RepetitorSubject() {
    }

    public RepetitorSubject(Repetitor repetitor, Subject subject) {
        this.id.setRepetitor(repetitor);
        this.id.setSubject(subject);
    }

    @EmbeddedId
    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(nullable = false)
    @Type(type = "enumSetOf.PupilCategory")
    public EnumSet<PupilCategory> getPupilCategories() {
        return pupilCategories;
    }

    public void setPupilCategories(EnumSet<PupilCategory> pupilCategories) {
        this.pupilCategories = pupilCategories;
    }

    @Column(nullable = false)
    public long getExtras() {
        return extras;
    }

    public void setExtras(long extras) {
        this.extras = extras;
    }

    @Column(nullable = false)
    public long getSections() {
        return sections;
    }

    public void setSections(long sections) {
        this.sections = sections;
    }

    @Transient
    public Repetitor getRepetitor() {
        return id.getRepetitor();
    }

    @Transient
    public Subject getSubject() {
        return id.getSubject();
    }

    @Transient
    public void setSubject(Subject subject) {
        id.setSubject(subject);
    }

    @Transient
    public List<SubjectSection> getSubjectSections() {
        return filterByMask(getSubject().getSections(), sections, Filters.VISIBLE_SECTION);
    }

    @Transient
    public void setSubjectSections(Collection<SubjectSection> sections) {
        this.sections = getMask(sections);
    }

    @Transient
    public boolean isHasSection(SubjectSection section) {
        return CollectionUtils.hasElement(section, sections);
    }

    @Transient
    public void setHasSection(SubjectSection section, boolean has) {
        sections = has ? CollectionUtils.appendElement(section, sections) : CollectionUtils.removeElement(section, sections);
    }

    @Transient
    public List<SubjectExtra> getSubjectExtras() {
        return filterByMask(getSubject().getExtras(), extras, Filters.VISIBLE_EXTRA);
    }

    @Transient
    public void setSubjectExtras(Collection<SubjectExtra> extras) {
        this.extras = getMask(extras);
    }

    @Transient
    public boolean isHasExtra(SubjectExtra extra) {
        return CollectionUtils.hasElement(extra, extras);
    }

    @Transient
    public void setHasExtra(SubjectExtra extra, boolean has) {
        sections = has ? CollectionUtils.appendElement(extra, extras) : CollectionUtils.removeElement(extra, extras);
    }

}
