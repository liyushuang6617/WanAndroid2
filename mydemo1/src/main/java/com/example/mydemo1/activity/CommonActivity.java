package com.example.mydemo1.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvCommonAdapter;
import com.example.mydemo1.base.BaseActivity;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.contract.CommonContract;
import com.example.mydemo1.presenter.CommonPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonActivity extends BaseActivity<CommonContract.CommonView, CommonPresenter<CommonContract.CommonView>>
        implements CommonContract.CommonView {

    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.common_recycle_view)
    RecyclerView commonRecycleView;
    private ArrayList<CommonDataBean> list;
    private RlvCommonAdapter adapter;

    @Override
    protected void initViewAndData() {
        list = new ArrayList<>();
        commonRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RlvCommonAdapter(this ,list);
        commonRecycleView.setAdapter(adapter);
        mPresenter.data();
    }

    @Override
    protected int createLayout() {
        return R.layout.activity_common;
    }

    @Override
    protected CommonPresenter<CommonContract.CommonView> createPresenter() {
        return new CommonPresenter<>();
    }

    @Override
    public void onCommonSuccess(List<CommonDataBean> commonDataBean) {
        list.addAll(commonDataBean);
        adapter.notifyDataSetChanged();
    }

    private static final String TAG = "CommonActivity";
    @Override
    public void onCommonFail(String msg) {
        Log.e(TAG, "onCommonFail: " +msg );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String msg) {
    }
}
