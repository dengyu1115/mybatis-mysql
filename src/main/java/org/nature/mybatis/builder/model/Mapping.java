package org.nature.mybatis.builder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mapping {
    private String property;
    private String column;
    private Class<?> type;
    private boolean id;
}
