package com.isinonet.wdview.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wangmingming on 2019/5/6.
 */
public class FileUtil {

    public static void main(String[] args) throws FileNotFoundException {



    }

    public static void writeLines(String filePath, List<String> lines) {

        File file = new File(filePath);
        File parent = file.getParentFile();
        try {
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileUtils.writeLines(file, lines);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static void copyDir(String oldPath, String newPath) throws IOException {

        File file = new File(oldPath);
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + File.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }

            if (new File(oldPath + File.separator + filePath[i]).isFile()) {
                File source = new File(oldPath + File.separator + filePath[i]);
                File dest = new File(newPath + File.separator + filePath[i]);
                if (!(dest.exists())) {
                    Files.copy(source.toPath(), dest.toPath());
                }

            }
        }

    }

    public static void toZip(String sourceDir, String toDir) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(toDir));
            toZip(sourceDir, fos,true);
        } catch (Exception e) {
            try {
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }



    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);

            File sourceFile = new File(srcDir);

            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);

            long end = System.currentTimeMillis();

            System.out.println("压缩完成，耗时：" + (end - start) + " ms");

        } catch (Exception e) {

            throw new RuntimeException("zip error from ZipUtils", e);

        } finally {

            if (zos != null) {

                try {

                    zos.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }


    private static void compress(File sourceFile, ZipOutputStream zos, String name,

                                 boolean KeepDirStructure) throws Exception {

        byte[] buf = new byte[1024];

        if (sourceFile.isFile()) {

            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字

            zos.putNextEntry(new ZipEntry(name));

            // copy文件到zip输出流中

            int len;

            FileInputStream in = new FileInputStream(sourceFile);

            while ((len = in.read(buf)) != -1) {

                zos.write(buf, 0, len);

            }

            // Complete the entry

            zos.closeEntry();

            in.close();

        } else {

            File[] listFiles = sourceFile.listFiles();

            if (listFiles == null || listFiles.length == 0) {

                // 需要保留原来的文件结构时,需要对空文件夹进行处理

                if (KeepDirStructure) {

                    // 空文件夹的处理

                    zos.putNextEntry(new ZipEntry(name + "/"));

                    // 没有文件，不需要文件的copy

                    zos.closeEntry();

                }


            } else {

                for (File file : listFiles) {

                    // 判断是否需要保留原来的文件结构

                    if (KeepDirStructure) {

                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,

                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了

                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);

                    } else {

                        compress(file, zos, file.getName(), KeepDirStructure);

                    }


                }

            }

        }

    }




    public static byte[] readFileToBytes(String filePath) {

        File file = new File(filePath);
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
