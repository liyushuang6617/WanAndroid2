package com.example.mydemo1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.FlowLayout;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Main2Activity extends AppCompatActivity {

    private View mStatusBarView;
    private TextView mToolbarTitle;
    private Toolbar mActivityToolbar;
    private FlowLayout mFlowLayout;
    private TextView tv;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
    }

    private void initData() {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        HttpManager.getInstance().getApiService(MyService.class).getCommonData("friend/json")
                .compose(RxUtils.<BaseResponse<List<CommonDataBean>>>rxScheduleThread())
                .compose(RxUtils.<List<CommonDataBean>>changeResult())
                .subscribe(new Observer<List<CommonDataBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(final List<CommonDataBean> commonDataBeans) {
                        for (int i = 0; i < commonDataBeans.size(); i++) {
                            link = commonDataBeans.get(i).getLink();
                            final String name = commonDataBeans.get(i).getName();
                            tv = (TextView) View.inflate(Main2Activity.this, R.layout.flow_layout_tv, null);
                            tv.setText(name);
                            tv.setTextColor(HomePagerUtils.getRandomColor());
                            mFlowLayout.addView(tv);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Main2Activity.this, ArticleDetailActivity.class);
                                    intent.putExtra("link", link);
                                    intent.putExtra(Constants.ARTICLE_TITLE,name);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    private static final String TAG = "Main2Activity";

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
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);

        mToolbarTitle.setText("常用网站");

        setSupportActionBar(mActivityToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
