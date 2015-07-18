package org.repetitor.database.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(value = AccessType.PROPERTY)
public class LessonInfo {

}
