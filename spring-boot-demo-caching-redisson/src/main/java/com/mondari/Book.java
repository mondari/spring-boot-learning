package com.mondari;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 踩坑记录（Redisson）：必须实现序列化。另外，如果使用JSON来序列化的化，必须添加无参构造方法
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book implements Serializable {

    private String isbn;
    private String title;

}