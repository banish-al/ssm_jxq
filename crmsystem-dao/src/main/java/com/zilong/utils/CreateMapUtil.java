package com.zilong.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据实体类自动生成对应的mapper和dao层接口
 */
public class CreateMapUtil {


    /**
     * 数据库命名规则
     */
    private enum NamingRules {
        //中间加下户线
        middle_underline,
        //前面和中间都加下划线
        before_underline,
        //使用驼峰命名法
        humpNamed
    }

    // 连接用户名，密码以及连接数据库的名称
    private static String user;
    private static String password;
    private static String url;

    private static String version = "1.5";

    /**
     * 当前工具类的实例池,当通过CreateMapUtil的构造函数创建对象时,会首先在该map下查找是否有对应类型的实例,如果没有,就不需要再重新创建了
     */
    private static Map<String, CreateMapUtil> instancePool = new HashMap<>();

    //该表是否存在
    private boolean hasTable = false;

    /**
     * 命名规则
     */
    public NamingRules namingRules;

    //需要写入的mapper字符串
    private String mapperXml = "";
    //需要写入的接口的字符串
    private String mapperMapping = "";
    //resultMap的名称
    private String resultMapName = "";

    /**包的绝对路径*/
    private static String packageUrl = "";

    /**
     * 默认的xml标签
     * key : id名
     * value : 字符串
     */
    private Map<String, String> xmlMap = new HashMap<>();

    /**
     * 额外的xml标签
     * key : id名
     * value : 字符串
     */
    public Map<String, String> extraXmlMap = new HashMap<>();

    /**
     * 生成类的的包名
     */
    private static String packagePath;

    /**
     * 实体类名
     */
    public String voName;
    /**
     * 表名
     */
    public String tableName;
    /**
     * vo类所有字段
     */
    public Field[] fields;
    /**
     * vo类主键字段
     */
    public String fieldPrimaryKey;
    /**
     * 数据库列名与类型
     */
    public Map<String, String> columns;
    /**
     * 主键名称
     */
    public String primaryKey;
    /**
     * 是否有自增长列
     */
    public boolean autoIncrement = false;
    /**
     * 外键集合
     * key : 外键表名
     * value : ForeignKey对象
     */
    public Map<String, ForeignKey> foreignKeys;

    /**
     * 初始化是否存在异常
     */
    private boolean hasError = false;

    /**
     * 第一次调用构造函数前必须调用该方法
     * 初始化数据库连接属性
     *
     * @param user     用户名
     * @param password 密码
     * @param url      连接url
     * @param driver   driver
     */
    public static void init(String user, String password, String url, String driver, String packagePath) {
        CreateMapUtil.user = user;
        CreateMapUtil.password = password;
        CreateMapUtil.url = url;
        CreateMapUtil.packagePath = packagePath;
        try {
            Class.forName(driver);
            String dirUrl = CreateMapUtil.class.getResource("").toURI().getPath();
            packageUrl = dirUrl.substring(1, dirUrl.indexOf("/target/classes/")) + "/src/main/java/" + packagePath.replaceAll("\\.", "/") + "/";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 指定要生成mapper的的实体类
     */
    public Class t;

    private CreateMapUtil() {
    }

    private CreateMapUtil(Class classType) {
        t = classType;
        //需要写入的mapper字符串
        mapperXml = "";
        //需要写入的接口的字符串
        mapperMapping = "";
        //实体类名
        voName = getSuffix(t.getName());
        //resultMap名称
        resultMapName = voName + "map";
        try {
            //如果不存在该表,就不继续执行
            hasTable = existTable(voName);
            if (!hasTable) return;
            //vo类所有字段
            fields = t.getDeclaredFields();
            //数据库列名与类型
            columns = getColumnNames(tableName);
            //检测键的命名类型
            namingRules = getNamingRules();
            //主键名称
            primaryKey = getTablePrimaryKey(tableName);
            //获取vo类主键字段
            if (primaryKey != null)
                for (Field i : fields) {
                    if (primaryKey.equals(getColumnName(i.getName()))) {
                        fieldPrimaryKey = i.getName();
                        break;
                    }
                }
            //获取外键
            foreignKeys = getForeignKeys(tableName);
            System.out.println("foreignKeys = " + foreignKeys);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 通过该方法获取该工具类的实例
     * 如果vo类型在数据库表中不存在,则返回一个null
     * 如果重复添加,则返回相同的对象
     *
     * @param classType 类的class对象
     * @return
     */
    public static CreateMapUtil addClass(Class classType) {
        if (classType == null) return null;
        CreateMapUtil inst = instancePool.get(classType.getName());
        if (inst == null) {
            inst = new CreateMapUtil(classType);
            //如果不存在该表格
            if (!inst.hasTable) return null;
            instancePool.put(classType.getName(), inst);
            System.out.println("new CreateMapUtil , voName = " + inst.voName + "\tclassType = " + classType.getName() + "\tinstanceSize() = " + instancePool.size());
            inst.hasError = inst.startAssociated();
        }
        return inst;
    }

    /**
     * 该方法将检测指定类是否已经添加
     *
     * @param classType 类的class对象
     */
    public static boolean existClass(Class classType) {
        if (classType == null) return false;
        return instancePool.get(classType.getName()) != null;
    }

    /**
     * 内部调用生成方法
     *
     * @return 是否存在异常
     */
    private boolean startAssociated() {
        try {
            //******************************拼接mapper

            //拼接头文件
            if (xmlMap.get("head") == null) {
                String head = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<!DOCTYPE mapper\n" +
                        "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                        "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                        "<mapper namespace=\"" + packagePath + "." + voName + "Mapping\">\n";
                xmlMap.put("head", head);
            }

            //拼接resultMap映射
            if (xmlMap.get("resultMap") == null) {
                String resultMap = "\n    <!-- resultMap映射 -->" +
                        "\n    <resultMap id=\"%s\" type=\"%s\">\n" +
                        "        <id column=\"%s\" property=\"%s\"/>";
                resultMap = String.format(resultMap,
                        resultMapName,
                        voName,
                        primaryKey,
                        fieldPrimaryKey);
                String resultTemp = "", associationTemp = "", collectionTemp = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {   //是数据库兼容的类型
                        if (!temp.equals(primaryKey))
                            resultTemp += "\n        <result column=\"" + temp + "\" property=\"" + i.getName() + "\"/>";
                    } else {
                        String type = getSuffix(i.getType().getName()).toLowerCase();
                        if ("list".equals(type)) {    //是集合类
                            CreateMapUtil other = null;
                            Class<?> actualTypeArgument = getListActualType(i);
                            //if(!CreateMapUtil.existClass(actualTypeArgument))
                            other = CreateMapUtil.addClass(actualTypeArgument);
                            if (other != null) {
                                // 外键表名指向当前类
                                ForeignKey foreignKey = other.foreignKeys.get(tableName.toLowerCase());
                                //if(foreignKey == null) foreignKey = other.foreignKeys.get(getName(tableName).toLowerCase());
                                if (foreignKey != null) {
                                    System.out.println("collection关联 : voName = " + voName + "\tother.voName = " + other.voName + "\tfieldName = " + i.getName() + "\tforeignKey = " + foreignKey);
                                    System.out.println("生成关联 : " + i.getName() + " -- " + foreignKey.fkColumn);
                                    String idName = "collection" + other.voName + "ListBy" + foreignKey.fkColumn + "DefaultMap";
                                    String mapName = other.resultMapName;
                                    //集合关联
                                    collectionTemp += "\n        <collection property=\"" + i.getName() + "\" javaType=\"list\" ofType=\"" + other.t.getName() + "\"\n" +
                                            "           column=\"{fk=" + foreignKey.pkColumn + "}\" select=\"" + packagePath + "." + other.voName + "Mapping." + idName + "\" fetchType=\"lazy\"/>";

                                    String getList = "\n    <!-- 关联查询,调用者:\"%sMapping\" -->\n" +
                                            "    <select id=\"%s\" resultMap=\"%s\">\n" +
                                            "       select %s from %s where %s = #{%s};\n" +
                                            "    </select>\n";
                                    getList = String.format(getList,
                                            packagePath + "." + voName,
                                            idName,
                                            mapName,
                                            other.getColumnKeyJoin(","),
                                            other.tableName,
                                            foreignKey.fkColumn,
                                            "fk");
                                    other.extraXmlMap.put(idName, getList);
                                }
                            }
                        } else { //不是集合
                            CreateMapUtil other = CreateMapUtil.addClass(i.getType());
                            if (other != null) {
                                ForeignKey foreignKey = foreignKeys.get(getName(other.tableName).toLowerCase());
                                if (foreignKey == null)
                                    foreignKey = other.foreignKeys.get(getName(tableName).toLowerCase());
                                System.out.println("association关联 : voName = " + voName + "\tother.voName = " + other.voName + "\tfieldName = " + i.getName() + "\tforeignKey = " + foreignKey);
                                if (foreignKey != null) {
                                    if (getName(other.voName).toLowerCase().equalsIgnoreCase(foreignKey.otherTable)) {   //正向association关联
                                        System.out.println("生成关联 : " + i.getName() + " -- " + foreignKey.fkColumn + "\t已屏蔽关联查询");
                                        String idName = "association" + other.voName + "By" + foreignKey.fkColumn;
                                        String mapName = "basic" + other.voName + "map";
                                        //单数据关联
                                        associationTemp += "\n        <association property=\"" + i.getName() + "\" column=\"{fk=" + foreignKey.fkColumn + "}\"\n" +
                                                "           select=\"" + packagePath + "." + other.voName + "Mapping." + idName + "\"" +
                                                "  javaType=\"" + other.voName + "\" fetchType=\"lazy\"/>";
                                        String get = "\n    <!-- 关联查询,调用者:\"%sMapping\" -->\n" +
                                                "    <select id=\"%s\" resultMap=\"%s\">\n" +
                                                "       select %s from %s where %s = #{%s};\n" +
                                                "    </select>\n";
                                        get = String.format(get,
                                                packagePath + "." + voName,
                                                idName,
                                                mapName,
                                                other.getColumnKeyJoin(","),
                                                other.tableName,
                                                foreignKey.pkColumn,
                                                "fk");
                                        other.extraXmlMap.put(idName, get);
                                        String mapTemp = "\n    <resultMap id=\"%s\" type=\"%s\">\n" +
                                                "        <id column=\"%s\" property=\"%s\"/>";
                                        mapTemp = String.format(mapTemp,
                                                mapName,
                                                other.voName,
                                                other.primaryKey,
                                                other.fieldPrimaryKey);
                                        String resultTempTemp = "";
                                        for (Field j : other.fields) {
                                            j.setAccessible(true);
                                            String columnTemp = other.getColumnName(j.getName());
                                            if (columnTemp != null) {   //是数据库兼容的类型
                                                if (!columnTemp.equalsIgnoreCase(other.primaryKey))
                                                    resultTempTemp += "\n        <result column=\"" + columnTemp + "\" property=\"" + j.getName() + "\"/>";
                                            }
                                        }
                                        other.extraXmlMap.put(mapName, mapTemp + resultTempTemp + "\n    </resultMap>\n");
                                    } else if (getName(voName).toLowerCase().equals(foreignKey.otherTable)) {   //反向association关联
                                        System.out.println("生成反向关联 : " + i.getName() + " -- " + foreignKey.fkColumn);
                                        String idName = "association" + other.voName + "By" + foreignKey.fkColumn;
                                        String mapName = other.resultMapName;
                                        //单数据关联
                                        associationTemp += "\n        <association property=\"" + i.getName() + "\" column=\"{pk=" + foreignKey.pkColumn + "}\"\n" +
                                                "           select=\"" + packagePath + "." + other.voName + "Mapping." + idName + "\"" +
                                                "  javaType=\"" + other.voName + "\" fetchType=\"lazy\"/>";
                                        String get = "\n    <!-- 关联查询,调用者:\"%sMapping\" -->\n" +
                                                "    <select id=\"%s\" resultMap=\"%s\">\n" +
                                                "       select %s from %s where %s = #{%s} limit 0,1;\n" +
                                                "    </select>\n";
                                        get = String.format(get,
                                                packagePath + "." + voName,
                                                idName,
                                                mapName,
                                                other.getColumnKeyJoin(","),
                                                other.tableName,
                                                foreignKey.fkColumn,
                                                "pk");
                                        other.extraXmlMap.put(idName, get);
                                    }
                                }
                            }
                        }
                    }
                }
                resultMap += resultTemp + associationTemp + collectionTemp + "\n    </resultMap>\n";
                xmlMap.put("resultMap", resultMap);
            }

            //拼接查询所有方法
            if (xmlMap.get("queryAll") == null) {
                String queryAll = "\n    <!-- 查询所有方法 -->" +
                        "\n    <select id=\"queryAll\" resultMap=\"%s\">\n" +
                        "       select %s from %s;\n" +
                        "    </select>\n";
                queryAll = String.format(queryAll,
                        resultMapName,
                        getColumnKeyJoin(","),
                        tableName);
                xmlMap.put("queryAll", queryAll);
            }

            //拼接查询id方法
            if (xmlMap.get("queryById") == null) {
                String queryById = "\n    <!-- 根据主键id查询数据方法 -->" +
                        "\n    <select id=\"queryById\" parameterType=\"%s\" resultMap=\"%s\">\n" +
                        "       select %s from %s where %s = #{" + fieldPrimaryKey + "};\n" +
                        "    </select>\n";
                queryById = String.format(queryById,
                        columns.get(primaryKey),
                        resultMapName,
                        getColumnKeyJoin(","),
                        tableName,
                        primaryKey);
                xmlMap.put("queryById", queryById);
            }

            //拼接query方法(条件查询)
            if (xmlMap.get("query") == null) {
                String query = "\n    <!-- 根据" + voName + "条件查询数据方法 -->" +
                        "\n    <select id=\"query\" resultMap=\"" + resultMapName + "\">\n" +
                        "        select " + getColumnKeyJoin(",") + " \n        from " + tableName;
                String temp5 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp5 += "\n            <if test=\"" + i.getName() +
                                " != null\"> and " + temp + " = #{" + i.getName() + "}</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp5 += "\n            <if test=\"" + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " = #{" + i.getName() + "." + mapUtil.primaryKey + "}</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                query += " \n        <where>" + temp5 + " \n        </where>\n    </select>\n";
                xmlMap.put("query", query);
            }

            //拼接queryLike方法(条件模糊查询)
            if (xmlMap.get("queryLike") == null) {
                String queryLike = "\n    <!-- 根据" + voName + "条件模糊查询数据方法 -->" +
                        "\n    <select id=\"queryLike\" resultMap=\"" + resultMapName + "\">\n" +
                        "        select " + getColumnKeyJoin(",") + " \n        from " + tableName;
                String temp4 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp4 += "\n            <if test=\"" + i.getName() +
                                " != null\"> and " + temp + " like concat('%',#{" + i.getName() + "},'%')</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp4 += "\n            <if test=\"" + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " like concat('%',#{" + i.getName() + "." + mapUtil.primaryKey + "},'%')</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                queryLike += " \n        <where>" + temp4 + " \n        </where>\n    </select>\n";
                xmlMap.put("queryLike", queryLike);
            }

            //拼接queryLimit方法,根据条件查询,限定显示条数
            if (xmlMap.get("queryLimit") == null) {
                String queryLimit = "\n    <!-- 根据" + voName + "条件查询数据方法,限制显示条数 -->" +
                        "\n    <select id=\"queryLimit\" resultMap=\"" + resultMapName + "\">\n" +
                        "        select " + getColumnKeyJoin(",") + " \n        from " + tableName;
                String temp5 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp5 += "\n            <if test=\"" + voName + "." + i.getName() +
                                " != null\"> and " + temp + " = #{" + voName + "." + i.getName() + "}</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp5 += "\n            <if test=\"" + voName + "." + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + voName + "." + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " = #{" + voName + "." + i.getName() + "." + mapUtil.primaryKey + "}</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                queryLimit += " \n        <where>" + temp5 + " \n        </where>";
                queryLimit += "\n        limit #{startIndex},#{pageSize};\n" +
                        "    </select>\n";
                xmlMap.put("queryLimit", queryLimit);
            }

            //拼接queryLikeLimit方法,根据条件模糊查询,限定显示条数
            if (xmlMap.get("queryLikeLimit") == null) {
                String queryLikeLimit = "\n    <!-- 根据" + voName + "条件模糊查询数据方法,限制显示条数 -->" +
                        "\n    <select id=\"queryLikeLimit\" resultMap=\"" + resultMapName + "\">\n" +
                        "        select " + getColumnKeyJoin(",") + " \n        from " + tableName;
                String temp4 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp4 += "\n            <if test=\"" + voName + "." + i.getName() +
                                " != null\"> and " + temp + " like concat('%',#{" + voName + "." + i.getName() + "},'%')</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp4 += "\n            <if test=\"" + voName + "." + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + voName + "." + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " like concat('%',#{" + voName + "." + i.getName() + "." + mapUtil.primaryKey + "},'%')</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                queryLikeLimit += " \n        <where>" + temp4 + " \n        </where>";
                queryLikeLimit += "\n        limit #{startIndex},#{pageSize};\n" +
                        "    </select>\n";
                xmlMap.put("queryLikeLimit", queryLikeLimit);
            }

            //拼接查询条数方法
            if (xmlMap.get("queryCount") == null) {
                String queryCount = "\n    <!-- 根据" + voName + "条件获取数据总条数方法 -->" +
                        "\n    <select id=\"queryCount\" resultType=\"int\">\n" +
                        "        select count(1) from " + tableName;
                String temp6 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp6 += "\n            <if test=\"" + i.getName() +
                                " != null\"> and " + temp + " = #{" + i.getName() + "}</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp6 += "\n            <if test=\"" + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " = #{" + i.getName() + "." + mapUtil.primaryKey + "}</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                queryCount += " \n        <where>" + temp6 + " \n        </where>\n" +
                        "    </select>\n";
                xmlMap.put("queryCount", queryCount);
            }

            //条件模糊查询记录条数
            if (xmlMap.get("queryLikeCount") == null) {
                String queryLikeCount = "\n    <!-- 根据" + voName + "模糊查询数据总条数方法 -->" +
                        "\n    <select id=\"queryLikeCount\" resultType=\"int\">\n" +
                        "        select count(1) from " + tableName;
                String temp4 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp4 += "\n            <if test=\"" + i.getName() +
                                " != null\"> and " + temp + " like concat('%',#{" + i.getName() + "},'%')</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp4 += "\n            <if test=\"" + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " like concat('%',#{" + i.getName() + "." + mapUtil.primaryKey + "},'%')</if>" +
                                        "\n            </if>";
                            }
                        }
                    }
                }
                queryLikeCount += " \n        <where>" + temp4 + " \n        </where>\n    </select>\n";
                xmlMap.put("queryLikeCount", queryLikeCount);
            }

            //拼接添加方法
            if (xmlMap.get("insert") == null) {
                String insert = "\n    <!-- 根据" + voName + "插入数据方法 -->" +
                        "\n    <insert id=\"insert\" parameterType=\"%s\" useGeneratedKeys=\"%b\" keyColumn=\"%s\" keyProperty=\"%s\">\n" +
                        "        insert into %s (";
                insert = String.format(insert,
                        voName,
                        autoIncrement,
                        primaryKey,
                        fieldPrimaryKey,
                        tableName);
                String temp1 = "", temp2 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp1 += temp + ",";
                        temp2 += "#{" + i.getName() + "},";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                if (!temp1.contains(foreignKey.fkColumn)) {
                                    temp1 += foreignKey.fkColumn + ",";
                                    temp2 += "#{" + i.getName() + "." + getName(foreignKey.pkColumn) + "},";
                                }
                            }
                        }
                    }
                }
                temp1 = temp1.substring(0, temp1.lastIndexOf(","));
                temp2 = temp2.substring(0, temp2.lastIndexOf(","));
                insert += temp1 + ") \n" + "        values (" + temp2;
                insert = insert + "); \n" + "    </insert>\n";
                xmlMap.put("insert", insert);
            }

            //拼接修改方法
            if (xmlMap.get("updateById") == null) {
                String updateById = "\n    <!-- 根据" + voName + "条件修改单条数据方法,从传入对象获取主键id -->" +
                        "\n    <update id=\"updateById\" parameterType=\"" + voName + "\">\n" +
                        "        update " + tableName + " \n        <set>";
                String temp3 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null && !temp.equals(primaryKey)) {
                        temp3 += "\n            <if test=\"" + i.getName() + " != null\">" + temp + " = #{" + i.getName() + "},</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp3 += String.format("\n            <if test=\"%s != null\">\n                <if test=\"%s != null\">%s = #{%s},</if>\n            </if>",
                                        i.getName(),
                                        i.getName() + "." + mapUtil.primaryKey,
                                        foreignKey.fkColumn,
                                        i.getName() + "." + mapUtil.primaryKey);
                            }
                        }
                    }
                }
                updateById += temp3;
                updateById += "\n        </set>\n" +
                        "        where " + primaryKey + " = #{" + fieldPrimaryKey + "}\n" +
                        "    </update>\n";
                xmlMap.put("updateById", updateById);
            }

            //拼接修改方法,根据条件修改多条数据
            if (xmlMap.get("updates") == null) {
                String updates = "\n    <!-- 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件 -->" +
                        "\n    <update id=\"updates\">\n" +
                        "        update " + tableName + " \n        <set>";
                String temp3 = "", temp4 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        if (!temp.equals(primaryKey))
                            temp3 += "\n            <if test=\"SetValue." + i.getName() + " != null\">" + temp + " = #{SetValue." + i.getName() + "},</if>";
                        temp4 += "\n                <if test=\"Condition." + i.getName() +
                                " != null\"> and " + temp + " = #{Condition." + i.getName() + "}</if>";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp3 += String.format("\n            <if test=\"%s != null\">\n                <if test=\"%s != null\">%s = #{%s},</if>\n            </if>",
                                        "SetValue." + i.getName(),
                                        "SetValue." + i.getName() + "." + mapUtil.primaryKey,
                                        foreignKey.fkColumn,
                                        "SetValue." + i.getName() + "." + mapUtil.primaryKey);
                                temp4 += "\n                <if test=\"Condition." + i.getName() + " != null\">" +
                                        "\n                    <if test=\"Condition." + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " = #{Condition." + i.getName() + "." + mapUtil.primaryKey + "}</if>" +
                                        "\n                </if>";
                            }
                        }
                    }
                }
                updates += temp3;
                updates += "\n        </set>\n" +
                        "        <where>\n            <if test=\"Condition != null\">" + temp4 +
                        "\n            </if>\n        </where>\n " +
                        "\n    </update>\n";
                xmlMap.put("updates", updates);
            }

            //拼接删除方法
            if (xmlMap.get("deleteById") == null) {
                String deleteById = "\n    <!-- 根据" + voName + "条件删除单条数据方法,从传入对象获取id -->" +
                        "\n    <delete id=\"deleteById\" parameterType=\"" + columns.get(primaryKey) + "\">\n" +
                        "        delete from " + tableName + " where " + primaryKey + " = #{" + fieldPrimaryKey + "};\n" +
                        "    </delete>\n";
                xmlMap.put("deleteById", deleteById);
            }

            //根据条件删除数据
            if (xmlMap.get("deletes") == null) {
                String deletes = "\n    <!-- 根据" + voName + "条件修改多条数据方法,从传入对象获取删除条件 -->" +
                        "\n    <delete id=\"deletes\">\n" +
                        "        delete from " + tableName;
                String temp6 = "", temp7 = "";
                for (Field i : fields) {
                    i.setAccessible(true);
                    String temp = getColumnName(i.getName());
                    if (temp != null) {
                        temp6 += "\n            <if test=\"" + i.getName() +
                                " != null\"> and " + temp + " = #{" + i.getName() + "}</if>";
                        temp7 += i.getName() + " == null and ";
                    } else {
                        CreateMapUtil mapUtil = CreateMapUtil.addClass(i.getType());
                        if (mapUtil != null) {
                            ForeignKey foreignKey = foreignKeys.get(getName(mapUtil.tableName).toLowerCase());
                            if (foreignKey != null) {
                                temp6 += "\n            <if test=\"" + i.getName() + " != null\">" +
                                        "\n                 <if test=\"" + i.getName() + "." + mapUtil.primaryKey +
                                        " != null\"> and " + foreignKey.fkColumn +
                                        " = #{" + i.getName() + "." + mapUtil.primaryKey + "}</if>" +
                                        "\n            </if>";
                                temp7 += i.getName() + " == null and ";
                            }
                        }
                    }
                }
                temp7 = temp7.substring(0, temp7.lastIndexOf("and "));
                deletes += " \n        <where>" +
                        "\n            <if test=\"" + temp7 + "\"> 1 = 2</if>" +
                        temp6 + " \n        </where>\n    </delete>\n";
                xmlMap.put("deletes", deletes);
            }

            //拼接尾部文件
            if (xmlMap.get("end") == null) {
                xmlMap.put("end", "\n</mapper>");
            }

            return false;
        } catch (Exception e) {
            System.err.println("\n-----------------------------------");
            System.err.println("Error voName = " + voName);
            System.err.println("-----------------------------------\n");
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 拼接mapperXml
     *
     * @return
     */
    public String jointXML() {
        //自定义代码段
        String customStr = null;
        File xmlFile = new File(packageUrl + voName + "Mapping.xml");
        try {
            if (xmlFile.exists()) {
                String tempStr = "";
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(xmlFile));
                while ((line = bufferedReader.readLine()) != null) {
                    tempStr += line + "\n";
                }
                int startIndex = tempStr.indexOf("<!-- custom -->");
                int endIndex = tempStr.indexOf("<!-- /custom -->");
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
                    customStr = tempStr.substring(startIndex, endIndex + "<!-- /custom -->".length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapperXml = "";
        mapperXml += xmlMap.get("head") + "\n";
        if (customStr == null)
            mapperXml += "<!-- custom -->\n    <!-- 在这里写你的自定义代码,每次生成.xml文件都会保留这一段代码 -->\n\n\n<!-- /custom -->";
        else mapperXml += customStr;
        mapperXml += "\n\n" + xmlMap.get("resultMap");
        mapperXml += xmlMap.get("queryAll");
        mapperXml += xmlMap.get("queryById");
        mapperXml += xmlMap.get("query");
        mapperXml += xmlMap.get("queryLike");
        mapperXml += xmlMap.get("queryLimit");
        mapperXml += xmlMap.get("queryLikeLimit");
        mapperXml += xmlMap.get("queryCount");
        mapperXml += xmlMap.get("queryLikeCount");
        mapperXml += xmlMap.get("insert");
        mapperXml += xmlMap.get("updateById");
        mapperXml += xmlMap.get("updates");
        mapperXml += xmlMap.get("deleteById");
        mapperXml += xmlMap.get("deletes");
        Set<String> set = extraXmlMap.keySet();
        for (String i : set) {
            mapperXml += extraXmlMap.get(i) + "\n";
        }
        mapperXml += xmlMap.get("end");
        return mapperXml;
    }

    /**
     * 拼接mapper层接口
     *
     * @return
     */
    public String jointMapping() {
        //自定义代码段
        String customImportStr = null;
        String customMethodsStr = null;
        File classFile = new File(packageUrl + voName + "Mapping.java");
        try {
            if (classFile.exists()) {
                String tempStr = "";
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(classFile));
                while ((line = bufferedReader.readLine()) != null) {
                    tempStr += line + "\n";
                }
                int startIndex = tempStr.indexOf("//** custom import **//");
                int endIndex = tempStr.indexOf("//** custom import **//");
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
                    customImportStr = tempStr.substring(startIndex, endIndex + "//** custom import **//".length());
                startIndex = tempStr.indexOf("//** custom methods **//");
                endIndex = tempStr.indexOf("//** /custom methods **//");
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
                    customMethodsStr = tempStr.substring(startIndex, endIndex + "//** /custom methods **//".length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapperMapping = "package " + packagePath + ";\n" +
                "\n" +
                "import " + t.getName() + ";\n" +
                "import org.apache.ibatis.annotations.Param;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "import java.util.List;\n\n";
        if (customImportStr == null)
            mapperMapping += "//** custom import **//\n" +
                    "  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码\n" +
                    "\n" +
                    "//** /custom import **//";
        else mapperMapping += customImportStr;
        mapperMapping += "\n\n@Repository\npublic interface " + voName + "Mapping {\n\n";
        if (customMethodsStr == null)
            mapperMapping += "//** custom methods **//\n" +
                    "  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码\n" +
                    "\n\n" +
                    "//** /custom methods **//";
        else mapperMapping += customMethodsStr;
        mapperMapping += "\n\n  /**\n   * 查询所有方法 \n   */";
        mapperMapping += "\n  List<" + voName + "> queryAll();";
        mapperMapping += "\n\n  /**\n   * 根据主键" + primaryKey + "(" + voName + "." + fieldPrimaryKey + ")查询单条数据方法 \n   */";
        mapperMapping += "\n  " + voName + " queryById(" + columns.get(primaryKey) + " id);";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件查询多条数据方法 \n   */";
        mapperMapping += "\n  List<" + voName + "> query(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件模糊查询多条数据方法 \n   */";
        mapperMapping += "\n  List<" + voName + "> queryLike(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 \n   */";
        mapperMapping += "\n  List<" + voName + "> queryLimit(@Param(\"" + voName + "\") " + voName + " " + voName.toLowerCase() + ", @Param(\"startIndex\") int startIndex, @Param(\"pageSize\") int pageSize);";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 \n   */";
        mapperMapping += "\n  List<" + voName + "> queryLikeLimit(@Param(\"" + voName + "\") " + voName + " " + voName.toLowerCase() + ", @Param(\"startIndex\") int startIndex, @Param(\"pageSize\") int pageSize);";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件获取数据总条数方法 \n   */";
        mapperMapping += "\n  int queryCount(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "模糊查询数据总条数方法 \n   */";
        mapperMapping += "\n  int queryLikeCount(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "插入数据方法 \n   */";
        mapperMapping += "\n  int insert(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件修改单条数据方法,从传入对象获取id \n   */";
        mapperMapping += "\n  int updateById(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n\n  /**\n   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 \n   */";
        mapperMapping += "\n  int updates(@Param(\"SetValue\") " + voName + " setValue, @Param(\"Condition\") " + voName + " condition);";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件删除单条数据方法,从传入对象获取id \n   */";
        mapperMapping += "\n  int deleteById(" + columns.get(primaryKey) + " id);";
        mapperMapping += "\n\n  /**\n   * 根据" + voName + "条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 \n   */";
        mapperMapping += "\n  int deletes(" + voName + " " + voName.toLowerCase() + ");";
        mapperMapping += "\n}";
        return mapperMapping;
    }

    /**
     * 写出java类和xml文件
     */
    public void writeFile() {
        try {
            //写出文件
            System.out.println("write url : " + packageUrl);
            File file = new File(packageUrl);
            if (!file.exists()) file.mkdirs();

            File xmlUrl = new File(packageUrl + voName + "Mapping.xml");
            File classUrl = new File(packageUrl + voName + "Mapping.java");
            //写入文件
            FileOutputStream fileOutputStream = new FileOutputStream(xmlUrl);
            fileOutputStream.write(mapperXml.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            FileOutputStream fileOutputStream2 = new FileOutputStream(classUrl);
            fileOutputStream2.write(mapperMapping.getBytes());
            fileOutputStream2.flush();
            fileOutputStream2.close();

            System.out.println("\n\n-------------------------------");
            System.out.println("********************************" + voName + "**********************************\n");
            for (Field i : fields) {
                i.setAccessible(true);
                System.out.println(voName + " field : " + i.getName() + "\tfieldType = " + i.getType());
            }
            System.out.println("-------------------------------");
            System.out.println("All columns : " + columns);
            System.out.println("-------------------------------");
            System.out.println("foreignKeys : " + foreignKeys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将所有类写出到文件
     */
    public static void write() {
        //备份文件
        backupFile(packagePath);
        Set<String> set = instancePool.keySet();
        int num = 0;
        int errorNum = 0;
        for (String i : set) {
            CreateMapUtil temp = instancePool.get(i);
            temp.jointXML();
            temp.jointMapping();
            temp.writeFile();
            num++;
            if (temp.hasError) errorNum++;
        }
        System.out.println("\n\n\n已自动备份写出前\"" + packagePath + "\"包下的所有文件,备份包路径为:\"" + packagePath + ".backup\"");
        System.out.println("\n已写出结果如下:");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\n写出完成!\n共写出" + num + "个对象.");
        System.out.println("包含 : " + (num - errorNum) + "个成功," + errorNum + "个失败!");
        if (errorNum > 0) {
            System.err.println("\n----------------------警告!!!---------------------------");
            System.err.println("写出存在异常!!!!,请检查原因!!!!");
            System.err.println("--------------------------------------------------------");
        }
        System.out.println("\nCreateMapUtil version : " + version);
        System.out.println("方法 : CreateMapUtil.write() 执行结束...");
    }


    /**
     * 备份文件
     *
     * @param packagePath 包路径
     */
    static void backupFile(String packagePath) {
        try {
            //写出文件
            //获取该类的绝对路径
            String dirUrl = CreateMapUtil.class.getResource("").toURI().getPath();
            dirUrl = dirUrl.substring(1, dirUrl.indexOf("/target/classes/")) + "/src/main/java/" + packagePath.replaceAll("\\.", "/");
            copyDir(dirUrl, dirUrl + "/backup");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件夹的拷贝
     */
    private static void copyDir(String sourcePathDir, String newPathDir) {
        File start = new File(sourcePathDir);
        File end = new File(newPathDir);
        String[] filePath = start.list();//获取该文件夹下的所有文件以及目录的名字
        if (!end.exists()) {
            end.mkdir();
        }
        if (filePath != null)
            for (String temp : filePath) {
                if ("backup".equals(temp)) continue;
                //添加满足情况的条件
                if (new File(sourcePathDir + File.separator + temp).isFile() && (temp.endsWith(".xml") || temp.endsWith(".java"))) {
                    //为文件则进行拷贝
                    copyFile(sourcePathDir + File.separator + temp, newPathDir + File.separator + temp);
                }
            }
    }

    /**
     * 文件的拷贝
     */
    private static boolean copyFile(String sourcePath, String newPath) {
        boolean flag = false;
        File readfile = new File(sourcePath);
        File newFile = new File(newPath + ".backup");
        BufferedWriter bufferedWriter = null;
        Writer writer = null;
        FileOutputStream fileOutputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileOutputStream = new FileOutputStream(newFile);
            writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(writer);

            bufferedReader = new BufferedReader(new FileReader(readfile));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            flag = true;
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 根据命名规则返回指定的名称
     *
     * @param name 对象的字段名称
     * @return 规范后的名称
     */
    private String getName(String name) {
        if (namingRules == NamingRules.before_underline) {
            name = name.replaceAll("[A-Z]", "_$0").toLowerCase();
            if (name.charAt(0) != '_')
                return "_" + name;
        } else if (namingRules == NamingRules.middle_underline) {
            name = name.replaceAll("[A-Z]", "_$0").toLowerCase();
            if (name.charAt(0) == '_')
                return name.substring(name.indexOf("_") + 1);
        }
        return name;
    }

    /**
     * 根据命名规则返回指定的名称
     *
     * @param name 对象的字段名称
     * @return 规范后的名称
     */
    private String getName(String name,NamingRules namingRules) {
        if (namingRules == NamingRules.before_underline) {
            name = name.replaceAll("[A-Z]", "_$0").toLowerCase();
            if (name.charAt(0) != '_')
                return "_" + name;
        } else if (namingRules == NamingRules.middle_underline) {
            name = name.replaceAll("[A-Z]", "_$0").toLowerCase();
            if (name.charAt(0) == '_')
                return name.substring(name.indexOf("_") + 1);
        }
        return name;
    }

    /**
     * 下划线转驼峰命名
     *
     * @param str 名称
     * @return 驼峰名称
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取后缀名
     *
     * @return 名称
     */
    private String getSuffix(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }


    /**
     * 拼接
     *
     * @param str
     * @return
     */
    private String getColumnKeyJoin(String str) {
        String key = "";
        Set<String> set = columns.keySet();
        for (String i : set) {
            key += i + str;
        }
        key = key.substring(0, key.length() - str.length());
        return key;
    }


    /**
     * 获取集合的泛型类
     *
     * @param listField
     * @return
     */
    private Class<?> getListActualType(Field listField) {
        Type genericType = listField.getGenericType();
        Class<?> actualTypeArgument = null;
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            // 得到泛型里的class类型对象
            actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
        }
        return actualTypeArgument;
    }

    /**
     * 根据vo字段获取列名
     *
     * @param fieldName
     * @return
     */
    private String getColumnName(String fieldName) {
        fieldName = getName(fieldName);
        return columns.get(fieldName) == null ? null : fieldName;
    }

    /**
     * 检测表的命名规则
     *
     * @return NamingRules枚举
     */
    private NamingRules getNamingRules() {
        NamingRules flag = NamingRules.humpNamed;
        Set<String> keys = columns.keySet();
        for (String i : keys) {
            if (flag == NamingRules.humpNamed && i.contains("_"))
                flag = NamingRules.middle_underline;
            if (flag == NamingRules.middle_underline && i.charAt(0) == '_') {
                return NamingRules.before_underline;
            }
        }
        return flag;
    }

    /**
     * 查询该类是否在数据库存在对应的表格
     *
     * @return bool
     */
    public boolean existTable(String tableName) {
        System.out.println("existTable : tableName = " + getName(tableName));
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rSet = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement("SHOW TABLES LIKE ?;");
            statement.setString(1, getName(tableName,NamingRules.humpNamed));
            rSet = statement.executeQuery();
            if (rSet.next()) {
                this.tableName = getName(tableName,NamingRules.humpNamed);
                return true;
            }
            CreateMapUtil.closeAll(null, statement, rSet);

            statement = connection.prepareStatement("SHOW TABLES LIKE ?;");
            statement.setString(1, getName(tableName,NamingRules.middle_underline));
            rSet = statement.executeQuery();
            if (rSet.next()) {
                this.tableName = getName(tableName,NamingRules.middle_underline);
                return true;
            }
            CreateMapUtil.closeAll(null, statement, rSet);

            statement = connection.prepareStatement("SHOW TABLES LIKE ?;");
            statement.setString(1, getName(tableName,NamingRules.before_underline));
            rSet = statement.executeQuery();
            if (rSet.next()) {
                this.tableName = getName(tableName,NamingRules.before_underline);
                return true;
            }
            CreateMapUtil.closeAll(null, statement, rSet);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            CreateMapUtil.closeAll(connection, statement, rSet);
        }
        return false;
    }

    /**
     * 获取表的外键
     *
     * @param tableName 当前表名
     * @return map集合, 第一项为外键表名, 第二项为外键列名
     */
    private static Map<String, ForeignKey> getForeignKeys(String tableName) throws SQLException {
        Map<String, ForeignKey> map = new HashMap<>();
        Connection connection = DriverManager.getConnection(url, user, password);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getImportedKeys(connection.getCatalog(), null, tableName);
        while (resultSet.next()) {
            String fkColumnName = resultSet.getString("FKCOLUMN_NAME");
            String pkTablenName = resultSet.getString("PKTABLE_NAME").toLowerCase();
            String pkColumnName = resultSet.getString("PKCOLUMN_NAME");
            map.put(pkTablenName, new ForeignKey(pkTablenName, fkColumnName, pkColumnName));
        }
        closeAll(connection, null, resultSet);
        return map;
    }

    /**
     * 获取表的所有列名以及对应的类型
     *
     * @return 列名与类型键值对
     */
    private Map<String, String> getColumnNames(String tableName) throws SQLException {
        Map<String, String> map = new HashMap<>();
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement("select * from " + tableName + " where 1 = 2");
        ResultSet rSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = rSet.getMetaData();
        //列的数量
        int length = resultSetMetaData.getColumnCount();
        for (int i = 0; i < length; i++) {
            if (!autoIncrement) autoIncrement = resultSetMetaData.isAutoIncrement(i + 1);
            String key = resultSetMetaData.getColumnName(i + 1);
            String value = resultSetMetaData.getColumnTypeName(i + 1).toLowerCase();
            map.put(key, value);
        }
        closeAll(connection, statement, rSet);
        return map;
    }


    /**
     * 获取指定表的主键名
     *
     * @param tableName 表名
     * @return 主键名称
     * @throws SQLException
     */
    private static String getTablePrimaryKey(String tableName) throws SQLException {
        String primaryKey = null;
        Connection connection = DriverManager.getConnection(url, user, password);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getPrimaryKeys(null, "", tableName);
        while (resultSet.next()) {
            primaryKey = resultSet.getString(4).toLowerCase();
        }
        closeAll(connection, null, resultSet);
        return primaryKey;
    }

    /**
     * 关闭连接
     */
    private static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 外键键值对
     */
    static class ForeignKey {
        public String otherTable;
        public String fkColumn;
        public String pkColumn;

        public ForeignKey() {
        }

        public ForeignKey(String otherTable, String fkColumn, String pkColumn) {
            this.otherTable = otherTable;
            this.fkColumn = fkColumn;
            this.pkColumn = pkColumn;
        }

        @Override
        public String toString() {
            return "ForeignKey{" +
                    "otherTable='" + otherTable + '\'' +
                    ", fkColumn='" + fkColumn + '\'' +
                    ", pkColumn='" + pkColumn + '\'' +
                    '}';
        }
    }
}