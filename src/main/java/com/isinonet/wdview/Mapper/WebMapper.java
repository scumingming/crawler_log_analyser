package com.isinonet.wdview.Mapper;
import com.isinonet.wdview.Mapper.provider.BaseProvider;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zouchen on 2019/4/22.
 */
public interface WebMapper extends BaseMapper {


//    @Insert(
//            "<script> "+
//                    " insert into t_web_data(url, author, video_file,content,area,time,image_file,pdf_file) values " +
//                    " <foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
//                    " (#{item.url},#{item.author},#{item.video_file},#{item.content},#{item.area},#{item.time}," +
//                    "#{item.image_file},#{item.pdf_file}),"+
//                    " </foreach> "+
//                    " </script>")
//    int insertBatch(@Param(value="list")List<Map<String, Object>> list);



    @SelectProvider(type = BaseProvider.class, method = "dataCountSql")
    int dataCount(@Param(value = "tableName") String tableName,
                  @Param(value = "column") String column,
                  @Param(value = "keyWords") String keyWords,
                  @Param(value = "startTime") String startTime,
                  @Param(value = "endTime") String endTime);

    @SelectProvider(type = BaseProvider.class, method = "dataListSql")
    List<Map<String, Object>> getDatas(@Param(value = "tableName") String tableName,
                                       @Param(value = "column") String column,
                                       @Param(value = "keyWords") String keyWords,
                                       @Param(value = "startTime") String startTime,
                                       @Param(value = "endTime") String endTime);


    @Select("SELECT * FROM t_web_data where id in (${ids})")
    List<Map<String, Object>> getDataByIds(@Param(value = "ids") String ids);




}
