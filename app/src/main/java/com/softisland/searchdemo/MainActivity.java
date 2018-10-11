package com.softisland.searchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.softisland.searchdemo.entity.HistorySearchList;
import com.softisland.searchdemo.entity.HistorySearchName;
import com.softisland.searchdemo.entity.HistorySearchName_;
import com.softisland.searchdemo.provider.HistorySearchListViewBinder;
import com.softisland.searchdemo.provider.HistorySearchNameViewBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.back_iv)
    AppCompatImageView backIv;
    @BindView(R.id.drop_down_tv)
    TextView dropDownTv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_input_et)
    EditText searchInputEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_goods_list_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.search_refresh_layout)
    SmartRefreshLayout searchRefreshLayout;

    private MultiTypeAdapter adapter;
    private Items items;
    private HistorySearchList historySearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        //可以注册多个adapter，如展示搜索结果，接口返回的搜索名称等。
        adapter.register(HistorySearchList.class, new HistorySearchListViewBinder());
        adapter.register(HistorySearchName.class, new HistorySearchNameViewBinder());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        initHistory();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        searchTv.setOnClickListener(v -> {
            String inputText = searchInputEt.getText().toString();
            if (!TextUtils.isEmpty(inputText)) {
                HistorySearchName historySearchName = new HistorySearchName(inputText);
                boolean isExist= SearchNameManager.getInstance().updateAndSaveName(historySearchName);
                //这里应该展示接口返回的名称
                if (!isExist) {
                    initHistory();
                }else {
                    items.add(historySearchName);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        searchInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (TextUtils.isEmpty(text)) {
                    initHistory();
                }
            }
        });
    }

    private void initHistory() {
        historySearchList = new HistorySearchList();
        historySearchList.setList(SearchNameManager.getInstance().queryHistory());
        items.clear();
        items.add(historySearchList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getName(HistorySearchName name) {
        SearchNameManager.getInstance().updateAndSaveName(name);
        items.clear();
        adapter.notifyDataSetChanged();
        searchInputEt.setText(name.getName());
        searchInputEt.setSelection(searchInputEt.getText().toString().length());
        //---做搜索操作---
    }
}