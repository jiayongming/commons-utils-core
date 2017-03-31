package com.commons.util.ftp;

import com.commons.model.FTPVo;
import com.commons.util.DefaultConfig;

import java.io.IOException;

/**
 * FTP工具类
 */
public class FTPFactory {


    //获取一个实例
    public static FTPUtil getInstance(String name) throws IOException {

        String host = DefaultConfig.get(name + ".host");
        if (host != null) {
            int port = Integer.parseInt(DefaultConfig.get(name + ".port"));
            String username = DefaultConfig.get(name + ".username");
            String password = DefaultConfig.get(name + ".password");
            String remoteDir = DefaultConfig.get(name + ".remoteDir");
            String localDir = DefaultConfig.get(name + ".localDir");
            String Encoding = DefaultConfig.get(name + ".Encoding");
            boolean passiveMode = Boolean.parseBoolean(DefaultConfig.get(name + ".passiveMode"));
            FTPVo vo = new FTPVo(host, port, username, password, remoteDir, localDir, Encoding, passiveMode);
            return new FTPUtilImpl(vo);
        } else {
            throw new IOException("config error");
        }
    }
}
