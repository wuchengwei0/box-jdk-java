import cn.hutool.core.io.resource.ClassPathResource;
import com.box.sdk.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void ds() throws  Exception{
        BoxAPIConnection api = getApi();
        BoxFolder boxFolder = new BoxFolder(api,"169494271897");


        Iterable<BoxItem.Info> children = BoxFolder.getRootFolder(api).getChildren();
        for (BoxItem.Info child : children) {
            System.out.println(child.getName());
            String id = child.getID();
            System.out.println("id="+id);
        }
    }
    @Test
    public void test() throws IOException {


        BoxAPIConnection api = getApi();

        BoxFolder rootFolder = BoxFolder.getRootFolder(api);


        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
        System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());

        listFolder(rootFolder,5);
        System.out.println("listFolder???");

        for (BoxItem.Info info : rootFolder) {
            System.out.println("?"+info.getName());
        }
        BoxFolder boxFolder = new BoxFolder(api, "");
        for (BoxItem.Info info : boxFolder) {
            System.out.println("!!!"+info.getName());
        }

    }
    private void listFolder(BoxFolder folder, int depth) {
        for (BoxItem.Info itemInfo : folder) {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("    ");
            }

            System.out.println("-----"+indent + itemInfo.getName());
            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                if (depth < MAX_DEPTH) {
                    listFolder(childFolder, depth + 1);
                }
            }
        }
    }
}
