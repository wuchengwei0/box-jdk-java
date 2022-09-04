package com.test.Chain;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 吴成伟
 * @date: 2022/8/17 10:22
 * @Description: TODO
 */
public class ff {
    public  void main1(String[] args) throws IOException {
        String path = "";
        runExe(path,null);
        String a = null;
        if(StringUtils.isEmpty(a)){

        }
    }

    /**
     * commands: command path + command params
     * @param commands
     * @throws Exception
     */
    public static void runExe(String exePath,List<String> commands){
        if( commands == null ){
            commands = new ArrayList<>();
        }
        // set commands: path + command params
        commands.add(0,exePath);
        // to Array
        String[] cmdArray = commands.stream().toArray(String[]::new);

        BufferedReader in = null;
        PrintWriter out = null;
        try{
            // exec .exe file
            Process process = Runtime.getRuntime().exec(cmdArray);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())), true);
            for (String line : commands){
                out.println(line);
            }
            out.println("exit");
            String rspLine = "";

            List<String> rspList = new ArrayList<>();
            while ((rspLine = in.readLine()) != null) {
                System.out.println(rspLine);
                rspList.add(rspLine);
            }
            process.waitFor();
        }catch (Exception e){

        }finally {
//            in.close();
//            out.close();
//            process.destroy();
        }
    }
}
