package com.isinonet.wdview.Mapper.provider;

import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
public class BaseProvider {

    public static String genCreateTableSql(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        String tableName = (String) params.get("tableName");
        Map<String, Object> map = (Map<String, Object>) params.get("map");

        Iterator it = map.keySet().iterator();
        sb.append("create table if not EXISTS ").append(tableName).append(" (\n ")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT,\n");
        while (it.hasNext()) {

            sb.append(it.next()).append(" Text,\n");

        }
        String sql = sb.append("TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP);").toString();

        return sql;
    }



    public static String insertSql(Map<String, Object> params) {

        StringBuilder sb = new StringBuilder();
        String tableName = (String) params.get("tableName");
        Map<String, Object> map = (Map<String, Object>) params.get("map");

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            columns.append(entry.getKey()).append(",");
            values.append("'").append(String.valueOf(entry.getValue()).replace("'","''")).append("',");
        }

        sb.append("insert into ")
                .append(tableName)
                .append("(").append(columns.substring(0, columns.length() -1)).append(")")
                .append(" VALUES ")
                .append("(").append(values.substring(0, values.length() -1)).append(")");

        return sb.toString();
    }



    public static String dataCountSql(Map<String, Object> params) {

        StringBuilder sb = new StringBuilder();
        String tableName = (String) params.get("tableName");
        String keywords = (String) params.get("keyWords");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        String column = (String) params.get("column");

        sb.append("SELECT SUM(1) FROM ")
                .append(tableName).append(" ")
                .append("WHERE 1=1 ");
        if (!StringUtils.isEmpty(startTime)) {
            sb.append(" and TimeStamp>='").append(startTime).append("'");
        }
        if (!StringUtils.isEmpty(endTime)) {
            sb.append(" and TimeStamp<='").append(endTime).append("'");
        }

        if(!StringUtils.isEmpty(keywords)) {
            sb.append(" and (");
            String[] keywordArray = keywords.split(",");
            for (int i = 0; i < keywordArray.length; i++) {
                sb.append(column).append(" like '%").append(keywordArray[i]).append("%'");
                if(i < keywordArray.length - 1) {
                    sb.append(" OR ");
                }
            }
            sb.append(")");
        }

        return sb.toString();
    }

    public static String dataListSql(Map<String, Object> params) {

        StringBuilder sb = new StringBuilder();
        String tableName = (String) params.get("tableName");
        String keywords = (String) params.get("keyWords");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        String column = (String) params.get("column");

        sb.append("SELECT * FROM ")
                .append(tableName).append(" ")
                .append("WHERE 1=1 ");
        if (!StringUtils.isEmpty(startTime)) {
            sb.append(" and TimeStamp>='").append(startTime).append("'");
        }
        if (!StringUtils.isEmpty(endTime)) {
            sb.append(" and TimeStamp<='").append(endTime).append("'");
        }

        if(!StringUtils.isEmpty(keywords)) {
            sb.append(" and (");
            String[] keywordArray = keywords.split(",");
            for (int i = 0; i < keywordArray.length; i++) {
                sb.append(column).append(" like '%").append(keywordArray[i]).append("%'");
                if(i < keywordArray.length - 1) {
                    sb.append(" OR ");
                }
            }
            sb.append(")");
        }

        return sb.toString();
    }





}
