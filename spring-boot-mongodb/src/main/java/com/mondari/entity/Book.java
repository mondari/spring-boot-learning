package com.mondari.entity;

/**
 * 书本
 */
public class Book {
    private String id;
    private String name;//名称
    private String writer;//作者
    private String press;//出版社
    private Double price;//价格(用Float类型会导致精度问题)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", writer='" + writer + '\'' +
                ", press='" + press + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

}