package com.finbyte.million.entities;

import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created on 01.06.20.
 */
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;

    public Company() {
    }

    public Company(String name, String address) {
        this.name = name;

        this.address = address;
    }
    public static final String randomName(){
        return RandomStringUtils.random(10, true, false);
    }
    public static final String randomAddress() {
        return RandomStringUtils.random(16, true, false);
    }

    public Company setAddress(final String address) {
        this.address = address;
        return this;

    }
}
