package com.test.service;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxSharedLink;

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

    String mkdir(String parentFolderId,String folderName) throws Exception;
    String mkdirs(String parentFolderId,String paths) throws Exception;

    boolean exists(String folderId,String relativePath,String ftype);
    boolean exists(String folderId,String relativePath);
    String getFID(String folderId,String relativePath,String ftype);
    String getFID(String folderId,String relativePath);
    BoxSharedLink shared(String fId, boolean canDownload, boolean canPreview, boolean canEdit);
    BoxSharedLink shared(String fId);
    boolean move(BoxFile file_from, BoxFolder folder_to, String name_rule_format);
    boolean move(BoxFile file_from, BoxFolder folder_to);
    boolean move(String file_from_id,String folder_to_id,String name_rule_format);
    boolean move(String file_from_id,String folder_to_id);

}
