package com.isinonet.wdview.Service.impl;


import com.alibaba.fastjson.JSON;
import com.isinonet.wdview.Mapper.WebMapper;
import com.isinonet.wdview.Service.InsertService;
import com.isinonet.wdview.Service.WebService;
import com.isinonet.wdview.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zouchen on 2019/4/22.
 */
@Service
public class WebServiceImpl implements WebService {

    @Resource
    private WebMapper webMapper;

    @Value("${export.path}")
    String exportPath;

    public int dataCount(String keyWords, String startTime, String endTime) {

        return webMapper.dataCount("t_web_data","content",keyWords,startTime, endTime);
    }

    public List<Map<String, Object>> getDatas(String keyWords, String startTime, String endTime) {

        return webMapper.getDatas("t_web_data","content",keyWords,startTime, endTime);
    }

    public List<Map<String, Object>> getDataByIds(String ids) {

        return webMapper.getDataByIds(ids);

    }

    public void exports(HttpServletResponse response, String ids, String saveRootpath) {

        long currentTimeMillis = System.currentTimeMillis();
        final String path = StringUtils.isEmpty(saveRootpath)
                ? exportPath + "/web_" + currentTimeMillis
                : saveRootpath + "/web_" + currentTimeMillis;

        List<Map<String, Object>> datas = getDataByIds(ids);
        if(null == datas) return;


        datas.forEach(item -> {

            String area = (String) item.get("area");
            String dirName = null != area
                    ? area.substring(area.lastIndexOf(":")+3,area.length()) + item.hashCode()
                    : String.valueOf(item.hashCode());
            String savePath = path + "/" + dirName;

            List<String> lines = new ArrayList<>();
            lines.add(String.valueOf(item.get("title")));
            lines.add(item.get("time") + "   " + item.get("author"));
            lines.add(String.valueOf(item.get("content")));
            FileUtil.writeLines(savePath + "/" + "content.txt", lines);

            String rootPath = (String) item.get("root_path");
            List<String> copyFiles = new ArrayList<>();
            List<String> imagePaths = JSON.parseObject(String.valueOf(item.get("image_file")), List.class);
            if(!CollectionUtils.isEmpty(imagePaths)) copyFiles.addAll(imagePaths);
            List<String> videoPaths = StringUtils.isEmpty(item.get("video_file")) ? null : JSON.parseObject(String
                    .valueOf(item.get("video_file")), List.class);
            if(!CollectionUtils.isEmpty(videoPaths)) copyFiles.addAll(videoPaths);

            if(!CollectionUtils.isEmpty(copyFiles)) {
                copyFiles.forEach(copyPath -> {
                    String oldImagePath = rootPath + copyPath.substring(1);
                    String imageName = copyPath.substring(copyPath.lastIndexOf("/") + 1);
                    String newImagePath = savePath + "/" + imageName;
                    FileUtil.copyFile(oldImagePath, newImagePath);
                });
            }

        });

        String zipFilePath = path + ".zip";
        FileUtil.toZip(path, zipFilePath);
        byte[] bytes = FileUtil.readFileToBytes(zipFilePath);

        response.setContentType("application/x-download; charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=web" + currentTimeMillis + ".zip");

        try (OutputStream os = response.getOutputStream()) {
            os.write(bytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int insertBatch(String filePath,List<Map<String, Object>> list) {

        return 0;//;webMapper.insertBatch(list);

    }

    public int insert(String filePath,Map<String, Object> map) {

        String textPath = filePath.substring(0, filePath.lastIndexOf("/"));
        String rootPath = textPath.substring(0, textPath.lastIndexOf("/"));
        map.put("root_path", rootPath);
        String fileNameWithoutPostfix = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        String tableName = "t_web_" + fileNameWithoutPostfix;

        webMapper.genTable(tableName,map);
        return webMapper.insert(tableName, map);

    }

}
