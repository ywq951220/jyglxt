package com.csxy.core.common.mybatisExtend;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class LogicDeleteProvider extends MapperTemplate {

	public LogicDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String logicDelete(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(" set flag = 0 ");
		sql.append(SqlHelper.wherePKColumns(entityClass));
		return sql.toString();
	}
}