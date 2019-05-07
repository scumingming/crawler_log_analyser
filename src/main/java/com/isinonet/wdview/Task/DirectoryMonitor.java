package com.isinonet.wdview.Task;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by zouchen on 2019/4/22.
 */
@Service
class DirectoryMonitor implements InitializingBean {

//    ThreadPoolExecutor threadPoolExecutor ;

    @Value("${monitor.path}")
    String monitorPath;

    private static final Logger logger = LoggerFactory.getLogger(DirectoryMonitor.class);

    @Autowired
    FileHanlder fileCreatedHanlder;

    public class FileMonitor {

        FileAlterationMonitor monitor = null;

        public FileMonitor(long interval) throws Exception {
            monitor = new FileAlterationMonitor(interval);
        }

        public void monitor(String path, FileAlterationListener listener) {
            FileAlterationObserver observer = new FileAlterationObserver(new File(path));
            monitor.addObserver(observer);
            observer.addListener(listener);
        }

        public void start() throws Exception {
            monitor.start();
        }

    }

    public class FileListener implements FileAlterationListener {

        @Override
        public void onStart(FileAlterationObserver observer) {
            logger.info("onStart");
        }

        @Override
        public void onDirectoryCreate(File directory) {
            logger.info("onDirectoryCreate:" + directory.getAbsolutePath());
        }

        @Override
        public void onDirectoryChange(File directory) {
            logger.info("onDirectoryChange:" + directory.getName());
        }

        @Override
        public void onDirectoryDelete(File directory) {
            logger.info("onDirectoryDelete:" + directory.getName());
        }

        @Override
        public void onFileCreate(File file) {
            logger.info("onFileCreate:" + file.getAbsolutePath());
            String filePath = file.getAbsolutePath().replace("\\","/");
            try {
                if(filePath.indexOf(".json") > 0 || filePath.indexOf(".txt") > 0) {
                    if(filePath.indexOf("twitter") > 0) {
                        fileCreatedHanlder.handleTwitterFile(filePath);
//                    threadPoolExecutor.submit((Runnable) () -> fileCreatedHanlder.handleTwitterFile(filePath));
                    } else if(filePath.indexOf("facebook") > 0) {
                        fileCreatedHanlder.handleFacebookFile(filePath);
//                    threadPoolExecutor.submit((Runnable) () -> fileCreatedHanlder.handleFacebookFile(filePath));
                    } else {
                        fileCreatedHanlder.handleWebFile(filePath);
//                    threadPoolExecutor.submit((Runnable) () -> fileCreatedHanlder.handleWebFile(filePath));
                    }
                }
            } catch (Exception e) {
                logger.error("fileCreated ERROR:", e);
            }

            logger.info("end");
        }

        @Override
        public void onFileChange(File file) {
            logger.info("onFileCreate : " + file.getName());
        }

        @Override
        public void onFileDelete(File file) {
            logger.info("onFileDelete :" + file.getName());
        }

        @Override
        public void onStop(FileAlterationObserver observer) {
            logger.info("onStop");
        }

    }

    @Override
    public void afterPropertiesSet() {
        FileMonitor m ;
        try {
            m = new FileMonitor(5000L);
            m.monitor(monitorPath, new FileListener());
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
