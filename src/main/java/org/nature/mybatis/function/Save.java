package org.nature.mybatis.function;

@FunctionalInterface
public interface Save<T> {

    int save(T datum);
}
