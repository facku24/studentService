package com.kunix.studentservice.model.hypersistence;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLRangeType extends ImmutableType<Range> implements DynamicParameterizedType {
    public static final PostgreSQLRangeType INSTANCE = new PostgreSQLRangeType();
    private Type type;

    public PostgreSQLRangeType() {
        super(Range.class);
    }

    public int[] sqlTypes() {
        return new int[]{1111};
    }

    protected Range get(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Object pgObject = rs.getObject(names[0]);
        if (pgObject == null) {
            return null;
        } else {
            String type = (String) ReflectionUtils.invokeGetter(pgObject, "type");
            String value = (String)ReflectionUtils.invokeGetter(pgObject, "value");
            switch (type) {
                case "int4range":
                    return Range.integerRange(value);
                case "int8range":
                    return Range.longRange(value);
                case "numrange":
                    return Range.bigDecimalRange(value);
                case "tsrange":
                    return Range.localDateTimeRange(value);
                case "tstzrange":
                    return Range.zonedDateTimeRange(value);
                case "daterange":
                    return Range.localDateRange(value);
                default:
                    throw new HibernateException(new IllegalStateException("The range type [" + type + "] is not supported!"));
            }
        }
    }

    protected void set(PreparedStatement st, Range range, int index, SharedSessionContractImplementor session) throws SQLException {
        if (range == null) {
            st.setNull(index, 1111);
        } else {
            Object holder = ReflectionUtils.newInstance("org.postgresql.util.PGobject");
            ReflectionUtils.invokeSetter(holder, "type", determineRangeType(range));
            ReflectionUtils.invokeSetter(holder, "value", range.asString());
            st.setObject(index, holder);
        }

    }

    private static String determineRangeType(Range<?> range) {
        /*Class<?> clazz = range.getClazz();
        if (clazz.equals(Integer.class)) {
            return "int4range";
        } else if (clazz.equals(Long.class)) {
            return "int8range";
        } else if (clazz.equals(BigDecimal.class)) {
            return "numrange";
        } else if (clazz.equals(LocalDateTime.class)) {
            return "tsrange";
        } else if (clazz.equals(ZonedDateTime.class)) {
            return "tstzrange";
        } else if (clazz.equals(LocalDate.class)) {
            return "daterange";
        } else {
            throw new HibernateException(new IllegalStateException("The class [" + clazz.getName() + "] is not supported!"));
        }*/
        return "int4range";
    }

    public void setParameterValues(Properties parameters) {
        XProperty xProperty = (XProperty)parameters.get("org.hibernate.type.ParameterType.xproperty");
        if (xProperty instanceof JavaXMember) {
            this.type = ((JavaXMember)xProperty).getJavaType();
        } else {
            this.type = ((DynamicParameterizedType.ParameterType)parameters.get("org.hibernate.type.ParameterType")).getReturnedClass();
        }

    }

    public Class<?> getElementType() {
        return this.type instanceof ParameterizedType ? (Class)((ParameterizedType)this.type).getActualTypeArguments()[0] : null;
    }
}
