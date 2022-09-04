import cn.hutool.core.io.resource.ClassPathResource;
import com.box.sdk.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 吴成伟
 * @date: 2022/8/10 23:07
 * @Description: TODO
 */
public class Box {

    private static final int MAX_DEPTH = 4;
    public BoxConfig getBoxConfig() throws IOException {
        File file = ResourceUtils.getFile("classpath:box-config.json");
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        BoxConfig boxConfig = BoxConfig.readFrom(reader);
        return boxConfig;
    }
    public BoxAPIConnection getApi() throws IOException {
        BoxAPIConnection boxAPIConnection = new BoxAPIConnection(getBoxConfig());
        BoxDeveloperEditionAPIConnection connection = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(getBoxConfig());
        return connection;
    }
    public void dfs(String rootFolderId,String folderPath){
        folderPath.split("/");
    }
    @Test
    public void xv() throws IOException {
        BoxFolder folder = new BoxFolder(getApi(),"169494271897");
        System.out.println(folder.getInfo().getType());
//        String[] folderPaths = "a1/a2/a3".split("/");
//        List<String> paths = Arrays.asList(folderPaths);
//        BoxFolder id = createFolder(folder, paths, 0);
//        System.out.println(id);
    }

    public BoxFolder createFolder(BoxFolder folder, String[] folderPaths, int deep) throws IOException{
        return createFolder(folder,folderPaths,deep);
    }
    public BoxFolder createFolder(BoxFolder folder, List<String> folderPaths, int deep) throws IOException {
        if( deep == folderPaths.size() ){
            return folder;
        }
        System.out.println("dfs1:name"+folder.getInfo().getName());
        Iterator<BoxItem.Info> it = folder.getChildren().iterator();
        while (it.hasNext()){
            BoxItem.Info info = it.next();
            System.out.println(info.getType());
            boolean same = info.getName().toLowerCase().equals(folderPaths.get(deep)) && "folder".equals(info.getType());
            if( same ){
                if( deep+1 == folderPaths.size() ){
                    return new BoxFolder(getApi(),info.getID());
                }else{
                    createFolder(new BoxFolder(folder.getAPI(),info.getID()),folderPaths,deep+1);
                }
            }
            if( same && deep+1 == folderPaths.size() ){
                return new BoxFolder(getApi(),info.getID());
            }
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
