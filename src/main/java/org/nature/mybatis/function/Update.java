package org.nature.mybatis.function;

@FunctionalInterface
public interface Update<T> {

    int update(T datum);
}
