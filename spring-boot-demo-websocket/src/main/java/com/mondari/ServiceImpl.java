package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceImpl {
    public void hello() {
        log.info("====> hello");
    }
}
