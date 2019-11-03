package com.mondar;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {
}
