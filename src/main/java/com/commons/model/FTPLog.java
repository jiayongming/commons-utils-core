package com.commons.model;

import com.commons.time.TimeUtil;
import lombok.Data;

/**
 * Created by jiayongming on 2015-04-08.
 */
@Data
public class FTPLog {
    private String host;
    private String operation;
    private int replyCode;
    private String localFile;
    private String remoteFile;
    private String replyCodeDesc;
    private String createTime = TimeUtil.currentDateTime();

}
