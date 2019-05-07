package com.isinonet.wdview.Mapper;

import com.isinonet.wdview.Mapper.provider.BaseProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
public interface TwitterMapper extends BaseMapper {

    @Insert({
            "<script> ",
            " insert into t_web_data(url, author, video_file,content,area,time,image_file,pdf_file) values ",
            " <foreach collection='list' item='item' index='index' separator=','>" ,
            " (#{item.url},#{item.author},#{item.video_file},#{item.content},#{item.area},#{item.time},#{item" +
                    ".image_file},#{item.pdf_file}),",
            " </foreach> ",
            " </script>"
        })
//    @SelectKey(statement = "SELECT seq id FROM sqlite_sequence WHERE (name = 'twitter_news')", before = false, keyProperty = "id", resultType = long.class)
    int insertBatch(@Param(value = "list") List<Map<String, Object>> list);


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

//    @SelectProvider(type = BaseProvider.class, method = "newMsgsSql")
//    List<Map<String, Object>> getDataContent(@Param(value="tableName")String tableName,
//                                       @Param(value="column")String column,
//                                       @Param(value="keyWords")String keyWords,
//                                       @Param(value="startTime")String startTime,
//                                       @Param(value ="endTime")String endTime);

    @Select("SELECT * FROM t_twitter_aboutdata where tweet_user_id=#{userId} COLLATE NOCASE  limit 1")
    Map<String, Object> getAboutData(@Param(value = "userId") String userId);

    @Select("SELECT * FROM t_twitter_commentdata where tweet_id=#{tweetId} COLLATE NOCASE")
    List<Map<String, Object>> getCommentData(@Param(value = "tweetId") String tweetId);

    @Select("SELECT * FROM t_twitter_followerdata where follower_to=#{userId} COLLATE NOCASE")
    List<Map<String, Object>> getFollowerData(@Param(value = "userId") String userId);


    @Select("SELECT * FROM t_twitter_followingdata where following_from=#{userId} COLLATE NOCASE")
    List<Map<String, Object>> getFollowingData(@Param(value = "userId") String userId);


    @Select("SELECT * FROM t_twitter_tweetdata where id in (${ids})")
    List<Map<String, Object>> getDataByIds(@Param(value = "ids") String ids);

    @Select("SELECT * FROM t_twitter_commentdata where tweet_id=${tweet_id}")
    List<Map<String, Object>> getCommentById(@Param(value = "tweet_id") String tweetId);




}
