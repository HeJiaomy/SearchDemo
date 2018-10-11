package com.softisland.searchdemo.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softisland.searchdemo.R;
import com.softisland.searchdemo.SearchNameManager;
import com.softisland.searchdemo.entity.HistorySearchList;
import com.softisland.searchdemo.entity.HistorySearchName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class HistorySearchListViewBinder extends ItemViewBinder<HistorySearchList, HistorySearchListViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_history_search_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HistorySearchList list) {
        holder.removeAll.setOnClickListener(v -> {
            SearchNameManager.getInstance().removeAll();
            list.setList(new ArrayList<>());
            getAdapter().notifyDataSetChanged();
        });
        holder.setData(list.getList());
        if (list.unEmpty()){
            holder.removeAll.setVisibility(View.VISIBLE);
        }else {
            holder.removeAll.setVisibility(View.GONE);
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.history_search_tv)
        TextView historySearchTv;
        @BindView(R.id.history_recyclerView)
        RecyclerView historyRecyclerView;
        @BindView(R.id.search_history_remove_all_tv)
        TextView removeAll;

        Items items;
        MultiTypeAdapter adapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            items = new Items();
            adapter = new MultiTypeAdapter(items);
            adapter.register(HistorySearchName.class, new HistorySearchNameViewBinder());
            historyRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            historyRecyclerView.setAdapter(adapter);
        }

        public void setData(List<HistorySearchName> list) {
            if (list != null) {
                items.clear();
                items.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
