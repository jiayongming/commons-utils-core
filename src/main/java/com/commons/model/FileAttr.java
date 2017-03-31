package com.commons.model;

import lombok.Data;

import java.util.Date;

/**
 * FTP文件熟悉
 */
@Data
public class FileAttr {

    private String fileName;
    private Date modifyTime;
    private Long size;

}
