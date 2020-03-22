package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info(".... Fetching books");
        log.info("isbn-1234 -->{}", bookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-4567 -->{}", bookRepository.getByIsbn("isbn-4567"));
        log.info("isbn-1234 -->{}", bookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-4567 -->{}", bookRepository.getByIsbn("isbn-4567"));
        log.info("isbn-1234 -->{}", bookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-1234 -->{}", bookRepository.getByIsbn("isbn-1234"));
    }

}