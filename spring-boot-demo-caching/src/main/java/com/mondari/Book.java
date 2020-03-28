package com.mondari;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 踩坑记录：必须要添加无参构造方法，否则反序列化报错
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    private String isbn;
    private String title;

}