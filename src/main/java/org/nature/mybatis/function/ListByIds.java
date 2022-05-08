package org.nature.mybatis.function;

import org.apache.ibatis.annotations.Param;

import java.util.List;

@FunctionalInterface
public interface ListByIds<T, I> {

    List<T> findByIds(@Param("ids") List<I> ids);
}
