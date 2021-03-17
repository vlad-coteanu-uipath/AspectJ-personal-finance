package main.common.entities;

import java.io.Serializable;

public class Expense implements Serializable {

    private Integer id;
    private String name;
    private Integer categoryId;
    private Integer userId;
    private Integer value;

    public Expense(Integer id, String name, Integer categoryId, Integer userId, Integer value) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.userId = userId;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
