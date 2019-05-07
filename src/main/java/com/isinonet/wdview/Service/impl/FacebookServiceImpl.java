package com.isinonet.wdview.Service.impl;

import com.alibaba.fastjson.JSON;
import com.isinonet.wdview.Mapper.FacebookMapper;
import com.isinonet.wdview.Service.FacebookService;
import com.isinonet.wdview.Service.InsertService;
import com.isinonet.wdview.request.BaseRequest;
import com.isinonet.wdview.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
@Service
public class FacebookServiceImpl implements FacebookService {

    private final String[] COLUMNS = new String[]{"","","",""};

    @Autowired
    FacebookMapper facebookMapper;

    @Value("${export.path}")
    String exportPath;


    @Override
    public int insertBatch(String filePath, List<Map<String, Object>> list) {
        return 0;
    }

    @Override
    public int insert(String filePath, Map<String, Object> map) {
        String textPath = filePath.substring(0, filePath.lastIndexOf("/"));
        String rootPath = textPath.substring(0, textPath.lastIndexOf("/"));
        map.put("root_path", rootPath);

        if(filePath.indexOf("introduction") >= 0) {
            facebookMapper.genTable("t_facebook_introduction",map);
            return facebookMapper.insert("t_facebook_introduction", map);

        } else if (filePath.indexOf("friend") >= 0) {
            facebookMapper.genTable("t_facebook_friend",map);
            return facebookMapper.insert("t_facebook_friend", map);

        } else if (filePath.indexOf("post_comment") >= 0) {
            if(map.containsKey("comment_id")) {
                facebookMapper.genTable("t_facebook_comment",map);
                return facebookMapper.insert("t_facebook_comment", map);
            } else {
                facebookMapper.genTable("t_facebook_post",map);
                return facebookMapper.insert("t_facebook_post", map);
            }

        }

        return -1;

    }


    /**
     * 新增关键字的帖子个数
     * @param request
     * @return
     */
    public int dataCount(BaseRequest request) {


        return facebookMapper.dataCount("t_facebook_post", "post_content", request.getKeyWords(),request
                .getStartTime(),request.getEndTime());
    }


    /**
     * 帖子列表
     * @param request
     * @return
     */
    public List<Map<String, Object>> dataList(BaseRequest request) {


        List<Map<String, Object>> data = facebookMapper.dataList("t_facebook_post", "post_content",
                request.getKeyWords(), request.getStartTime(),request.getEndTime());

        return data;
    }



    /**
     * 帖子作者简介
     * @param userId
     * @return
     */
    public Map<String, Object> getIntroduction(String userId) {

        Map<String, Object> aboutData = facebookMapper.getIntroduction(userId);
        return aboutData;
    }


    /**
     * 帖子评论
     * @param
     * @return
     */
    public List<Map<String, Object>> getComments(String postId) {
        return facebookMapper.getComments(postId);
    }

    /**
     * 作者粉丝
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getFriends(String userId) {

        return facebookMapper.getFriends(userId);
    }


    public List<Map<String, Object>> getDataByIds(String ids) {

        return facebookMapper.getDataByIds(ids);

    }

    public List<Map<String, Object>> getCommentById(String id) {
        return facebookMapper.getCommentById(id);
    }


    public void exports(HttpServletResponse response, String ids, String saveRootpath) {

        long currentTimeMillis = System.currentTimeMillis();
        final String path = StringUtils.isEmpty(saveRootpath)
                ? exportPath + "/facebook_" + currentTimeMillis
                : saveRootpath + "/facebook_" + currentTimeMillis;

        List<Map<String, Object>> datas = getDataByIds(ids);
        if(null == datas) return;

        datas.forEach(item -> {

            String author_id = (String) item.get("author_id");
            String dirName = null != author_id
                    ? author_id + item.hashCode()
                    : String.valueOf(item.hashCode());
            String savePath = path + "/" + dirName;

            List<String> lines = new ArrayList<>();
            lines.add(String.valueOf(item.get("author_id")));
            lines.add(item.get("post_date") + "");
            lines.add(String.valueOf(item.get("post_content")));

            String post_id = item.get("post_id") + "";
            List<Map<String, Object>> comments = getCommentById(post_id);
            if(!CollectionUtils.isEmpty(comments)) {
                comments.forEach(comment -> {
                    String commentName = comment.get("comment_name") + "";
                    String commentTime = comment.get("comment_date") + "";
                    String commentContent = comment.get("comment_content") + "";

                    lines.add(commentName + "     " + commentTime);
                    lines.add(commentContent);

                });
            }

            FileUtil.writeLines(savePath + "/" + "content.txt", lines);

            String rootPath = (String) item.get("root_path");
            List<String> copyFiles = new ArrayList<>();
            String imagePath = item.get("post_images_path") + "";
            if(imagePath.startsWith("[")) {
                List<String> imagePaths = JSON.parseObject(imagePath, List.class);
                copyFiles.addAll(imagePaths);
            } else if (!StringUtils.isEmpty(imagePath)) {
                copyFiles.add(imagePath);
            } else {

            }

            String videoPath = item.get("post_videos_path") + "";
            if(videoPath.startsWith("[")) {
                List<String> videoPaths =  JSON.parseObject(videoPath, List.class);
                copyFiles.addAll(videoPaths);
            } else if(!StringUtils.isEmpty(videoPath)) {
                copyFiles.add(videoPath);
            }

            if(!CollectionUtils.isEmpty(copyFiles)) {
                copyFiles.forEach(copyPath -> {
                    String oldImagePath = rootPath + "/" + copyPath;
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
        response.addHeader("Content-Disposition", "attachment;filename=fackbook_" + currentTimeMillis + ".zip");

        try (OutputStream os = response.getOutputStream()) {
            os.write(bytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }






}

