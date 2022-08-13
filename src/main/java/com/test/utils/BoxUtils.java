package com.test.utils;


import com.box.sdk.*;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author: 吴成伟
 * @date: 2022/8/11 22:28
 * @Description: TODO
 */
public class BoxUtils {
    private final static Logger logger = LoggerFactory.getLogger(BoxUtils.class);

    public static BoxFile.Info uploadFile(BoxAPIConnection api, String boxFolderId, File localFile, String targetFileName, String targetFileDesc, ProgressListener listener) throws FileNotFoundException {
        if( api == null )
            logger.error("BoxAPIConnection should not be null");
        if( boxFolderId == null || boxFolderId.trim().equals("") )
            logger.error("boxFolderId should not be empty");
        BoxFolder folder = new BoxFolder(api,boxFolderId);
        if( localFile.exists() && localFile.isFile() ){
            long localFileSize = localFile.length();
            targetFileName = targetFileName == null || targetFileName.equals("") ? localFile.getName() : targetFileName;
            folder.canUpload(targetFileName,localFileSize);
            InputStream in = new FileInputStream(localFile);
            FileUploadParams params = new FileUploadParams();
            params.setContent(in);
            params.setName(targetFileName);
            params.setDescription(targetFileDesc);
            params.setProgressListener(listener);
            return folder.uploadFile(params);
        }else{
            logger.error("localFilePath is not exists or is not file");
            return null;
        }
    }
    public static BoxFile.Info uploadFile(BoxAPIConnection api, String boxFolderId, String localFilePath, String targetFileName, String targetFileDesc, ProgressListener listener) throws FileNotFoundException {
        if (localFilePath == null || localFilePath.trim().equals("")) {
            logger.error("localFilePath can't be empty");
        }
        File localFile = new File(localFilePath);
        return uploadFile(api, boxFolderId, localFile, targetFileName, targetFileDesc, listener);
    }
    public static BoxFile.Info uploadFile(BoxAPIConnection api, String boxFolderId, File localFile) throws FileNotFoundException {
        return uploadFile(api, boxFolderId, localFile, null, null, null);
    }
    public static BoxFile.Info uploadFile(BoxAPIConnection api, String boxFolderId, String localFilePath) throws FileNotFoundException {
        return uploadFile(api, boxFolderId, localFilePath, null, null, null);
    }

    public static void downloadFile(BoxAPIConnection api,String boxFileId,OutputStream localOutPutStream,ProgressListener listener) throws IOException{
        BoxFile file = new BoxFile(api,boxFileId);
        if( listener != null )
            file.download(localOutPutStream,listener);
        else
            file.download(localOutPutStream);
        localOutPutStream.close();
    }
    public static void downloadFile(BoxAPIConnection api,String boxFileId,OutputStream outputStream ) throws IOException{
        downloadFile(api,boxFileId,outputStream,null);
    }
    public static void downloadFile(BoxAPIConnection api,String boxFileId,String localFilePath) throws IOException{
        OutputStream out = new FileOutputStream(localFilePath);
        downloadFile(api,boxFileId,out);
    }
    public static void downloadFile(BoxAPIConnection api,String boxFileId,String localFilePath,ProgressListener listener){
        downloadFile(api,boxFileId,localFilePath,null);
    }
    public static void downloadFileToDir(BoxAPIConnection api,String boxFileId,String localDir,ProgressListener listener) throws IOException{
        BoxFile boxFile = new BoxFile(api,boxFileId);
        BoxFile.Info info = boxFile.getInfo();
        String fileName = info.getName();
        String localFilePath = null;
        if( localDir.endsWith("/") || localDir.endsWith("\\") )
            localFilePath = localDir + fileName;
        else
            localFilePath = localDir + File.pathSeparator + fileName;
        downloadFile(api,boxFileId,localFilePath,listener);
    }
    public static void downloadFileToDir(BoxAPIConnection api, String boxFileId, String localDir) throws IOException {
        downloadFileToDir(api, boxFileId, localDir, null);
    }
    public static List<BoxItem.Info> listFiles(BoxAPIConnection api, String folderId) {
        BoxFolder folder = null;
        if (folderId == null || folderId.trim().equals("") || folderId.trim().equals("/")) {
            folder = BoxFolder.getRootFolder(api);
        } else {
            folder = new BoxFolder(api, folderId);
        }
        List<BoxItem.Info> list = new ArrayList<BoxItem.Info>();
        for (BoxItem.Info itemInfo : folder) {
            System.out.println(itemInfo.getType());
            list.add(itemInfo);
        }
        return list;
    }

    /**
     * 获取同一天的所有文件
     * @param api
     * @param folderId
     * @param startDate
     * @return
     */
    public static List<BoxItem.Info> getSameDayFiles(BoxAPIConnection api, String folderId, Date startDate){
        BoxFolder boxFolder = new BoxFolder(api, folderId);
        List<BoxItem.Info> list = new ArrayList<>();
        for (BoxItem.Info info : boxFolder) {
            Date createDate = info.getCreatedAt();
            String type = info.getType();
            if( "folder".equals(type) && DateUtils.isSameDay(createDate,startDate)){
                list.add(info);
            }
        }
        return list;
    }
    public static List<BoxItem.Info> getTodayFiles(BoxAPIConnection api, String folderId){
        return getSameDayFiles(api,folderId,new Date());
    }
    public static List<BoxItem.Info> getBeforeOrAfterFileList(BoxAPIConnection api, String folderId, Date startDate,Boolean isAfter){
        BoxFolder boxFolder = new BoxFolder(api, folderId);
        List<BoxItem.Info> list = new ArrayList<>();
        for (BoxItem.Info info : boxFolder) {
            Date createDate = info.getCreatedAt();
            if( createDate.getTime() > startDate.getTime() && isAfter ){
                list.add(info);
            }else{
                list.add(info);
            }
        }
        return list;
    }
    public static String getDownLoadURL(BoxAPIConnection api,String folderId){
        BoxFile boxFile = new BoxFile(api,folderId);
        URL downloadURL = boxFile.getDownloadURL();
        return downloadURL.toString();
    }
}
