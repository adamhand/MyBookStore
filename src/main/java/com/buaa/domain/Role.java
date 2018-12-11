package com.buaa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable {
    private String id;
    private String name;
    private String description;

    private List<Function> functions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Function> getFunctions() {
        return functions;
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

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }
}
