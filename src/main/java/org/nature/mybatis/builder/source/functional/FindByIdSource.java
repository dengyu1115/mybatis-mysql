package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.MappingUtil;
import org.nature.mybatis.builder.util.TextUtil;

import java.util.List;

public class FindByIdSource extends BaseQuerySource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.SELECT;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> mappings) {
        List<Mapping> list = MappingUtil.idList(mappings);
        String sql = "select " + TextUtil.columns(mappings) + " from " + table +
                " where (" + TextUtil.columns(list) + ")=(" + TextUtil.properties(list) + ")";
        return new RawSqlSource(config, sql, null);
    }
}
