package com.test.service.impl;

import com.box.sdk.*;
import com.box.sdk.sharedlink.BoxSharedLinkRequest;
import com.test.service.BoxService;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: 吴成伟
 * @date: 2022/9/4 22:23
 * @Description: TODO
 */
@Service
public class BoxServiceImpl implements BoxService {
    private static final boolean IGNORE_CASE = true;
    private static final String ILLEGAL_CHARACTER = "\\/:*?\"<>|";
    private static final String TYPE_FILE = "file";
    private static final String TYPE_FOLDER = "folder";

    @Test
    public void teset() throws Exception{
        System.out.println("start");
        boolean move = move("1023567014530", "169494677651");
        System.out.println(move);
    }

    @Autowired
    @Qualifier("apis")
    private BoxAPIConnection api;

    @Override
    public boolean uploadFile(String boxFolderId,String boxFolder,String localFilePath,String user_id)throws IOException{
        File file = new File(localFilePath);
        return uploadFile(boxFolderId,boxFolder,file,user_id);
    }
    @Override
    public boolean uploadFile(String boxFolderId,String boxFolder,File localFile,String user_id) throws IOException {
        boolean result = false;
        try{
//            mkdirs(boxFolderId,)
        }catch (Exception e){
            result = false;
        }finally {
            return result;
        }
    }
    public BoxFile.Info uploadFile(String boxFolderId,String file_path,String user_id) throws IOException{
        return uploadFile(boxFolderId,new File(file_path),user_id);
    }
    public BoxFile.Info uploadFile(String boxFolderId,File localFile,String user_id) throws IOException{
        return uploadFile(new BoxFolder(api,boxFolderId),localFile,user_id);
    }
    public BoxFile.Info uploadFile(BoxFolder folder,File localFile,String user_id) throws IOException{
        boolean result = false;
        BoxFile.Info info = null;
        try{
            folder.canUpload(localFile.getName(),localFile.length());
            FileUploadParams params = new FileUploadParams();
            FileInputStream in = new FileInputStream(localFile);
            params.setContent(in);
            params.setName(localFile.getName());
            if(StringUtils.isNotEmpty(user_id))
                params.setDescription(user_id);
            info = folder.uploadFile(params);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return info;
        }
    }

    @Override
    public void downloadFile(String boxFileId, String boxFolder,OutputStream localOutputStream)throws IOException{
        BoxFile file = new BoxFile(api, boxFileId);
        file.download(localOutputStream);
        localOutputStream.close();
    }
    @Override
    public boolean move(String file_from_id,String folder_to_id){
        return move(file_from_id,folder_to_id,null);
    }

    @Override
    public boolean move(String file_from_id,String folder_to_id,String name_rule_format){
        boolean success = false;
        try{
            BoxFile boxFile = new BoxFile(api,file_from_id);
            BoxFolder boxFolder = new BoxFolder(api, folder_to_id);
            boolean info = move(boxFile, boxFolder,name_rule_format);
            success = true;
        }catch (Exception e){
            success = false;
            e.printStackTrace();
        }finally {
            return success;
        }
    }

    @Override
    public boolean move(BoxFile file_from,BoxFolder folder_to){
        return move(file_from,folder_to,null);
    }

    @Override
    public boolean move(BoxFile file_from,BoxFolder folder_to,String name_rule_format){
        boolean success = false;
        try{
            if( StringUtils.isNotEmpty(name_rule_format) ){
                String name = file_from.getInfo().getName();
                String name_prefix = new SimpleDateFormat(name_rule_format).format(new Date());
                BoxItem.Info info = file_from.move(folder_to, name_prefix + name);
            }else{
                BoxItem.Info info = file_from.move(folder_to);
            }
            success = true;
        }catch (Exception e){
            success = false;
            e.printStackTrace();
        }finally {
            return success;
        }
    }

    /**
     * create shared
     * @param fId
     * @return
     */
    @Override
    public BoxSharedLink shared(String fId){
        return shared(fId,true,true,false);
    }
    @Override
    public BoxSharedLink shared(String fId,boolean canDownload,boolean canPreview,boolean canEdit){
        BoxFile boxFile = new BoxFile(api,fId);
        BoxSharedLinkRequest boxSharedLinkRequest = new BoxSharedLinkRequest();
        boxSharedLinkRequest.permissions(canDownload,canPreview,canEdit);
        BoxSharedLink sharedLink = boxFile.createSharedLink(boxSharedLinkRequest);
        return sharedLink;
    }

    /**
     * ensure the file/folder exists
     * @param folderId parent Folder id
     * @param relativePath  file/folder
     * @return exists-->true,not exists--->false
     */
    @Override
    public boolean exists(String folderId,String relativePath){
        return exists(folderId,relativePath,TYPE_FILE);
    }
    @Override
    public boolean exists(String folderId,String relativePath,String ftype){
        String fid = getFID(folderId, relativePath, ftype);
        return StringUtils.isNotEmpty(fid);
    }


    /**
     *
     * @param folderId
     * @param relativePath
     * @return
     */
    @Override
    public String getFID(String folderId,String relativePath){
        return getFID(folderId,relativePath,TYPE_FILE);
    }

    /**
     *
     * @param folderId
     * @param relativePath
     * @param ftype "file" or "folder" and default "file" type
     * @return
     */
    @Override
    public String getFID(String folderId,String relativePath,String ftype){
        String[] paths = getPaths(relativePath);
        if (paths==null||paths.length==0){
            return null;
        }else if( paths.length == 1 ){
            return getID(folderId,paths[0],ftype);
        }else{
            for (int i = 0; i < paths.length-1; i++) {
                folderId = getID(folderId,paths[i],TYPE_FOLDER);
                if( folderId == null ){
                    return null;
                }
            }
            return getID(folderId,paths[paths.length-1],ftype);
        }
    }

    private String getID(String parentFolderId,String fname,String ftype){
        BoxFolder boxFolder = new BoxFolder(api,parentFolderId);
        Iterator<BoxItem.Info> it = boxFolder.getChildren().iterator();
        while (it.hasNext()){
            BoxItem.Info info = it.next();
            String name = info.getName();
            String type = info.getType();
            if( name_equals(name,fname) && type.equals(ftype) ){
                return info.getID();
            }
        }
        return null;
    }

    @Override
    public String mkdirs(String parentFolderId,String paths) throws Exception{
        String[] path_list = getPaths(paths);
        String folderId = parentFolderId;
        for (String path : path_list) {
            folderId = mkdir(folderId,path);
        }
        return folderId;
    }
    /**
     *
     * @param parentFolderId
     * @param folderName
     * @return create new folder's id or exists folder's id
     * @throws Exception
     */
    @Override
    public String mkdir(String parentFolderId,String folderName) throws Exception{
        if( !legal_name(folderName) ){
            throw new Exception("file or folder name is Illegal");
        }
        BoxFolder parentFolder = new BoxFolder(api, parentFolderId);
        Iterator<BoxItem.Info> it = parentFolder.getChildren().iterator();
        boolean created = false;
        while (it.hasNext()){
            BoxItem.Info info = it.next();
            String type = info.getType();
            String box_folder_name = info.getName();
            if( name_equals(box_folder_name,folderName) && "folder".equals(type) ){
                return info.getID();
            }
        }
        if (!created){
            BoxFolder.Info folder = parentFolder.createFolder(folderName);
            return folder.getID();
        }else{
            return null;
        }
    }

    /**
     * split path with '/'
     * @param paths
     * @return
     */
    private String[] getPaths(String paths){
        if( StringUtils.isEmpty(paths) ){
            return null;
        }
        paths = paths.startsWith("/")? paths.substring(1) : paths;
        String[] path_list = paths.split("/");
        return path_list;
    }
    public boolean name_equals(String box_fname,String fname){
        if( IGNORE_CASE ){
            return box_fname.toLowerCase().equals(fname.toLowerCase());
        }else{
            return box_fname.equals(fname);
        }
    }
    public boolean legal_name(String name){
        char[] chars = ILLEGAL_CHARACTER.toCharArray();
        for (char illegal_char : chars) {
            boolean illegal = name.contains(illegal_char + "");
            if(illegal){
                return false;
            }
        }
        return true;
    }
    public String getFType(String ftype){
        if( StringUtils.isEmpty(ftype) ){
            return TYPE_FILE;
        }else{
            ftype = ftype.toLowerCase();
            if( "file".equals(ftype) || "folder".equals(ftype) )
                return ftype;
            else
                return TYPE_FILE;
        }
    }
//------------------------------------------------
    @Before
    public void init() throws IOException {
        System.out.println("api = init");
        api = getApi();
    }
    public com.box.sdk.BoxConfig getBoxConfig() throws IOException {
        File file = ResourceUtils.getFile("classpath:box-config.json");
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        com.box.sdk.BoxConfig boxConfig = com.box.sdk.BoxConfig.readFrom(reader);
        return boxConfig;
    }
    public BoxAPIConnection getApi() throws IOException {
        BoxDeveloperEditionAPIConnection connection = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(getBoxConfig());
        return connection;
    }
}
