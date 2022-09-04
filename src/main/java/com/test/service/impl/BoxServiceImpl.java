package com.test.service.impl;

import com.box.sdk.*;
import com.test.service.BoxService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: 吴成伟
 * @date: 2022/9/4 22:23
 * @Description: TODO
 */
@Service
public class BoxServiceImpl implements BoxService {
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
            BoxFolder rootFolder = new BoxFolder(api, boxFolderId);
            BoxFolder folder = createFolder(rootFolder, boxFolder.split("/"), 0);

            folder.canUpload(localFile.getName(),localFile.length());

            FileUploadParams params = new FileUploadParams();
            FileInputStream in = new FileInputStream(localFile);
            params.setContent(in);
            params.setName(localFile.getName());
            if(StringUtils.isNotEmpty(user_id))
                params.setDescription(user_id);
            folder.uploadFile(params);
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            return result;
        }
    }

    @Override
    public void downloadFile(String boxFileId, String boxFolder,OutputStream localOutputStream)throws IOException{
        BoxFile file = new BoxFile(api, boxFileId);
        file.download(localOutputStream);
        localOutputStream.close();
    }

    public String getFolderID(BoxFolder folder,String[] folderPaths) throws IOException {
        BoxFolder target_folder = createFolder(folder, Arrays.asList(folderPaths), 0);
        return target_folder.getID();
    }
    public String getFolderID(BoxFolder folder,List<String> folderPaths) throws IOException {
        BoxFolder target_folder = createFolder(folder, folderPaths, 0);
        return target_folder.getID();
    }
    public BoxFolder createFolder(BoxFolder folder, String[] folderPaths, int deep) throws IOException{
        return createFolder(folder,Arrays.asList(folderPaths),deep);
    }
    public BoxFolder createFolder(BoxFolder folder, List<String> folderPaths, int deep) throws IOException {
        if( deep == folderPaths.size() ){
            return folder;
        }
        System.out.println("dfs1:name"+folder.getInfo().getName());
        Iterator<BoxItem.Info> it = folder.getChildren().iterator();
        while (it.hasNext()){
            BoxItem.Info info = it.next();
            if( !"folder".equals(info.getType()) )
                continue;
            System.out.println(info.getType());
            boolean same = info.getName().toLowerCase().equals(folderPaths.get(deep));
            if( same ){
                if( deep+1 == folderPaths.size() ){
                    return new BoxFolder(api,info.getID());
                }else{
                    return createFolder(new BoxFolder(folder.getAPI(),info.getID()),folderPaths,deep+1);
                }
            }
//            if( same && deep+1 == folderPaths.size() ){
//                return new BoxFolder(api,info.getID());
//            }
        }
        if( deep < folderPaths.size() ){
            BoxFolder.Info folderInfo = folder.createFolder(folderPaths.get(deep));
            BoxFolder boxFolder = new BoxFolder(folder.getAPI(), folderInfo.getID());
            return createFolder(boxFolder,folderPaths,deep+1);
        }else{
            return folder;
        }
    }
}
