package com.example.mydemo1.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvCollectionAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.CollectionDataBean;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CollectionActivity extends AppCompatActivity {

//    @BindView(R.id.collection_recycle_view)
//    RecyclerView collectionRecycleView;

    private int originId;
    private View mStatusBarView;
    private TextView mToolbarTitle;
    private Toolbar mActivityToolbar;

    private static final String TAG = "CollectionActivity";
    private ArrayList<CollectionDataBean.DatasBean> list;
    private RlvCollectionAdapter adapter;
    private RecyclerView mCollectionRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        toolbarSetting();
        initData();
    }

    public void initData() {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        HttpManager.getInstance().getApiService(MyService.class).getCollectionData("lg/collect/list/0/json")
                .compose(RxUtils.<BaseResponse<CollectionDataBean>>rxScheduleThread())
                .compose(RxUtils.<CollectionDataBean>changeResult())
                .subscribe(new Observer<CollectionDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CollectionDataBean collectionDataBean) {
                        List<CollectionDataBean.DatasBean> datas = collectionDataBean.getDatas();
                        list.addAll(collectionDataBean.getDatas());
                        adapter.notifyDataSetChanged();
//                        originId = datas.get(0).getOriginId();
//                        collectionData();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void toolbarSetting() {
        setSupportActionBar(mActivityToolbar);
        mToolbarTitle.setText("收藏");
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

    private void initView() {
        mStatusBarView = (View) findViewById(R.id.status_bar_view);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mActivityToolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        mCollectionRecycleView = (RecyclerView) findViewById(R.id.collection_recycle_view);

        mCollectionRecycleView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new RlvCollectionAdapter(this, list);
        mCollectionRecycleView.setHasFixedSize(true);
        mCollectionRecycleView.setAdapter(adapter);
    }

//    private void collectionData() {
//        HttpManager.getInstance().getApiService(MyService.class).getCollectionItem(originId)
//                .compose(RxUtils.<BaseResponse>rxScheduleThread())
//                .subscribe(new Observer<BaseResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse baseResponse) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
