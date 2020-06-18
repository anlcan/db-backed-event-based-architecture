package com.finbyte.million;

import com.finbyte.million.entities.Company;
import com.finbyte.million.repositories.CompanyCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Component
public class MillionApplication implements ApplicationRunner {

    @Autowired
    private CompanyCrudRepository repository;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MillionApplication.class);
        app.run(args);
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        final Company s = new Company(
                Company.randomName(),
                Company.randomAddress()
        );
        repository.save(s);

    }
}
