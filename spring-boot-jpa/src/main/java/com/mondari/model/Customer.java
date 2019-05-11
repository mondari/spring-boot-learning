package com.mondari.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 唯一索引可以在 @Index 或 @Column注解里添加 unique = true 属性来定义
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(indexes = {@Index(name = "index_name", columnList = "firstName, lastName")})
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String firstName;
    private String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
