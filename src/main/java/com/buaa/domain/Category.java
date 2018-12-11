package com.buaa.domain;

import java.io.Serializable;

/**
 * 图书的分类。
 */
public class Category implements Serializable{
    private String id;             //图书的分类号
    private String name;           //图书的分类名
    private String description;    //额外描述

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
