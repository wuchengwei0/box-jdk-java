package com.test.config;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author: 吴成伟
 * @date: 2022/8/11 22:27
 * @Description: TODO
 */
@Configuration
public class BoxConfig {
    @Bean("apis")
    public BoxAPIConnection getApi() throws IOException {
        BoxDeveloperEditionAPIConnection connection = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(getBoxConfig());
        return connection;
    }
    public com.box.sdk.BoxConfig getBoxConfig() throws IOException {
        File file = ResourceUtils.getFile("classpath:box-config.json");
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        com.box.sdk.BoxConfig boxConfig = com.box.sdk.BoxConfig.readFrom(reader);
        return boxConfig;
    }
}
