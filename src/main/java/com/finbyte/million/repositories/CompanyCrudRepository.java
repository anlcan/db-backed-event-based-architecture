package com.finbyte.million.repositories;

import com.finbyte.million.entities.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 01.06.20.
 */
@Repository
public interface CompanyCrudRepository extends CrudRepository<Company, Long> {

}
