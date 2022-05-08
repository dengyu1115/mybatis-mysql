package org.nature.mybatis.builder.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.BindingException;
import org.nature.mybatis.annotation.Column;
import org.nature.mybatis.annotation.Hold;
import org.nature.mybatis.annotation.Id;
import org.nature.mybatis.annotation.Model;
import org.nature.mybatis.builder.model.Mapping;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class MappingUtil {

    public static List<Mapping> list(Class<?> cls) {
        Set<Mapping> fields = new HashSet<>();
        Model ann = cls.getAnnotation(Model.class);
        Set<String> excludeFields = Arrays.stream(ann.excludeFields()).collect(Collectors.toSet());
        doAddFields(cls, fields, excludeFields, "", "");
        return new ArrayList<>(fields);
    }

    public static List<Mapping> idList(List<Mapping> mappings) {
        List<Mapping> list = mappings.stream().filter(Mapping::isId).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new BindingException("no field marked as Id");
        }
        return list;
    }

    private static void doAddFields(Class<?> cls, Set<Mapping> mappings, Set<String> excludeFields, String scope, String prefix) {
        if (cls == Object.class) {
            return;
        }
        doAddFields(cls.getSuperclass(), mappings, excludeFields, scope, prefix);
        Field[] declaredFields = cls.getDeclaredFields();
        Map<String, Mapping> map = mappings.stream().collect(Collectors.toMap(Mapping::getProperty, i -> i));
        for (Field field : declaredFields) {
            String name = field.getName();
            if (excludeFields.contains(name)) {
                continue;
            }
            Hold hold = field.getAnnotation(Hold.class);
            if (StringUtils.isNotBlank(scope)) {
                name = scope + "." + name;
            }
            String column = getColumn(field);
            if (StringUtils.isNotBlank(prefix)) {
                column = prefix + column;
            }
            if (hold == null) {
                Mapping mapping = map.get(name);
                if (mapping != null) {
                    mappings.remove(mapping);
                }
                mappings.add(new Mapping(name, column, field.getType(), field.isAnnotationPresent(Id.class)));
            } else {
                excludeFields = Arrays.stream(hold.excludeFields()).collect(Collectors.toSet());
                doAddFields(field.getType(), mappings, excludeFields, name, prefix + hold.prefix());
            }
        }
    }

    public static String getColumn(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            return column.value();
        }
        String name = field.getName();
        return name.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

}
