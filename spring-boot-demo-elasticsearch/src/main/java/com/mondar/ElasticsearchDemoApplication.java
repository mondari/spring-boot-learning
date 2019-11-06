package com.mondar;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ElasticsearchDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchDemoApplication.class, args);
    }

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    ConferenceRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // 查询
        System.out.println("————————查询————————");
        String expectedDate = "2014-10-29";
        String expectedWord = "java";
        CriteriaQuery query = new CriteriaQuery(
                new Criteria("keywords").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));
        List<Conference> conferenceList = restTemplate.queryForList(query, Conference.class);
        conferenceList.forEach(conference -> {
            System.out.println(JSON.toJSONString(conference, true));
        });

        // 更新
        System.out.println("————————更新————————");
        conferenceList.forEach(conference -> {
            conference.setName(conference.getName().concat(" is a complete success"));
            UpdateRequest doc = new UpdateRequest().doc(JSON.toJSONString(conference), XContentType.JSON);
            UpdateQuery updateQuery = new UpdateQueryBuilder()
                    .withClass(Conference.class)
                    .withId(String.valueOf(conference.getId()))
                    .withUpdateRequest(doc)
                    .build();
            UpdateResponse update = restTemplate.update(updateQuery);
            System.out.println(JSON.toJSONString(update, true));
        });

        // 删除索引
        deleteIndex();
    }

    @PostConstruct
    public void insertDataSample() {
        // 删除所有的文档
        repository.deleteAll();

        // Save data sample
        repository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
                .keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London")
                .keywords(Arrays.asList("scala", "play", "java")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin")
                .keywords(Arrays.asList("java", "elasticsearch", "kibana")).location(new GeoPoint(52.5234051D, 13.4113999))
                .build());
        repository.save(Conference.builder().date("2014-11-12").name("AWS London 2014")
                .keywords(Arrays.asList("cloud", "aws")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow")
                .keywords(Arrays.asList("java", "spring")).location(new GeoPoint(50.0646501D, 19.9449799)).build());
    }

    @PreDestroy
    public void deleteIndex() {
        restTemplate.deleteIndex(Conference.class);
    }
}
