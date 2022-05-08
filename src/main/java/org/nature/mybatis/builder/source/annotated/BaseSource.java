package org.nature.mybatis.builder.source.annotated;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.nature.mybatis.annotation.Model;
import org.nature.mybatis.annotation.TableModel;
import org.nature.mybatis.builder.model.Mapping;
import org.nature.mybatis.builder.source.Source;
import org.nature.mybatis.builder.util.MappingUtil;

import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseSource implements Source {

    @Override
    public void addSource(Configuration config, Class<?> mapper, Method method) {
        String name = mapper.getName() + "." + method.getName();
        TableModel tableModel = mapper.getAnnotation(TableModel.class);
        if (tableModel == null) {
            throw new BindingException(String.format("class %s should be marked with TableModel", mapper));
        }
        Class<?> model = tableModel.value();
        Model modelAnnotation = model.getAnnotation(Model.class);
        if (modelAnnotation == null) {
            throw new BindingException(String.format("model class %s should be marked with Model", model));
        }
        String table = modelAnnotation.table();
        if (StringUtils.isBlank(table)) {
            throw new BindingException("table should not be blank");
        }
        String where = this.textWhere(method);
        String in = this.textIn(method);
        List<Mapping> mappings = MappingUtil.list(model);
        SqlSource sqlSource = this.sqlSource(config, table, where, in, mappings);
        this.addStatement(config, name, model, mappings, sqlSource);
    }

    protected abstract void addStatement(Configuration config, String name, Class<?> model, List<Mapping> mappings, SqlSource sqlSource);

    protected abstract SqlSource sqlSource(Configuration config, String table, String where, String in, List<Mapping> mappings);

    protected abstract String textIn(Method method);

    protected abstract String textWhere(Method method);
}
