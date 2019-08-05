package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.SearchHotBean;
import com.example.mydemo1.contract.SearchHotContract;
import com.example.mydemo1.model.SearchHotModel;

import java.util.List;

public class SearchHotPresenter<V extends SearchHotContract.SearchHotView> extends BasePresenter<V>
        implements SearchHotContract.SearchHotPresenter, SearchHotContract.SearchHotModel.CallBack {

    public SearchHotContract.SearchHotModel searchHotModel = new SearchHotModel();

    @Override
    public void onSearchHothttp() {
        if (mView != null) {
            searchHotModel.getSearchHotData(this);
        }
    }

    @Override
    public void onSearchHotSucces(List<SearchHotBean> searchHotBeans) {
        mView.onSearchHotSucces(searchHotBeans);
    }

    @Override
    public void onSearchHotFail(String msg) {
        mView.onSearchHotFail(msg);
    }
}
