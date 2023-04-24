package com.kunix.studentservice.model.hypersistence;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.type.BasicType;
import org.hibernate.type.ForeignKeyDirection;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.IncomparableComparator;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class ImmutableType<T> implements UserType, BasicType {
    private final Configuration configuration;
    private final Class<T> clazz;

    protected ImmutableType(Class<T> clazz) {
        this.clazz = clazz;
        this.configuration = Configuration.INSTANCE;
    }

    protected ImmutableType(Class<T> clazz, Configuration configuration) {
        this.clazz = clazz;
        this.configuration = configuration;
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    protected abstract T get(ResultSet var1, String[] var2, SharedSessionContractImplementor var3, Object var4) throws SQLException;

    protected abstract void set(PreparedStatement var1, T var2, int var3, SharedSessionContractImplementor var4) throws SQLException;

    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        return this.get(rs, names, session, owner);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        this.set(st, this.clazz.cast(value), index, session);
    }

    public Class<T> returnedClass() {
        return this.clazz;
    }

    public boolean equals(Object x, Object y) {
        return x == y || x != null && x.equals(y);
    }

    public int hashCode(Object x) {
        return x.hashCode();
    }

    public Object deepCopy(Object value) {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object o) {
        return (Serializable)o;
    }

    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    public Object replace(Object o, Object target, Object owner) {
        return o;
    }

    public boolean isAssociationType() {
        return false;
    }

    public boolean isCollectionType() {
        return false;
    }

    public boolean isEntityType() {
        return false;
    }

    public boolean isAnyType() {
        return false;
    }

    public boolean isComponentType() {
        return false;
    }

    public int getColumnSpan(Mapping mapping) throws MappingException {
        return 1;
    }

    public int[] sqlTypes(Mapping mapping) throws MappingException {
        return this.sqlTypes();
    }

    public Size[] dictatedSizes(Mapping mapping) throws MappingException {
        return new Size[]{new Size()};
    }

    public Size[] defaultSizes(Mapping mapping) throws MappingException {
        return this.dictatedSizes(mapping);
    }

    public Class getReturnedClass() {
        return this.returnedClass();
    }

    public boolean isSame(Object x, Object y) throws HibernateException {
        return this.equals(x, y);
    }

    public boolean isEqual(Object x, Object y) throws HibernateException {
        return this.equals(x, y);
    }

    public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory) throws HibernateException {
        return this.equals(x, y);
    }

    public int getHashCode(Object x) throws HibernateException {
        return this.hashCode(x);
    }

    public int getHashCode(Object x, SessionFactoryImplementor factory) throws HibernateException {
        return this.hashCode(x);
    }

    public int compare(Object x, Object y) {
        return IncomparableComparator.INSTANCE.compare(x, y);
    }

    public final boolean isDirty(Object old, Object current, SharedSessionContractImplementor session) {
        return this.isDirty(old, current);
    }

    public final boolean isDirty(Object old, Object current, boolean[] checkable, SharedSessionContractImplementor session) {
        return checkable[0] && this.isDirty(old, current);
    }

    protected final boolean isDirty(Object old, Object current) {
        return !this.isSame(old, current);
    }

    public boolean isModified(Object dbState, Object currentState, boolean[] checkable, SharedSessionContractImplementor session) throws HibernateException {
        return this.isDirty(dbState, currentState);
    }

    public Object nullSafeGet(ResultSet rs, String name, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        return this.get(rs, new String[]{name}, session, owner);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        this.set(st, this.returnedClass().cast(value), index, session);
    }

    public String toLoggableString(Object value, SessionFactoryImplementor factory) throws HibernateException {
        return String.valueOf(value);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public Object deepCopy(Object value, SessionFactoryImplementor factory) throws HibernateException {
        return this.deepCopy(value);
    }

    public Serializable disassemble(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return this.disassemble(value);
    }

    public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return this.assemble(cached, session);
    }

    public void beforeAssemble(Serializable cached, SharedSessionContractImplementor session) {
    }

    public Object hydrate(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        return this.nullSafeGet(rs, names, session, owner);
    }

    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return value;
    }

    public Object semiResolve(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return value;
    }

    public Type getSemiResolvedType(SessionFactoryImplementor factory) {
        return this;
    }

    public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner, Map copyCache) throws HibernateException {
        return this.replace(original, target, owner);
    }

    public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection) throws HibernateException {
        return this.replace(original, target, owner);
    }

    public boolean[] toColumnNullness(Object value, Mapping mapping) {
        return value == null ? ArrayHelper.FALSE : ArrayHelper.TRUE;
    }

    public String[] getRegistrationKeys() {
        return new String[]{this.getName()};
    }
}
