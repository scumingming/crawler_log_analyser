package com.isinonet.wdview.Service.impl;

import com.isinonet.wdview.Mapper.ConfigMapper;
import com.isinonet.wdview.Service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangmingming on 2019/4/29.
 */
@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    ConfigMapper configMapper;

    public List<String> getKeyWords() {
        return configMapper.getKeyWords();
    }

    public int addKeyWord(String keyWord) {

        return configMapper.addKeyWord(keyWord);
    }



    public int deleteKeyWord(String keyWord) {

        return configMapper.deleteKeyWord(keyWord);
    }


}
