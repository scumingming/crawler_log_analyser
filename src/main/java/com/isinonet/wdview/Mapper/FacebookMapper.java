package com.isinonet.wdview.Mapper;


import com.isinonet.wdview.Mapper.provider.BaseProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */

public interface FacebookMapper extends BaseMapper {


    @SelectProvider(type = BaseProvider.class, method = "dataCountSql")
    int dataCount(@Param(value = "tableName") String tableName,
                  @Param(value = "column") String column,
                  @Param(value = "keyWords") String keyWords,
                  @Param(value = "startTime") String startTime,
                  @Param(value = "endTime") String endTime);

    @SelectProvider(type = BaseProvider.class, method = "dataListSql")
    List<Map<String, Object>> dataList(@Param(value = "tableName") String tableName,
                                       @Param(value = "column") String column,
                                       @Param(value = "keyWords") String keyWords,
                                       @Param(value = "startTime") String startTime,
                                       @Param(value = "endTime") String endTime);

    @Select("SELECT * FROM t_facebook_comment where post_id=#{postId} COLLATE NOCASE")
    List<Map<String, Object>> getComments(@Param(value = "postId") String postId);

    @Select("SELECT * FROM t_facebook_introduction where account_id=#{userId} COLLATE NOCASE  limit 1")
    Map<String, Object> getIntroduction(@Param(value = "userId") String userId);

    @Select("SELECT * FROM t_facebook_friend where friend_from=#{userId} COLLATE NOCASE")
    List<Map<String, Object>> getFriends(@Param(value = "userId") String userId);


    @Select("SELECT * FROM t_facebook_post where id in (${ids})")
    List<Map<String, Object>> getDataByIds(@Param(value = "ids") String ids);

    @Select("SELECT * FROM t_facebook_comment where post_id=${post_id}")
    List<Map<String, Object>> getCommentById(@Param(value = "post_id") String postId);

}
