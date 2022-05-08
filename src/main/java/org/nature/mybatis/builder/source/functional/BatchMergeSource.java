package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.NodeUtil;
import org.nature.mybatis.builder.util.TextUtil;

import java.util.List;

public class BatchMergeSource extends BaseUpdateSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.INSERT;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> mappings) {
        String main = "replace into " + table + "(" + TextUtil.columns(mappings) + ") values ";
        String sub = "(" + TextUtil.itemProperties(mappings) + ")";
        MixedSqlNode node = NodeUtil.valuesNode(config, main, sub);
        return new DynamicSqlSource(config, node);
    }
}
