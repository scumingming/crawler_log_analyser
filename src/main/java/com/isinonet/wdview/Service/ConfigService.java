package com.isinonet.wdview.Service;


import java.util.List;

/**
 * Created by wangmingming on 2019/4/29.
 */

public interface ConfigService {

    List<String> getKeyWords();

    int addKeyWord(String keyWord);


    int deleteKeyWord(String keyWord);

}
