package com.yun.album.common;

import com.yun.album.util.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Context {
    private final Logger logger = LoggerFactory.getLogger(Context.class);
    /** 服务器编号 */
    @Value("${server.id}")
    private int serverId;

    @PostConstruct
    public void init() {
        try {
            IdFactory.instance.init(serverId);
        } catch (Exception e) {
            logger.error("init server error.", e);
        }

    }
}
