package com.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于包装FTP的相关的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FTPVo {
    private String hostName;
    private int port;
    private String username;
    private String password;
    private String remoteDir;
    private String localDir;
    private String remoteEncoding;
    private boolean passiveMode;

}
