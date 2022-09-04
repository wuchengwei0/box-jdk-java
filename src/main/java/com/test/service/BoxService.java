package com.test.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: 吴成伟
 * @date: 2022/9/4 22:21
 * @Description: TODO
 */
public interface BoxService {
    boolean uploadFile(String boxFolderId,String boxFolder,String localFilePath,String user_id)throws IOException;
    boolean uploadFile(String boxFolderId, String boxFolder, File localFile, String user_id) throws IOException;
    void downloadFile(String boxFileId, String boxFolder, OutputStream localOutputStream)throws IOException;
}
