package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;

import java.util.List;

public class DeleteAllSource extends BaseUpdateSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.DELETE;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> fields) {
        String sql = "delete from " + table;
        return new RawSqlSource(config, sql, null);
    }
}
