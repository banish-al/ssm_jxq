package com.zilong.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;


public class CreateEntityUtil {

    //表名
    private String tableName;
    //实体类名
    private String voName;
    //列名数组
    private String[] colNames;
    //列名类型数组
    private String[] colTypes;
    //列名大小数组
    private int[] colSizes;
    //表注释
    private String tableComment = null;
    //列名注释
    private Map<String, String> colNamesComment = new HashMap<>();

    /**
     * 表外键集合<br/>
     * key : 表名<br/>
     * value : ForeignKey对象集合
     */
    private static Map<String, List<CreateMapUtil.ForeignKey>> tableFkMap;

    /**
     * 外键关联主键集合<br/>
     * key : 表名<br/>
     * value : ForeignPrimaryKey对象集合
     */
    private static Map<String, List<ForeignPrimaryKey>> tablePkMap;

    /**
     * 当前表的外键集合
     */
    private List<CreateMapUtil.ForeignKey> tableFkList;

    /**
     * 当前表的外键关联主键集合
     */
    private List<ForeignPrimaryKey> tablePkList;


    //需要导入的sql包
    private String importSql = "";
    //是否需要导入包java.math.BigDecimal
    private boolean needBigDecimal = false;
    //是否创建EntityHelper
    private boolean needEntityHelper = false;
    //private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    // 数据库配置信息
    private static String URL = "";
    private static String NAME = "";
    private static String PASS = "";
    private static String DRIVER = "";

    //数据库名称
    private static String DATABASENAME = "";

    /**
     * 指定包名
     */
    private static String packageOutPath = "";

    /**包的绝对路径*/
    private static String packageUrl = "";

    //指定需要生成的表的表名，全部生成设置为null
    private static String[] generateTables = null;
    //生成表是数量
    private static int tableNum = 0;
    //主键
    private static String pk;

    /**
     * s是否创建构造函数
     */
    private static boolean generateConstructor = true;

    /**
     * 是否创建get,set方法
     */
    private static boolean generateGetSetMethod = true;

    /**
     * 是否重写ToString方法
     */
    private static boolean generateToString = true;

    /**
     * 是否使用外键关联关联对象替代外键Id
     */
    public static boolean useFkEntity = false;

    /**
     * 是否加入外键关联集合
     */
    public static boolean usePkEntityList = false;

    /**
     * 工具了当前版本
     */
    private static String version = "1.2";


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
        CreateEntityUtil.NAME = user;
        CreateEntityUtil.PASS = password;
        CreateEntityUtil.URL = url;
        CreateEntityUtil.DRIVER = driver;
        CreateEntityUtil.packageOutPath = packagePath;
        String databaseName = CreateEntityUtil.URL;
        databaseName = databaseName.substring(0, databaseName.indexOf("?"));
        databaseName = databaseName.substring(databaseName.lastIndexOf("/") + 1);
        CreateEntityUtil.DATABASENAME = databaseName;
        try {
            String dirUrl = CreateMapUtil.class.getResource("").toURI().getPath();
            packageUrl = dirUrl.substring(1, dirUrl.indexOf("/target/classes/")) + "/src/main/java/" + packagePath.replaceAll("\\.", "/") + "/";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定需要生成的表的表名，全部生成设置为null
     *
     * @param tableNames 表名
     */
    public static void addTable(String... tableNames) {
        if (generateTables == null) generateTables = tableNames;
        else generateTables = concat(generateTables, tableNames);
    }

    /**
     * 拼接数组
     */
    private static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 生成class的所有内容,外部调用
     *
     * @param generateConstructor  是否创建构造方法
     * @param generateGetSetMethod 是否创建get,set方法
     * @param generateToString     是否重写toString方法
     */
    public static void write(boolean generateConstructor, boolean generateGetSetMethod, boolean generateToString) {
        CreateEntityUtil.generateConstructor = generateConstructor;
        CreateEntityUtil.generateGetSetMethod = generateGetSetMethod;
        CreateEntityUtil.generateToString = generateToString;
        //备份文件
        CreateMapUtil.backupFile(packageOutPath);
        int errorNum = new CreateEntityUtil().generate();
        System.out.println("\n\n\n已自动备份写出前\"" + packageOutPath + "\"包下的所有文件,备份包路径为:\"" + packageOutPath + ".backup\"");
        System.out.println("\n已写出结果如下:");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\n写出完成!\n共写出" + tableNum + "个对象.");
        System.out.println("包含 : " + (tableNum - errorNum) + "个成功," + errorNum + "个失败!");
        if (errorNum > 0) {
            System.err.println("\n----------------------警告!!!---------------------------");
            System.err.println("写出存在异常!!!!,请检查原因!!!!");
            System.err.println("--------------------------------------------------------");
        }
        System.out.println("\nCreateEntityUtil version : " + version);
        System.out.println("方法 : CreateEntityUtil.write() 执行结束...");
    }

    private CreateEntityUtil() {
    }

    /**
     * @description 生成class的所有内容
     */
    private String parse() {
        try {
            tableFkList = null;
            tablePkList = null;
            if (useFkEntity) tableFkList = tableFkMap.get(voName);
            if (usePkEntityList) tablePkList = tablePkMap.get(voName);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageOutPath + ";\r\n");
            sb.append("\r\n");
            sb.append(importSql + "\r\n");

            for (int i = 0; i < colNames.length; i++) {
                String hasbd = sqlType2JavaType(colTypes[i]);
                if (hasbd == "BigDecimal" || "BigDecimal".equals(hasbd)) {
                    needBigDecimal = true;
                }
            }
            if (needBigDecimal) {
                sb.append("import java.math.BigDecimal;\r\n");
            }
            sb.append("import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\r\n");
            sb.append("import java.io.Serializable;\r\n\n");
            if (tablePkList != null) sb.append("import java.util.List;\r\n\n");
            // 注释部分
            sb.append("/**\r\n");
            sb.append(" * 表名 :  " + tableName + "<br/>\r\n");
            if (tableComment != null && !"".equals(tableComment)) sb.append(" * 表注释 : " + tableComment + "\r\n");
            sb.append(" */ \r\n");
            // 实体部分
            String classExtends = "";
            if (needEntityHelper) {
                classExtends = " extends EntityHelper";
            }
            sb.append("@JsonIgnoreProperties(value = \"handler\")\r\n");
            sb.append("public class " + voName + classExtends + " implements Serializable " + "{\r\n\r\n");

            processAllAttrs(sb);// 属性
            sb.append("\r\n");
            if (generateConstructor) processConstructor(sb);//构造函数
            if (generateGetSetMethod) processAllMethod(sb);// get set方法
            if (generateToString) processToString(sb);
            if (needEntityHelper) {
                processEntityHelper(sb, pk);
            }
            sb.append("}\r\n");
            return sb.toString();
        }catch (Exception e){
            System.err.println("error table : " + tableName + "\n");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param sb
     * @description 生成所有成员变量及注释
     * @author paul
     * @version V1.0
     */
    private void processAllAttrs(StringBuffer sb) {
        go:for (int i = 0; i < colNames.length; i++) {
            if (colNamesComment.get(colNames[i]) != null && !"".equals(colNamesComment.get(colNames[i]))) {
                sb.append("\t/**" + colNamesComment.get(colNames[i]) + "*/\r\n");
            }
            String sqlType = sqlType2JavaType(colTypes[i]);
            if(useFkEntity && tableFkList != null) {
                for (CreateMapUtil.ForeignKey fk : tableFkList) {
                    if (fk.fkColumn.equals(colNames[i])) {
                        sb.append("\tprivate " + fk.otherTable + " " + initLow(fk.otherTable) + ";\r\n");
                        continue go;
                    }
                }
            }
            if (sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("datetime")) {
                sb.append("\t@JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")\r\n");
            }
            sb.append("\tprivate " + sqlType + " " + colNames[i] + ";\r\n");
        }
        if(usePkEntityList){
            if (tablePkList != null)
                for (ForeignPrimaryKey i : tablePkList){
                    sb.append("\tprivate List<" + i.otherTable + "> " + initLow(i.otherTable) + "s;\r\n");
                }
        }
    }

    /**
     * EntityHelper
     *
     * @param sb
     * @param pk
     */
    private void processEntityHelper(StringBuffer sb, String pk) {
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String getPrimaryKey() {\r\n");
        sb.append("\t\treturn \"" + pk + "\";\r\n");
        sb.append("\t}\r\n");
    }

    /**
     * 重写toString()方法
     *
     * @param sb
     */
    private void processToString(StringBuffer sb) {
        String str = "";
        str += "\t@Override\r\n\tpublic String toString() {\r\n";
        str += "\t\treturn \"" + tableName + "[\" + \r\n";
        str += "\t\t\t\"";
        go:for (int i = 0; i < colNames.length; i++) {
            if (useFkEntity && tableFkList != null) {
                for (CreateMapUtil.ForeignKey fk : tableFkList) {
                    if (fk.fkColumn.equals(colNames[i])) {
                        String lowName = initLow(fk.otherTable);
                        str += lowName + "=\" + " + lowName + " + \r\n";
                        str += "\t\t\t\", ";
                        continue go;
                    }
                }
            }
            str += colNames[i] + "=\" + " + colNames[i] + " + \r\n";
            str += "\t\t\t\", ";
        }
        if (usePkEntityList) {
            if (tablePkList != null)
                for (ForeignPrimaryKey j : tablePkList) {
                    String lowName = initLow(j.otherTable) + "s";
                    str += lowName + "=\" + " + lowName + " + \r\n";
                    str += "\t\t\t\", ";
                }
        }
        str = str.substring(0,str.length() - 2);
        str += "\t\t\t]\";\r\n";
        str += "\t}\r\n";
        sb.append(str);
    }

    /**
     * 构造函数
     *
     * @param sb
     */
    private void processConstructor(StringBuffer sb) {
        String p = "";
        String v = "";
        go:for (int i = 0; i < colNames.length; i++) {
            if(useFkEntity && tableFkList != null) {
                for (CreateMapUtil.ForeignKey fk : tableFkList) {
                    if (fk.fkColumn.equals(colNames[i])) {
                        p += fk.otherTable + " " + initLow(fk.otherTable) + ",";
                        v += "\t\tthis." + initLow(fk.otherTable) + "=" + initLow(fk.otherTable) + ";\r\n";
                        continue go;
                    }
                }
            }
            p += sqlType2JavaType(colTypes[i]) + " " + colNames[i] + ",";
            v += "\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n";
        }
        if(usePkEntityList){
            if (tablePkList != null)
                for (ForeignPrimaryKey j : tablePkList){
                    p += "List<" + j.otherTable + "> " + initLow(j.otherTable) + "s,";
                    v += "\t\tthis." + initLow(j.otherTable) + "s=" + initLow(j.otherTable) + "s;\r\n";
                }
        }
        //去除尾部逗号
        p = p.substring(0,p.length() - 1);
        //无参数构造函数
        sb.append("\tpublic " + under2camel(tableName, true) + "() {\r\n");
        sb.append("\t\tsuper();\r\n");
        sb.append("\t}\r\n");
        //带参构造函数
        sb.append("\tpublic " + under2camel(tableName, true) + "(" + p.toString() + ") {\r\n");
        sb.append(v);
        sb.append("\t}\r\n");
    }

    /**
     * @param sb
     * @description 生成所有get/set方法
     */
    private void processAllMethod(StringBuffer sb) {
        go:for (int i = 0; i < colNames.length; i++) {
            if(useFkEntity && tableFkList != null) {
                for (CreateMapUtil.ForeignKey fk : tableFkList) {
                    if (fk.fkColumn.equals(colNames[i])) {
                        String capName = initCap(fk.otherTable);
                        String lowName = initLow(fk.otherTable);
                        sb.append("\tpublic void set" + capName + "(" + capName + " "
                                + lowName + "){\r\n");
                        sb.append("\t\tthis." + lowName + "=" + lowName + ";\r\n");
                        sb.append("\t}\r\n");
                        sb.append("\tpublic " + capName + " get" + capName + "(){\r\n");
                        sb.append("\t\treturn " + lowName + ";\r\n");
                        sb.append("\t}\r\n");
                        continue go;
                    }
                }
            }
            boolean flag = colNamesComment.get(colNames[i]) != null && !"".equals(colNamesComment.get(colNames[i]));
            if (flag) sb.append("\t/**设置\"" + colNamesComment.get(colNames[i]) + "\"*/\r\n");
            sb.append("\tpublic void set" + initCap(colNames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + colNames[i] + "){\r\n");
            sb.append("\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
            if (flag) sb.append("\t/**获取\"" + colNamesComment.get(colNames[i]) + "\"*/\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initCap(colNames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
        if(usePkEntityList){
            if (tablePkList != null)
                for (ForeignPrimaryKey i : tablePkList){
                    String capName = initCap(i.otherTable);
                    String lowName = initLow(i.otherTable) + "s";
                    sb.append("\tpublic void set" + capName + "s(List<" + capName + "> "
                            + lowName + "){\r\n");
                    sb.append("\t\tthis." + lowName + "=" + lowName + ";\r\n");
                    sb.append("\t}\r\n");
                    sb.append("\tpublic List<" + capName + "> get" + capName + "s(){\r\n");
                    sb.append("\t\treturn " + lowName + ";\r\n");
                    sb.append("\t}\r\n");
                }
        }
    }

    /**
     * @param str 传入字符串
     * @return
     * @description 将传入字符串的首字母转成大写
     */
    private String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
            ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * @param str 传入字符串
     * @return
     * @description 将传入字符串的首字母转成小写
     */
    private String initLow(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z')
            ch[0] = (char) (ch[0] + 32);
        return new String(ch);
    }

    /**
     * 功能：下划线命名转驼峰命名
     *
     * @param s
     * @param fistCharToUpperCase 首字母是否大写
     * @return
     * @author 呐喊
     */
    private String under2camel(String s, boolean fistCharToUpperCase) {
        String separator = "_";
        String under = "";
        s = s.replace(separator, " ");
        String sarr[] = s.split(" ");
        for (int i = 0; i < sarr.length; i++) {
            String w = sarr[i].substring(0, 1).toUpperCase() + sarr[i].substring(1);
            under += w;
        }
        if (!fistCharToUpperCase) {
            under = under.substring(0, 1).toLowerCase() + under.substring(1);
        }
        return under;
    }

    /**
     * @return
     * @description 查找sql字段类型所对应的Java类型
     */
    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("longtext")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            return "BigDecimal";
        } else if (sqlType.equalsIgnoreCase("timestamp")) {
            return "Timestamp";
        }
        return initCap(sqlType.toLowerCase());
    }

    /**
     * 功能：获取并创建实体所在的路径目录
     *
     * @return
     */
    private static String pkgDirName() {
        String dirName = null;
        try {
            dirName = CreateMapUtil.class.getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        dirName = dirName.substring(1, dirName.indexOf("/target/classes/")) + "/src/main/java/" + packageOutPath.replaceAll("\\.", "/");
        //String dirName = basePath + "/src/main/java/" + packageOutPath.replace(".", "/");
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("mkdirs dir 【" + dirName + "】");
        }
        return dirName;
    }

    /**
     * 生成EntityHelper
     */
    private void EntityHelper() {
        String dirName = CreateEntityUtil.pkgDirName();
        String javaPath = dirName + "/EntityHelper.java";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageOutPath + ";\r\n");
            sb.append("\r\n");
            sb.append("public abstract class EntityHelper{\r\n\r\n");
            sb.append("\tpublic abstract String getPrimaryKey();\r\n");
            sb.append("\r\n");
            sb.append("}\r\n");
            FileWriter fw = new FileWriter(javaPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            if (pw != null) {
                pw.close();
            }
            System.out.println("create class 【EntityHelper】");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @description 生成方法
     * @return 返回错误个数
     */
    private int generate() {
        //与数据库的连接
        Connection con = null;
        PreparedStatement pStemt = null;
        tableFkMap = new HashMap<>();
        tablePkMap = new HashMap<>();
        int errorNum = 0;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, NAME, PASS);
            System.out.println("connect database success..." + con);
            //获取数据库的元数据
            DatabaseMetaData db = con.getMetaData();
            //是否有指定生成表，有指定则直接用指定表，没有则全表生成
            List<String> tableNames = new ArrayList<>();
            if (generateTables == null) {
                //从元数据中获取到所有的表名
                ResultSet rs = db.getTables(null, null, null, new String[]{"TABLE"});
                while (rs.next()) tableNames.add(rs.getString(3));
                rs.close();
            } else {
                tableNames.addAll(Arrays.asList(generateTables));
            }
            tableNum = tableNames.size();
            if (needEntityHelper) {
                EntityHelper();
            }
            String tableSql;
            PrintWriter pw = null;
            int len = tableNames.size();
            for (int j = 0; j < len; j++) {
                tableName = tableNames.get(j);
                //根据表名获取外键
                //foreignKeys
                if (useFkEntity || usePkEntityList) {
                    Map<String, CreateMapUtil.ForeignKey> foreignKeyMap = getForeignKeys(tableName);
                    //加入到foreignKeys
                    addForeignKeys(foreignKeyMap);
                }
            }
            //
            System.out.println("--------------------------------------");
            System.out.println("tableFkMap = "  +tableFkMap);
            System.out.println("tablePkMap = "  +tablePkMap);
            System.out.println("--------------------------------------");
            //
            for (int j = 0; j < len; j++) {
                importSql = "";
                tableName = tableNames.get(j);
                voName = under2camel(tableName,true);
                tableComment = null;
                System.out.println("tableName = " + tableName);
                tableSql = SQL + tableName;
                pStemt = con.prepareStatement(tableSql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                ResultSet rsk = con.getMetaData().getPrimaryKeys(con.getCatalog().toLowerCase(), null, tableName);
                if (rsk.next()) {
                    String primaryKey = rsk.getString("COLUMN_NAME");
                    pk = primaryKey;
                }
                rsk.close();
                int size = rsmd.getColumnCount();
                colNames = new String[size];
                colTypes = new String[size];
                colSizes = new int[size];
                //获取所需的信息
                for (int i = 0; i < size; i++) {
                    colNames[i] = under2camel(rsmd.getColumnName(i + 1), false);
                    colTypes[i] = rsmd.getColumnTypeName(i + 1);
                    boolean flag = false;
                    if (colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("date")) {
                        importSql += "import java.sql.Date;\n";
                        flag = true;
                    }
                    if (colTypes[i].equalsIgnoreCase("timestamp")) {
                        importSql += "import java.sql.Timestamp;\n";
                        flag = true;
                    }
                    if (colTypes[i].equalsIgnoreCase("image")) {
                        importSql += "import java.sql.Blod;\n";
                    }
                    if (flag) importSql += "import com.fasterxml.jackson.annotation.JsonFormat;\n";
                    colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                }
                //获取表注释
                ResultSet resultSet = pStemt.executeQuery("SELECT table_comment FROM information_schema.TABLES WHERE table_schema = '" + DATABASENAME + "' and TABLE_NAME = '" + tableName + "' ORDER BY table_name;");
                if (resultSet.next()) tableComment = resultSet.getString(1);
                //获取字段注释
                ResultSet rsComment = pStemt.executeQuery("show full columns from " + tableName);
                while (rsComment.next()) {
                    colNamesComment.put(under2camel(rsComment.getString("Field"), false), rsComment.getString("Comment"));
                }
                rsComment.close();
                //解析生成实体java文件的所有内容
                String content = parse();
                if(content == null) {
                    errorNum ++;
                    continue;
                }
                //输出生成文件
                String dirName = CreateEntityUtil.pkgDirName();
                String javaPath = dirName + "/" + under2camel(tableName, true) + ".java";
                FileWriter fw = new FileWriter(javaPath);
                pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                System.out.println("create class 【" + tableName + "】");
            }
            if (pw != null)
                pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(con, pStemt, null);
        }
        return errorNum;
    }

    /**
     * 将表的外键map添加到foreignKeys集合中
     *
     * @param foreignKeyMap 表的外键集合
     */
    private void addForeignKeys(Map<String, CreateMapUtil.ForeignKey> foreignKeyMap) {
        Set<String> set = foreignKeyMap.keySet();
        String name = under2camel(tableName,true);
        for (String i : set){
            //键值对对象
            CreateMapUtil.ForeignKey temp = foreignKeyMap.get(i);
            temp = new CreateMapUtil.ForeignKey(under2camel(temp.otherTable,true),
                    under2camel(temp.fkColumn,false),under2camel(temp.pkColumn,false));
            if(useFkEntity){
                List<CreateMapUtil.ForeignKey> temp1 = tableFkMap.get(name);
                if(temp1 == null){
                    temp1 = new ArrayList<>();
                    tableFkMap.put(name,temp1);
                }
                temp1.add(temp);
            }
            if(usePkEntityList){
                String otherName = under2camel(temp.otherTable,true);
                List<ForeignPrimaryKey> temp1 = tablePkMap.get(otherName);
                if(temp1 == null){
                    temp1 = new ArrayList<>();
                    tablePkMap.put(otherName,temp1);
                }
                temp1.add(new ForeignPrimaryKey(name,temp.pkColumn,temp.fkColumn));
            }
        }
    }

    /**
     * 获取表的外键
     *
     * @param tableName 当前表名
     * @return map集合, 第一项为外键表名, 第二项为外键列名
     */
    private static Map<String, CreateMapUtil.ForeignKey> getForeignKeys(String tableName) throws SQLException {
        Map<String, CreateMapUtil.ForeignKey> map = new HashMap<>();
        Connection connection = DriverManager.getConnection(URL, NAME, PASS);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getImportedKeys(connection.getCatalog(), null, tableName);
        while (resultSet.next()) {
            String fkColumnName = resultSet.getString("FKCOLUMN_NAME");
            String pkTablenName = resultSet.getString("PKTABLE_NAME").toLowerCase();
            String pkColumnName = resultSet.getString("PKCOLUMN_NAME");
            map.put(pkTablenName, new CreateMapUtil.ForeignKey(pkTablenName, fkColumnName, pkColumnName));
        }
        closeAll(connection, null, resultSet);
        return map;
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
     * 外键关联主键键值对
     */
    static class ForeignPrimaryKey {
        public String otherTable;
        public String pkColumn;
        public String fkColumn;

        public ForeignPrimaryKey(){}

        public ForeignPrimaryKey(String otherTable, String pkColumn, String fkColumn) {
            this.otherTable = otherTable;
            this.pkColumn = pkColumn;
            this.fkColumn = fkColumn;
        }

        @Override
        public String toString() {
            return "ForeignPrimaryKey{" +
                    "otherTable='" + otherTable + '\'' +
                    ", pkColumn='" + pkColumn + '\'' +
                    ", fkColumn='" + fkColumn + '\'' +
                    '}';
        }
    }
}