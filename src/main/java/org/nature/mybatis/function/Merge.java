package org.nature.mybatis.function;

@FunctionalInterface
public interface Merge<T> {

    int merge(T datum);
}
