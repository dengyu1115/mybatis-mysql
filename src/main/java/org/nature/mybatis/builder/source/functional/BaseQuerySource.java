package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.ResultMapUtil;

import java.util.List;

public abstract class BaseQuerySource extends BaseSource {

    @Override
    protected void addStatement(Configuration config, String name, Class<?> model, List<Mapping> mappings, SqlSource sqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(config, name, sqlSource, this.getType());
        builder.resultMaps(ResultMapUtil.resultMaps(config, name, model, mappings));
        MappedStatement ms = builder.build();
        config.addMappedStatement(ms);
    }

}
