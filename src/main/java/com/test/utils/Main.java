package com.test.utils;

import com.box.sdk.*;

import java.io.IOException;

/**
 * @Author: 吴成伟
 * @date: 2022/8/11 22:32
 * @Description: TODO
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BoxAPIConnection api = BoxConfig.getApi();
        String id = BoxFolder.getRootFolder(api).getID();
        System.out.println("id"+id);
        String path = "";
        String localFilePath = "D:/ChromeCoreDownloads/download/1.txt";
        String boxFolderId = "169494271897";
        String targetFileName = "";
        String targetFileDesc = "";
        ProgressListener listener = null;
//        BoxUtils.uploadFile(api,boxFolderId,localFilePath);

        BoxFile boxFile = new BoxFile(api,boxFolderId);
        Iterable<BoxItem.Info> childrens = new BoxFolder(api, "0").getChildren();
        BoxFolder uploader = null;
        for (BoxItem.Info children : childrens) {
            System.out.println("123:"+children.getName());
            System.out.println(children.getID());
            uploader = new BoxFolder(api,children.getID());
        }
        for (BoxItem.Info child : uploader.getChildren()) {
            System.out.println(":::"+child.getName());
        }
    }
}
