package com.mondar;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 *
 * ElasticsearchRepository extends ElasticsearchCrudRepository extends PagingAndSortingRepository extends CrudRepository
 */
public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
