package com.example.mydemo1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvSearchAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.ResuleSearchBean;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ResultSearchActivity extends AppCompatActivity implements RlvSearchAdapter.A {

    private View mStatusBarView;
    private TextView mToolbarTitle;
    private Toolbar mActivityToolbar;
    private RecyclerView mResuleRecycleView;
    private String key;
    private ArrayList<ArticleItemBean> list;
    private RlvSearchAdapter adapter;
    private ArrayList<ResuleSearchBean> searchBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        initView();
        initData();
    }

    private void initData() {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        HttpManager.getInstance().getApiService(MyService.class).getSearchData("article/query/0/json", key)
                .compose(RxUtils.<BaseResponse<ArticleListData>>rxScheduleThread())
                .compose(RxUtils.<ArticleListData>changeResult())
                .subscribe(new Observer<ArticleListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ArticleListData searchDataBeans) {
                        list.addAll(searchDataBeans.getDatas());
                        adapter.notifyDataSetChanged();
                    }

                    private static final String TAG = "ResultSearchActivity";

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mStatusBarView = (View) findViewById(R.id.status_bar_view);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mActivityToolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        mResuleRecycleView = (RecyclerView) findViewById(R.id.resule_recycle_view);

        key = getIntent().getStringExtra("key");
        mResuleRecycleView.setLayoutManager(new LinearLayoutManager(this));

        toolbatSetting();
        list = new ArrayList<>();
        adapter = new RlvSearchAdapter(this, list);
        mResuleRecycleView.setHasFixedSize(true);
        mResuleRecycleView.setAdapter(adapter);

        adapter.setA(this);
    }


    private void toolbatSetting() {
        setSupportActionBar(mActivityToolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            mToolbarTitle.setText(key);
        }

        mActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemClick(int pos, ArticleItemBean bean) {
        Intent intent = new Intent(ResultSearchActivity.this, ArticleDetailActivity.class);
        intent.putExtra("link", bean.getLink());
        intent.putExtra(Constants.ARTICLE_TITLE, bean.getTitle());
        startActivity(intent);
    }
}
