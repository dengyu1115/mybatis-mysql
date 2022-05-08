package org.nature.mybatis.function;

import java.util.List;

@FunctionalInterface
public interface ListAll<T> {

    List<T> listAll();
}
