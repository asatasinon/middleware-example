package com.raven.middleware.example.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.api.IDataInit;

/**
 * @author raven
 * @date 2024/6/21 11:23
 * @description
 */
@Slf4j
@Component
public class ReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private List<IDataInit> dataInits;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (IDataInit dataInit : dataInits) {
            try {
                dataInit.preInitData();
                dataInit.postInitData();
                dataInit.afterInitData();
            } catch (Exception e) {
                log.error("class: {} init data error, ", dataInit.getClass().getSimpleName(), e);
            }
        }
        log.info("ReadyEventListener onApplicationEvent");
    }
}
