package org.nature.mybatis.builder.source.annotated;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.annotation.Delete;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.NodeUtil;

import java.lang.reflect.Method;
import java.util.List;

public class DeleteSource extends BaseSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.DELETE;
    }

    @Override
    protected void addStatement(Configuration config, String name, Class<?> model, List<Mapping> mappings, SqlSource sqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(config, name, sqlSource, this.getType());
        MappedStatement ms = builder.build();
        config.addMappedStatement(ms);
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, String where, String in, List<Mapping> mappings) {
        String sql = "delete from " + table + " where " + where;
        if (StringUtils.isBlank(in)) {
            return new RawSqlSource(config, sql, null);
        }
        return new DynamicSqlSource(config, NodeUtil.insNode(config, sql, in));
    }

    @Override
    protected String textIn(Method method) {
        Delete annotation = method.getAnnotation(Delete.class);
        return annotation.in();
    }

    @Override
    protected String textWhere(Method method) {
        Delete annotation = method.getAnnotation(Delete.class);
        return annotation.where();
    }
}
