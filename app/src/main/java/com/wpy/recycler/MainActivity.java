package com.wpy.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wpy.recycler.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *   给RecyclerView 添加上拉加载的视图
 */
public class MainActivity extends AppCompatActivity implements MyAdapter.OnScrollReachedBottomListener {
    private List<String> listStr = new ArrayList<>();
    private RecyclerView mRecycler;
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private RecyclerView.LayoutManager mLayoutmanager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
    }

    private void intiView() {
        mRecycler = (RecyclerView) findViewById(R.id.mRecycler);
        for (int i = 0; i < 15; i++) {
            listStr.add("数据" + i);
        }
        setAdapter();
        scrollListener();
    }

    private void setAdapter() {
        myAdapter = new MyAdapter(this, listStr);
        mLayoutmanager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutmanager);
        mRecycler.setAdapter(myAdapter);
    }

    private void scrollListener() {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = mLayoutmanager.getChildCount();
                    totalItemCount = mLayoutmanager.getItemCount();
                    pastVisibleItems = (((LinearLayoutManager) mLayoutmanager).findFirstVisibleItemPosition());
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            // 滚动到列表底部
                            loading = false;
                            // 产生回调
                            onScrollReachedBottom();
                        }
                    }
                }
            }
        });
    }

    // RecyclerView滚动到最后一个元素
    @Override
    public void onScrollReachedBottom() {
        // 模拟请求延迟
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 产生mock数据
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    list.add("新数据"+i);
                }
                // 注意这个 -1，不要将数据插入到“正在载入”提示View之后去了
                int insertPosition = myAdapter.getItemCount() - 1;
                myAdapter.addDatas(list);
                // 使用该方法更新数据，可以保持当前用户的滚动位置
                myAdapter.notifyItemInserted(insertPosition);

                loading = true;
            }
        },300);
    }
}
