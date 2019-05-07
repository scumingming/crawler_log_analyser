package com.isinonet.wdview.Mapper;

import com.isinonet.wdview.Mapper.provider.BaseProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
public interface BaseMapper {


    @UpdateProvider(type = BaseProvider.class, method = "genCreateTableSql")
    int genTable(@Param(value = "tableName") String tableName, @Param(value = "map") Map<String, Object> map);


    @InsertProvider(type = BaseProvider.class, method = "insertSql")
    int insert(@Param(value = "tableName") String tableName, @Param(value = "map") Map<String, Object> map);

}
