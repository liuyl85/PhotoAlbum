package com.yun.album.common;

import com.yun.album.common.properties.SystemProperties;
import com.yun.album.util.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class Context {
    private final Logger logger = LoggerFactory.getLogger(Context.class);
    @Resource
    private SystemProperties systemProperties;

    @PostConstruct
    public void init() {
        try {
            IdFactory.instance.init(systemProperties.getServerId());
        } catch (Exception e) {
            logger.error("init server error.", e);
            System.exit(0);
        }
    }

}
