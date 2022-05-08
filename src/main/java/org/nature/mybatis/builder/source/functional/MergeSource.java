package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.TextUtil;

import java.util.List;

public class MergeSource extends BaseUpdateSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.INSERT;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> mappings) {
        String sql = "replace into " + table + "(" + TextUtil.columns(mappings) + ") values ("
                + TextUtil.properties(mappings) + ")";
        return new RawSqlSource(config, sql, null);
    }
}
