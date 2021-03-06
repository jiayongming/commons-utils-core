package com.commons.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class DefaultConfig {

    /*获取CLASS_PATH*/
    public static String CLASS_PATH = "";

    public static Map<String, String> INIT_MAP = Maps.newHashMap() ;

    static {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = contextClassLoader.getResource("default.properties").openStream()) {
            CLASS_PATH = new File(contextClassLoader.getResource("").toURI()).getPath();


            INIT_MAP = PropertiesUtil.properties(inputStream);

            //获取非jar包内的配置信息
            try {
                String config_file = new File(contextClassLoader.getResource("default.properties").toURI()).getPath();
                Map<String, String> CONFIG_MAP = PropertiesUtil.getAllProperties(config_file);
                INIT_MAP.putAll(CONFIG_MAP);
            } catch (Exception e) {
            }

        } catch (IOException | URISyntaxException e) {
            log.error("init config error:" + e.getMessage());
        }
    }


    /*主机特征码*/
    public static final String HOST_FEATURE = INIT_MAP.get("HOST_FEATURE");

    /*日期时间类型格式*/
    public static final String DATETIME_FORMAT = INIT_MAP.get("DATETIME_FORMAT");

    /*日期类型格式*/
    public static final String DATE_FORMAT = INIT_MAP.get("DATE_FORMAT");

    /*时间类型的格式*/
    public static final String TIME_FORMAT = INIT_MAP.get("TIME_FORMAT");

    /**
     * 获取配置信息
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return INIT_MAP.get(key);
    }

    public static void main(String[] args) {

    }
}
