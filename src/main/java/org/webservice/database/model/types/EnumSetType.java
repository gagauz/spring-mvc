package org.webservice.database.model.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class EnumSetType<E extends Enum<E>> implements UserType, ParameterizedType {

    private static final int SQL_TYPE = Types.BIGINT;
    private static final int[] SQL_TYPES = new int[] { SQL_TYPE };

    public static final String ELEMENT_CLASS = "class";

    private static final Class<?> RETURNED_CLASS = EnumSet.class;

    protected Class<E> enumClass;

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        long value = rs.getLong(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        final EnumSet<E> enumConsts = EnumSet.noneOf(enumClass);
        E[] all = enumClass.getEnumConstants();
        for (byte i = 0; i < all.length; i++, value = value >>> 1) {
            if ((value & 0x01) > 0) {
                enumConsts.add(all[i]);
            }
        }
        return enumConsts;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParameterValues(Properties parameters) {
        try {
            final String enumClassName = parameters.getProperty(ELEMENT_CLASS);
            enumClass = (Class<E>) Class.forName(enumClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("AbstractEnumSetType. Enum class not found", e);
        }
    }

    @Override
    public Class<?> returnedClass() {
        return RETURNED_CLASS;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x != null ? x.equals(y) : y == null;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x != null ? x.hashCode() : 0;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value != null ? EnumSet.copyOf((EnumSet<?>) value) : null;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlTypes()[0]);
        } else {
            @SuppressWarnings("unchecked")
            final EnumSet<E> enumConsts = (EnumSet<E>) value;

            if (!checkElementType(enumConsts)) {
                throw new IllegalArgumentException(
                        "AbstractEnumSetType. Incorrect EnumSet element type! EnumClass = "
                                + enumClass.getName());
            }

            long longValue = 0;
            for (E e : enumConsts) {
                longValue = longValue | (0x0000000000000001 << e.ordinal());
            }
            st.setLong(index, longValue);
        }
    }

    protected boolean checkElementType(final EnumSet<?> set) {

        if (set.isEmpty()) {
            return true;
        }

        return enumClass.isAssignableFrom(set.iterator().next().getClass());
    }
}
