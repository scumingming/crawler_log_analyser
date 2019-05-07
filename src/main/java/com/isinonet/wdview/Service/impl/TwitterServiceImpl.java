package com.isinonet.wdview.Service.impl;

import com.alibaba.fastjson.JSON;
import com.isinonet.wdview.Mapper.TwitterMapper;
import com.isinonet.wdview.Service.InsertService;
import com.isinonet.wdview.Service.TwitterService;
import com.isinonet.wdview.request.BaseRequest;
import com.isinonet.wdview.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
@Service
public class TwitterServiceImpl implements TwitterService {

    @Resource
    TwitterMapper twitterMapper;

    @Value("${export.path}")
    String exportPath;

    @Override
    public int insertBatch(String filePath,List<Map<String, Object>> list) {
        return twitterMapper.insertBatch(list);
    }

    @Override
    public int insert(String filePath,Map<String, Object> map) {
        String textPath = filePath.substring(0, filePath.lastIndexOf("/"));
        String rootPath = textPath.substring(0, textPath.lastIndexOf("/"));
        map.put("root_path", rootPath);
        String fileNameWithoutPostfix = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        String tableName = "t_twitter_" + fileNameWithoutPostfix;
//        if(fileName.indexOf("aboutdata") >= 0) {
//            twitterMapper.genTable("t_twitter_aboutdata",map);
//            twitterMapper.insert2("t_twitter_aboutdata", map);
//
//        } else if (fileName.indexOf("commentdata") >= 0) {
//            twitterMapper.genTable("t_twitter_commentdata",map);
//            twitterMapper.insert2("t_twitter_commentdata", map);
//
//        } else if (fileName.indexOf("followerdata") >= 0) {
//            twitterMapper.genTable("t_twitter_followerdata",map);
//            twitterMapper.insert2("t_twitter_followerdata", map);
//
//        } else if (fileName.indexOf("followingdata") >= 0) {
//            twitterMapper.genTable("t_twitter_followingdata",map);
//            twitterMapper.insert2("t_twitter_followingdata", map);
//
//        } else if (fileName.indexOf("likedata") >= 0) {
//            twitterMapper.genTable("t_twitter_likedata",map);
//            twitterMapper.insert2("t_twitter_likedata", map);
//        } else if (fileName.indexOf("tweetdata") >= 0) {
////            twitterMapper.insertTweetData(map);
//            twitterMapper.genTable("t_twitter_tweetdata",map);
//            twitterMapper.insert2("t_twitter_tweetdata", map);
//        }
        twitterMapper.genTable(tableName,map);
        return twitterMapper.insert(tableName, map);

    }


    /**
     * 新增关键字的帖子个数
     * @param request
     * @return
     */
    public int dataCount(BaseRequest request) {

//        twitterService
        return twitterMapper.dataCount("t_twitter_tweetdata", "tweet_content", request.getKeyWords(),request
                .getStartTime(),request.getEndTime());
    }


    /**
     * 帖子列表
     * @param request
     * @return
     */
    public List<Map<String, Object>> dataList(BaseRequest request) {


        List<Map<String, Object>> data = twitterMapper.dataList("t_twitter_tweetdata", "tweet_content", request.getKeyWords(),request
                .getStartTime(),request.getEndTime());

        return data;
    }




    /**
     * 帖子作者简介
     * @param userId
     * @return
     */
    public Map<String, Object> getAboutData(String userId) {

        Map<String, Object> aboutData = twitterMapper.getAboutData(userId);
        return aboutData;
    }


    /**
     * 帖子评论
     * @param tweetId
     * @return
     */
    public List<Map<String, Object>> getCommentData(String tweetId) {
        return twitterMapper.getCommentData(tweetId);
    }

    /**
     * 作者粉丝
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getFollowerData(String userId) {

        return twitterMapper.getFollowerData(userId);
    }

    /**
     * 作者关注列表
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getFollowingData(String userId) {

        return twitterMapper.getFollowingData(userId);
    }


    public List<Map<String, Object>> getDataByIds(String ids) {

        return twitterMapper.getDataByIds(ids);

    }

    public List<Map<String, Object>> getCommentById(String id) {
        return twitterMapper.getCommentById(id);
    }


    public void exports(HttpServletResponse response, String ids, String saveRootpath) {

        long currentTimeMillis = System.currentTimeMillis();
        final String path = StringUtils.isEmpty(saveRootpath)
                            ? exportPath + "/twitter_" + currentTimeMillis
                            : saveRootpath + "/twitter_" + currentTimeMillis;

        List<Map<String, Object>> datas = getDataByIds(ids);
        if(null == datas) return;

        datas.forEach(item -> {

            String account_id = (String) item.get("account_id");
            String dirName = null != account_id
                    ? account_id + item.hashCode()
                    : String.valueOf(item.hashCode());
            String savePath = path + "/" + dirName;

            List<String> lines = new ArrayList<>();
            lines.add(String.valueOf(item.get("tweet_author")));
            lines.add(item.get("tweet_time") + "");
            lines.add(String.valueOf(item.get("tweet_content")));

            String tweet_id = item.get("tweet_id") + "";
            List<Map<String, Object>> comments = getCommentById(tweet_id);
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
            String imagePath = item.get("image_path") + "";
            if(imagePath.startsWith("[")) {
                List<String> imagePaths = JSON.parseObject(imagePath, List.class);
                copyFiles.addAll(imagePaths);
            } else if (!StringUtils.isEmpty(imagePath)) {
                copyFiles.add(imagePath);
            } else {

            }

            String videoPath = item.get("video_path") + "";
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
        response.addHeader("Content-Disposition", "attachment;filename=twitter_" + currentTimeMillis + ".zip");

        try (OutputStream os = response.getOutputStream()) {
            os.write(bytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
