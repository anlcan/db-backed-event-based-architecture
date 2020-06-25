package com.finbyte.million.service;

import com.finbyte.million.entities.Company;
import com.finbyte.million.repositories.CompanyCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 18.06.20.
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyCrudRepository repository;

    public Company insert() {
         Company s = new Company(
                Company.randomName(),
                Company.randomAddress()
        );
        return repository.save(s);
    }

    public Company delete() {
        long count = repository.count();
        final Optional<Company> byId = repository.findById(ThreadLocalRandom.current().nextLong(count));
        byId.ifPresent(c -> repository.delete(c));
        return null;
    }


    public Company update() {

        long count = repository.count();
        final Optional<Company> byId = repository.findById(ThreadLocalRandom.current().nextLong(count));
        byId.ifPresent(c -> repository.save(c.setAddress(Company.randomAddress())));
        return null;
    }
}
