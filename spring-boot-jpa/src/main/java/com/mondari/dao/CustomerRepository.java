package com.mondari.dao;

import com.mondari.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

}
