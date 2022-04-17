package org.nature.mybatis.builder.util;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultMapUtil {

    public static List<ResultMap> resultMaps(Configuration configuration, String id, Class<?> type, List<Mapping> mappings) {
        return toList(resultMap(configuration, id, type, mappings));
    }

    private static ResultMap resultMap(Configuration configuration, String id, Class<?> type, List<Mapping> mappings) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (Mapping mapping : mappings) {
            String column = mapping.getColumn().replace("`", "");
            ResultMapping resultMapping = new ResultMapping
                    .Builder(configuration, mapping.getProperty(), column, mapping.getType()).build();
            resultMappings.add(resultMapping);
        }
        return new ResultMap.Builder(configuration, id + ".ResultMap", type, resultMappings).build();
    }

    @SafeVarargs
    public static <T> List<T> toList(T... ts) {
        return Arrays.asList(ts);
    }

}
