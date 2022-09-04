package com.test.utils;

import com.box.sdk.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Author: 吴成伟
 * @date: 2022/8/12 23:30
 * @Description: TODO
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
//        BoxAPIConnection api = com.test.config.BoxConfig.getApi();
//        String boxFolderId = "169494271897";
//        List<BoxItem.Info> infos = BoxUtils.listFiles(api, boxFolderId);
//        for (BoxItem.Info info : infos) {
//            info.getSize();
//            info.getType().equals("folder"); //
//            info.getType().equals("file");  //
//        }
    }
    @Test
    public void upload() throws IOException {
        BoxAPIConnection api = com.test.utils.BoxConfig.getApi();
        String boxFolderId = "169494271897";
//        String localFilePath = "D:/ChromeCoreDownloads/download/1.txt";
        String localFilePath = "D:/ChromeCoreDownloads/download/12.xlsx";
        BoxFile.Info test_upload = BoxUtils.uploadFile(api, boxFolderId, localFilePath, "12.xlsx", "test upload", null);
    }
    @Test
    public void ddd() throws IOException {
        BoxAPIConnection api = com.test.utils.BoxConfig.getApi();
        BoxFile boxFile = new BoxFile(api,"995657866633");
        System.out.println("?");
        System.out.println(boxFile.getDownloadURL().toString());
        System.out.println("!");
    }
    @Test
    public void tests() throws IOException{
        BoxAPIConnection api = BoxConfig.getApi();
        BoxFolder boxFolder = new BoxFolder(api,"169494271897");
    }
}
