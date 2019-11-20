package com.waterelephant.utils.mapper.provider;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SelectKeyHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.MetaObjectUtil;

public class BwMapperProvider extends MapperTemplate {
	//表名后缀
	public static final String TABLE_SUFFIX = "_new";
	
	public BwMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

//	/**
//     * 查询
//     *
//     * @param ms
//     * @return
//     */
//    public SqlNode selectNewTabByExample(MappedStatement ms) {
//        
//    	Class<?> entityClass = getSelectReturnType(ms);
//
////    	String tableName = tableName(entityClass);
////    	
////    	String newTableName = tableName + TABLE_SUFFIX;
////    	
////    	SqlNode sqlNode = selectByExample(ms);
////    	
////    	new MixedSqlNode(null).apply(context)
//    	
//        //将返回值修改为实体类型
//        setResultType(ms, entityClass);
//        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
//        //静态的sql部分:select column ... from table
//        sqlNodes.add(new StaticTextSqlNode("SELECT"));
//        IfSqlNode distinctSqlNode = new IfSqlNode(new StaticTextSqlNode("DISTINCT"), "distinct");
//        sqlNodes.add(distinctSqlNode);
//        sqlNodes.add(new StaticTextSqlNode(EntityHelper.getSelectColumns(entityClass) + " FROM " + tableName(entityClass) + TABLE_SUFFIX));
//        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
//        sqlNodes.add(ifNullSqlNode);
//        IfSqlNode orderByClauseSqlNode = new IfSqlNode(new TextSqlNode("order by ${orderByClause}"), "orderByClause != null");
//        sqlNodes.add(orderByClauseSqlNode);
//        String orderByClause = EntityHelper.getOrderByClause(entityClass);
//        if (orderByClause.length() > 0) {
//            IfSqlNode defaultOrderByClauseSqlNode = new IfSqlNode(new StaticTextSqlNode("ORDER BY " + orderByClause), "orderByClause == null");
//            sqlNodes.add(defaultOrderByClauseSqlNode);
//        }
//        
//        return new MixedSqlNode(sqlNodes);
//    }
	
	 /**
     * 根据Example查询
     *
     * @param ms
     * @return
     */
    public String selectNewTabByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append("<if test=\"distinct\">distinct</if>");
        //支持查询指定列
        sql.append(SqlHelper.exampleSelectColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass) + TABLE_SUFFIX));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleOrderBy(entityClass));
        sql.append(SqlHelper.exampleForUpdate());
        return sql.toString();
    }
    
//    /**
//     * 插入全部
//     *
//     * @param ms
//     * @return
//     */
//    public SqlNode insertNewTab(MappedStatement ms) {
//    	Class<?> entityClass = getSelectReturnType(ms);
//        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
//        //insert into table
//        sqlNodes.add(new StaticTextSqlNode("INSERT INTO " + tableName(entityClass) + TABLE_SUFFIX));
//        //获取全部列
//        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
//        //Identity列只能有一个
//        Boolean hasIdentityKey = false;
//        //处理所有的主键策略
//        for (EntityHelper.EntityColumn column : columnList) {
//            //序列的情况，直接写入sql中，不需要额外的获取值
//            if (column.getSequenceName() != null && column.getSequenceName().length() > 0) {
//            } else if (column.isIdentity()) {
//                //这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
//                //这是一个bind节点
//                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_cache", column.getProperty()));
//                //如果是Identity列，就需要插入selectKey
//                //如果已经存在Identity列，抛出异常
//                if (hasIdentityKey) {
//                    //jdbc类型只需要添加一次
//                    if (column.getGenerator() != null && column.getGenerator().equals("JDBC")) {
//                        continue;
//                    }
//                    throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
//                }
//                //插入selectKey
//                newSelectKeyMappedStatement(ms, column);
//                hasIdentityKey = true;
//            } else if (column.isUuid()) {
//                //uuid的情况，直接插入bind节点
//                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_bind", getUUID()));
//            }
//        }
//        //插入全部的(列名,列名...)
//        sqlNodes.add(new StaticTextSqlNode("(" + EntityHelper.getAllColumns(entityClass) + ")"));
//        List<SqlNode> ifNodes = new LinkedList<SqlNode>();
//        //处理所有的values(属性值,属性值...)
//        for (EntityHelper.EntityColumn column : columnList) {
//            //优先使用传入的属性值,当原属性property!=null时，用原属性
//            //自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
//            if (column.isIdentity()) {
//                ifNodes.add(getIfCacheNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_cache },")));
//            } else {
//                //其他情况值仍然存在原property中
//                ifNodes.add(getIfNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "},")));
//            }
//            //当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
//            //序列的情况
//            if (column.getSequenceName() != null && column.getSequenceName().length() > 0) {
//                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(getSeqNextVal(column) + " ,")));
//            } else if (column.isIdentity()) {
//                ifNodes.add(getIfCacheIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + " },")));
//            } else if (column.isUuid()) {
//                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_bind },")));
//            } else {
//                //当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
//                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + ",jdbcType=VARCHAR},")));
//            }
//        }
//        //values(#{property},#{property}...)
//        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "VALUES (", null, ")", ","));
//        return new MixedSqlNode(sqlNodes);
//    }
    
    
    public String insertNewTab(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        EntityColumn logicDeleteColumn = SqlHelper.getLogicDeleteColumn(entityClass);
        processKey(sql, entityClass, ms, columnList);
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass) + TABLE_SUFFIX));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (logicDeleteColumn != null && logicDeleteColumn == column) {
                sql.append(SqlHelper.getLogicDeletedValue(column, false)).append(",");
                continue;
            }
            //优先使用传入的属性值,当原属性property!=null时，用原属性
            //自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
            if (column.isIdentity()) {
                sql.append(SqlHelper.getIfCacheNotNull(column, column.getColumnHolder(null, "_cache", ",")));
            } else {
                //其他情况值仍然存在原property中
                sql.append(SqlHelper.getIfNotNull(column, column.getColumnHolder(null, null, ","), isNotEmpty()));
            }
            //当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
            if (column.isIdentity()) {
                sql.append(SqlHelper.getIfCacheIsNull(column, column.getColumnHolder() + ","));
            } else {
                //当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
                sql.append(SqlHelper.getIfIsNull(column, column.getColumnHolder(null, null, ","), isNotEmpty()));
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }
    
    private void processKey(StringBuilder sql, Class<?> entityClass, MappedStatement ms, Set<EntityColumn> columnList){
        //Identity列只能有一个
        Boolean hasIdentityKey = false;
        //先处理cache或bind节点
        for (EntityColumn column : columnList) {
            if (column.isIdentity()) {
                //这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
                //这是一个bind节点
                sql.append(SqlHelper.getBindCache(column));
                //如果是Identity列，就需要插入selectKey
                //如果已经存在Identity列，抛出异常
                if (hasIdentityKey) {
                    //jdbc类型只需要添加一次
                    if (column.getGenerator() != null && "JDBC".equals(column.getGenerator())) {
                        continue;
                    }
                    throw new MapperException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }
                //插入selectKey
                SelectKeyHelper.newSelectKeyMappedStatement(ms, column, entityClass, isBEFORE(), getIDENTITY(column));
                hasIdentityKey = true;
            } else if(column.getGenIdClass() != null){
                sql.append("<bind name=\"").append(column.getColumn()).append("GenIdBind\" value=\"@tk.mybatis.mapper.genid.GenIdUtil@genId(");
                sql.append("_parameter").append(", '").append(column.getProperty()).append("'");
                sql.append(", @").append(column.getGenIdClass().getCanonicalName()).append("@class");
                sql.append(", '").append(tableName(entityClass) + TABLE_SUFFIX).append("'");
                sql.append(", '").append(column.getColumn()).append("')");
                sql.append("\"/>");
            }

        }
    }

//    /**
//     * 通过条件删除
//     *
//     * @param ms
//     * @return
//     */
//    public SqlNode deleteNewTab(MappedStatement ms) {
//        Class<?> entityClass = getSelectReturnType(ms);
//        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
//        //delete from table
//        sqlNodes.add(new StaticTextSqlNode("DELETE FROM " + tableName(entityClass) + TABLE_SUFFIX));
//        //where/if判断条件
//        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
//        return new MixedSqlNode(sqlNodes);
//    }
    
    
    /**
     * 通过条件删除
     *
     * @param ms
     * @return
     */
    public String deleteNewTab(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        //如果设置了安全删除，就不允许执行不带查询条件的 delete 方法
        if (getConfig().isSafeDelete()) {
            sql.append(SqlHelper.notAllNullParameterCheck("_parameter", EntityHelper.getColumns(entityClass)));
        }
        // 如果是逻辑删除，则修改为更新表，修改逻辑删除字段的值
        if (SqlHelper.hasLogicDeleteColumn(entityClass)) {
            sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass) + TABLE_SUFFIX));
            sql.append("<set>");
            sql.append(SqlHelper.logicDeleteColumnEqualsValue(entityClass, true));
            sql.append("</set>");
            MetaObjectUtil.forObject(ms).setValue("sqlCommandType", SqlCommandType.UPDATE);
        } else {
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass) + TABLE_SUFFIX));
        }
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

//    /**
//     * 通过主键删除
//     *
//     * @param ms
//     */
//    public void deleteNewTabByPrimaryKey(MappedStatement ms) {
//        final Class<?> entityClass = getSelectReturnType(ms);
//        List<ParameterMapping> parameterMappings = getPrimaryKeyParameterMappings(ms);
//        //开始拼sql
//        String sql = new SQL() {{
//            //delete from table
//            DELETE_FROM(tableName(entityClass) + TABLE_SUFFIX );
//            //where 主键=#{property} 条件
//            WHERE(EntityHelper.getPrimaryKeyWhere(entityClass));
//        }}.toString();
//        //静态SqlSource
//        StaticSqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, parameterMappings);
//        
//        //替换原有的SqlSource
//        setSqlSource(ms, sqlSource);
//    }
    
    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String deleteNewTabByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (SqlHelper.hasLogicDeleteColumn(entityClass)) {
            sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)+ TABLE_SUFFIX ));
            sql.append("<set>");
            sql.append(SqlHelper.logicDeleteColumnEqualsValue(entityClass, true));
            sql.append("</set>");
            MetaObjectUtil.forObject(ms).setValue("sqlCommandType", SqlCommandType.UPDATE);
        } else {
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass) + TABLE_SUFFIX));
        }
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }
    
//    /**
//     * 根据Example删除
//     *
//     * @param ms
//     * @return
//     */
//    public SqlNode deleteNewTabByExample(MappedStatement ms) {
//        Class<?> entityClass = getSelectReturnType(ms);
//
//        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
//        //静态的sql部分:select column ... from table
//        sqlNodes.add(new StaticTextSqlNode("DELETE FROM " + tableName(entityClass) + TABLE_SUFFIX));
//        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
//        sqlNodes.add(ifNullSqlNode);
//        return new MixedSqlNode(sqlNodes);
//    }
    
    /**
     * 根据Example删除
     *
     * @param ms
     * @return
     */
    public String deleteNewTabByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        //如果设置了安全删除，就不允许执行不带查询条件的 delete 方法
        if (getConfig().isSafeDelete()) {
            sql.append(SqlHelper.exampleHasAtLeastOneCriteriaCheck("_parameter"));
        }
        if (SqlHelper.hasLogicDeleteColumn(entityClass)) {
            sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)+ TABLE_SUFFIX));
            sql.append("<set>");
            sql.append(SqlHelper.logicDeleteColumnEqualsValue(entityClass, true));
            sql.append("</set>");
            MetaObjectUtil.forObject(ms).setValue("sqlCommandType", SqlCommandType.UPDATE);
        } else {
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)+ TABLE_SUFFIX));
        }
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }
}
