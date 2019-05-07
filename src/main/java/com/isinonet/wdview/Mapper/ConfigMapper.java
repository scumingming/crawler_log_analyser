package com.isinonet.wdview.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by wangmingming on 2019/4/29.
 */
public interface ConfigMapper {

    @Select("SELECT keyword from t_keyword_config")
    List<String> getKeyWords();

    @Insert("INSERT INTO t_keyword_config(keyword) VALUES(#{keyWord})")
    int addKeyWord(@Param(value = "keyWord") String keyWord) ;


    @Delete("DELETE FROM t_keyword_config WHERE keyword=#{keyWord}")
    int deleteKeyWord(@Param(value = "keyWord") String keyWord) ;

}
