package org.nature.mybatis.builder;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.annotation.Delete;
import org.nature.mybatis.annotation.Select;
import org.nature.mybatis.builder.source.Source;
import org.nature.mybatis.builder.source.annotated.DeleteSource;
import org.nature.mybatis.builder.source.annotated.SelectSource;
import org.nature.mybatis.builder.source.functional.*;
import org.nature.mybatis.function.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Router {

    private static final Map<String, Object> LOCKS = new ConcurrentHashMap<>(), METHODS = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Source> SOURCES = new HashMap<>();

    static {
        SOURCES.put(Select.class, new SelectSource());
        SOURCES.put(Delete.class, new DeleteSource());
        SOURCES.put(BatchMerge.class, new BatchMergeSource());
        SOURCES.put(BatchSave.class, new BatchSaveSource());
        SOURCES.put(DeleteAll.class, new DeleteAllSource());
        SOURCES.put(DeleteById.class, new DeleteByIdSource());
        SOURCES.put(DeleteByIds.class, new DeleteByIdsSource());
        SOURCES.put(FindById.class, new FindByIdSource());
        SOURCES.put(ListAll.class, new ListAllSource());
        SOURCES.put(ListByIds.class, new ListByIdsSource());
        SOURCES.put(Merge.class, new MergeSource());
        SOURCES.put(Save.class, new SaveSource());
        SOURCES.put(Update.class, new UpdateSource());
    }

    public static void addStatement(Configuration config, Class<?> mapper, Method method) {
        String name = mapper.getName() + "." + method.getName();
        Object o = LOCKS.computeIfAbsent(name, k -> new Object());
        synchronized (o) {
            if (METHODS.containsKey(name)) {
                return;
            }
            Source source = getSource(method);
            if (source == null) {
                throw new BindingException(String.format("%s find no source", name));
            }
            source.addSource(config, mapper, method);
            METHODS.put(name, new byte[0]);
        }

    }

    private static Source getSource(Method method) {
        Source source = SOURCES.get(method.getDeclaringClass());
        if (source != null) {
            return source;
        }
        Select select = method.getAnnotation(Select.class);
        if (select != null) {
            source = SOURCES.get(Select.class);
            if (source != null) {
                return source;
            }
        }
        Delete delete = method.getAnnotation(Delete.class);
        if (delete != null) {
            return SOURCES.get(Delete.class);
        }
        return null;
    }

    public static SqlCommandType getCommandType(Method method) {
        Source source = getSource(method);
        if (source == null) {
            return null;
        }
        return source.getType();
    }

}
