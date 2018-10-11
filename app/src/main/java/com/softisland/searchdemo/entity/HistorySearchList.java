package com.softisland.searchdemo.entity;

import java.util.List;

public class HistorySearchList {
    private List<HistorySearchName> list;

    public List<HistorySearchName> getList() {
        return list;
    }

    public void setList(List<HistorySearchName> list) {
        this.list = list;
    }

    public boolean unEmpty() {
        return list != null && !list.isEmpty();
    }
}
