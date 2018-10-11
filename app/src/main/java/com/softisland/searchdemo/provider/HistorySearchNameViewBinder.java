package com.softisland.searchdemo.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softisland.searchdemo.R;
import com.softisland.searchdemo.SearchNameManager;
import com.softisland.searchdemo.entity.HistorySearchName;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class HistorySearchNameViewBinder extends ItemViewBinder<HistorySearchName, HistorySearchNameViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_history_search_name, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final HistorySearchName bean) {
        holder.historySearchTv.setOnClickListener(v -> {
            EventBus.getDefault().post(bean);
        });
        holder.historySearchTv.setText(bean.getName());
        if (bean.getId() < 1) {
            holder.searchHistoryCloseIv.setVisibility(View.INVISIBLE);
        } else {
            holder.searchHistoryCloseIv.setVisibility(View.VISIBLE);
        }
        holder.searchHistoryCloseIv.setOnClickListener(v -> {
            SearchNameManager.getInstance().removeName(bean);
            getAdapter().getItems().remove(bean);
            getAdapter().notifyDataSetChanged();
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.history_search_tv)
        TextView historySearchTv;
        @BindView(R.id.search_history_close_iv)
        ImageView searchHistoryCloseIv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
