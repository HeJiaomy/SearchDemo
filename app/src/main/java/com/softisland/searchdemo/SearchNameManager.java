package com.softisland.searchdemo;

import com.softisland.searchdemo.app.MyApplication;
import com.softisland.searchdemo.entity.HistorySearchName;
import com.softisland.searchdemo.entity.HistorySearchName_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class SearchNameManager {
    private static volatile SearchNameManager searchNameManager;

    private Box<HistorySearchName> searchNameBox;

    public static SearchNameManager getInstance() {
        if (searchNameManager == null) {
            synchronized (SearchNameManager.class) {
                if (searchNameManager == null) {
                    searchNameManager = new SearchNameManager();
                }
            }
        }
        return searchNameManager;
    }

    private SearchNameManager() {
        BoxStore boxStore = MyApplication.getBoxStore();
        searchNameBox = boxStore.boxFor(HistorySearchName.class);
    }


    public List<HistorySearchName> queryHistory() {
        Query<HistorySearchName> queryBuilder = searchNameBox.query()
                .build();
        return queryBuilder.find();
    }

    public void removeName(HistorySearchName name) {
        searchNameBox.remove(name);
    }

    public boolean updateAndSaveName(HistorySearchName name) {
        HistorySearchName searchName = queryHistory(name.getName());
        if (searchName!= null) {
            saveName(searchName);
        }else {
            saveName(name);
            return false;
        }
        return true;
    }

    private void saveName(HistorySearchName name) {
        searchNameBox.put(name);
    }

    private HistorySearchName queryHistory(String name) {
        Query<HistorySearchName> queryBuilder = searchNameBox.query()
                .equal(HistorySearchName_.name, name)
                .build();
        return queryBuilder.findUnique();
    }

    public void removeAll(){
        searchNameBox.removeAll();
    }
}
