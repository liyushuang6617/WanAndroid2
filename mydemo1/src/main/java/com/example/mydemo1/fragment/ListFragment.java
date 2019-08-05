package com.example.mydemo1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydemo1.R;
import com.example.mydemo1.bean.WxArticleListBean;
import com.example.mydemo1.contract.WxArticleListContract;
import com.example.mydemo1.presenter.WxArticleListPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment<WxArticleListContract.WxArticleListView, WxArticleListPresenter<WxArticleListContract.WxArticleListView>>
        implements WxArticleListContract.WxArticleListView{


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected int createLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected WxArticleListPresenter<WxArticleListContract.WxArticleListView> createPresenter() {
        return null;
    }

    @Override
    public void onWxArticleListSuccess(WxArticleListBean wxArticleListBean) {

    }

    @Override
    public void onWxArticleListFail(String msg) {

    }
}
