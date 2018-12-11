package com.buaa.domain;

import java.io.Serializable;

/**
 * 图书的属性描述。
 */
public class Book implements Serializable {
    private String id;            //图书的ID
    private String name;          //书名
    private String author;        //作者
    private float price;          //价格
    private String path;          //image的存放路径
    private String filename;      //image的文件名
    private String description;   //图书的描述
    private Category category;    //图书的分类属性

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public float getPrice() {
        return price;
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
