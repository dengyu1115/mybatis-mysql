package org.nature.mybatis.builder.source;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;

public interface Source {

    void addSource(Configuration config, Class<?> mapper, Method method);

    SqlCommandType getType();

}
