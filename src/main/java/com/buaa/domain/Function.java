package com.buaa.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 角色函数，被role调用
 */
public class Function implements Serializable {
    private String id;
    private String name;
    private String url;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function)) return false;
        Function function = (Function) o;
        return Objects.equals(getId(), function.getId()) &&
                Objects.equals(getName(), function.getName()) &&
                Objects.equals(getUrl(), function.getUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getUrl());
    }
}
