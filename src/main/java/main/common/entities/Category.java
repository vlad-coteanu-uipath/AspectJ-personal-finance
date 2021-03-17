package main.common.entities;

import java.io.Serializable;

public class Category implements Serializable {

    private Integer id;
    private String name;
    private Integer userId;

    public Category(Integer id, String name, Integer userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
