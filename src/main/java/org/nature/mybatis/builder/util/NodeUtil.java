package org.nature.mybatis.builder.util;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;

import java.util.Arrays;

public class NodeUtil {

    public static MixedSqlNode insNode(Configuration configuration, String mainText, String sub) {
        return mixedSqlNode(configuration, mainText, sub, "ids", "(", ")");
    }

    public static MixedSqlNode valuesNode(Configuration configuration, String mainText, String sub) {
        return mixedSqlNode(configuration, mainText, sub, "list", null, null);
    }

    public static MixedSqlNode mixedSqlNode(Configuration configuration, String mainText, String subText, String list, String open, String close) {
        TextSqlNode head = new TextSqlNode(mainText);
        TextSqlNode include = new TextSqlNode(subText);
        MixedSqlNode contents = new MixedSqlNode(Arrays.asList(include));
        ForEachSqlNode node = new ForEachSqlNode(configuration, contents, list, null, null, "i", open, close, ",");
        return new MixedSqlNode(Arrays.asList(head, node));
    }
}
