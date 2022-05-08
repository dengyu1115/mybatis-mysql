package org.nature.mybatis.builder.source.functional;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.util.MappingUtil;
import org.nature.mybatis.builder.util.NodeUtil;
import org.nature.mybatis.builder.util.TextUtil;

import java.util.List;


public class DeleteByIdsSource extends BaseUpdateSource {
    @Override
    public SqlCommandType getType() {
        return SqlCommandType.DELETE;
    }

    @Override
    protected SqlSource sqlSource(Configuration config, String table, List<Mapping> mappings) {
        List<Mapping> list = MappingUtil.idList(mappings);
        String mainText = "delete from " + table + " where (" + TextUtil.columns(list) + ") in";
        String sub = "(" + TextUtil.itemProperties(list) + ")";
        MixedSqlNode sqlNode = NodeUtil.mixedSqlNode(config, mainText, sub, "ids", "(", ")");
        return new DynamicSqlSource(config, sqlNode);
    }
}
