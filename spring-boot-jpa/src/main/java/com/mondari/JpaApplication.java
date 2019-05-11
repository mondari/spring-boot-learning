package com.mondari;

import com.mondari.dao.CustomerRepository;
import com.mondari.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.mondari.dao")
@SpringBootApplication
public class JpaApplication {

    private static final Logger LOG = LoggerFactory.getLogger(JpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(CustomerRepository repository) {
        return args -> {
            // 添加一堆顾客（主键会自动生成）
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Pauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));

            // fetch all customers
            LOG.info("Customers found with findAll():");
            for (Customer customer : repository.findAll()) {
                LOG.info(customer.toString());
            }
            // 空行
            LOG.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                    .ifPresent(customer -> {
                        LOG.info("Customer found with findById(1L):");
                        LOG.info(customer.toString());
                        LOG.info("");
                    });

            // fetch customers by last name (自定义方法)
            LOG.info("Customer found with findByLastName('Bauer'):");
            repository.findByLastName("Bauer").forEach(bauer -> {
                LOG.info(bauer.toString());
            });
            LOG.info("");

            LOG.info("最后请打开 http://localhost:8080/h2-console 访问 H2数据库Web控制台!");
        };
    }

}

