package com.mondar;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 * @auhtor Christoph Strobl
 */
@Data
@Builder
@Document(indexName = "conference", type = "geo-class-point-type")
public class Conference {

    private @Id
    String id;

    /**
     * 踩坑记录：一定要把analyzer放在最前面才能设置mapping分词
     */
    @Field(analyzer = "ik_smart", type = FieldType.Text)
    private String name;

    private @Field(type = Date)
    String date;

    private GeoPoint location;

    private List<String> keywords;

    // do not remove it
    public Conference() {
    }

    // do not remove it - work around for lombok generated constructor for all params
    public Conference(String id, String name, String date, GeoPoint location, List<String> keywords) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.keywords = keywords;
    }
}

