package com.example.mydemo1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvLishiAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ResuleSearchBean;
import com.example.mydemo1.bean.SearchHotBean;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.FlowLayout;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<SearchHotBean> list;
    private Toolbar mSearchToolbar;
    private ImageButton mSearchIvBtn;
    private FlowLayout mSearchFlowLayout;
    /**
     * 全部清除
     */
    private TextView mSearchNullTint;
    /**
     * 快去搜索更多历史吧
     */
    private TextView mGoSearch;
    /**
     * 发现更多精彩内容
     */
    private EditText mEtartname;
    private TextView textView;

    private String art;
    private String text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        toolbarSetting();
        initData();
    }

    private void initView() {
        mSearchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        mSearchIvBtn = (ImageButton) findViewById(R.id.search_iv_btn);
        mSearchFlowLayout = (FlowLayout) findViewById(R.id.search_flow_layout);
        mSearchNullTint = (TextView) findViewById(R.id.search_null_tint);
        mEtartname = (EditText) findViewById(R.id.etartname);
        mSearchIvBtn.setOnClickListener(this);
    }

    private void initData() {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        HttpManager.getInstance().getApiService(MyService.class).getSearchHotBean("hotkey/json")
                .compose(RxUtils.<BaseResponse<List<SearchHotBean>>>rxScheduleThread())
                .compose(RxUtils.<List<SearchHotBean>>changeResult())
                .subscribe(new Observer<List<SearchHotBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<SearchHotBean> searchHotBeans) {
                        for (int i = 0; i < searchHotBeans.size(); i++) {
                            textView = (TextView) View.inflate(SearchActivity.this, R.layout.flow_layout_tv, null);
                            textView.setText(searchHotBeans.get(i).getName());
                            textView.setTextColor(HomePagerUtils.getRandomColor());
                            text = textView.getText().toString();
                            TextView viewById = textView.findViewById(R.id.common_title_tv);
                            viewById.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(SearchActivity.this, ResultSearchActivity.class);
                                    intent1.putExtra("key", text);
                                    startActivity(intent1);
                                }
                            });
                            mSearchFlowLayout.addView(textView);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void toolbarSetting() {
        setSupportActionBar(mSearchToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_iv_btn:
                art = mEtartname.getText().toString();
                Intent intent = new Intent(SearchActivity.this, ResultSearchActivity.class);
                intent.putExtra("key", art);
                startActivity(intent);
                break;
        }
    }
}
