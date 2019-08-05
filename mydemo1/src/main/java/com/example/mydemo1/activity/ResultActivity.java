package com.example.mydemo1.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvResultAdapter;
import com.example.mydemo1.adapter.RlvSearchAdapter;
import com.example.mydemo1.base.BaseActivity;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.contract.SearchContract;
import com.example.mydemo1.presenter.SearchPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ResultActivity extends BaseActivity<SearchContract.SearchView, SearchPresenter<SearchContract.SearchView>>
        implements SearchContract.SearchView {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

    @Override
    protected SearchPresenter<SearchContract.SearchView> createPresenter() {
        return null;
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected int createLayout() {
        return 0;
    }

    @Override
    public void onSearchSuccess(List<SearchDataBean> searchDataBeans) {

    }

    @Override
    public void onSearchFail(String msg) {

    }
}
