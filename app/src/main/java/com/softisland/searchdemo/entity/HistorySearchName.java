package com.softisland.searchdemo.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class HistorySearchName {
    @Id
    public long id;
    private String name;

    public HistorySearchName() {
    }

    public HistorySearchName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
