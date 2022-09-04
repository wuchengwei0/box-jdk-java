package com.test.utils;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @Author: 吴成伟
 * @date: 2022/8/11 22:27
 * @Description: TODO
 */
public class BoxConfig {
    public static BoxAPIConnection getApi() throws IOException {
        BoxDeveloperEditionAPIConnection connection = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(getBoxConfig());
        return connection;
    }
    public static com.box.sdk.BoxConfig getBoxConfig() throws IOException {
        File file = ResourceUtils.getFile("classpath:box-config.json");
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        com.box.sdk.BoxConfig boxConfig = com.box.sdk.BoxConfig.readFrom(reader);
        return boxConfig;
    }
}
