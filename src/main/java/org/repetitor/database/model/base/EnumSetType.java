package org.repetitor.database.model.base;

import org.repetitor.database.model.types.CollectionType;

import java.util.Collection;
import java.util.EnumSet;

public class EnumSetType<E extends Enum<E>> extends CollectionType<E> {

    @Override
    public Collection<E> createCollection(Class<E> class1, int size) {
        return EnumSet.noneOf(class1);
    }
}
