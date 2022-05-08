package org.nature.mybatis.builder.source.annotated;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.annotation.Select;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.NodeUtil;
import org.nature.mybatis.builder.util.ResultMapUtil;
import org.nature.mybatis.builder.util.TextUtil;

import java.lang.reflect.Method;
import java.util.List;

public class SelectSource extends BaseSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.SELECT;
    }

    @Override
    protected void addStatement(Configuration config, String name, Class<?> model, List<Mapping> mappings, SqlSource sqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(config, name, sqlSource, this.getType());
        builder.resultMaps(ResultMapUtil.resultMaps(config, name, model, mappings));
        MappedStatement ms = builder.build();
        config.addMappedStatement(ms);
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, String where, String in, List<Mapping> mappings) {
        String sql = "select " + TextUtil.columns(mappings) + " where " + where;
        if (StringUtils.isBlank(in)) {
            return new RawSqlSource(config, sql, null);
        }
        return new DynamicSqlSource(config, NodeUtil.insNode(config, sql, in));
    }

    @Override
    protected String textIn(Method method) {
        Select annotation = method.getAnnotation(Select.class);
        return annotation.in();
    }

    @Override
    protected String textWhere(Method method) {
        Select annotation = method.getAnnotation(Select.class);
        return annotation.where();
    }
}
