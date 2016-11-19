package com.luojiash.main.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.luojiash.main.service.MqRpcService;

@Service("mqRpcService")
public class MqRpcServiceImpl implements MqRpcService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String send(String s) {
        logger.info(s);
        return "I receive: " + s;
    }

}
