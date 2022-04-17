package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.MappingUtil;
import org.nature.mybatis.builder.util.TextUtil;

import java.util.List;

public class UpdateSource extends BaseUpdateSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.UPDATE;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> mappings) {
        List<Mapping> idList = MappingUtil.idList(mappings);
        mappings.removeAll(idList);
        StringBuilder builder = new StringBuilder("update " + table + " set ");
        int size = mappings.size();
        for (int i = 0; i < size; i++) {
            Mapping mapping = mappings.get(i);
            builder.append(mapping.getColumn()).append(" = ");
            builder.append("#{").append(mapping.getProperty()).append("}");
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append(" where ");
        String sql = builder + "(" + TextUtil.columns(idList) + ")=(" + TextUtil.properties(idList) + ")";
        return new RawSqlSource(config, sql, null);
    }
}
