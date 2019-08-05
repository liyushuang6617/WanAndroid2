package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ListData;
import com.example.mydemo1.contract.MainContract;
import com.example.mydemo1.model.IMainModel;

import java.util.List;

public class IMainPresenter<V extends MainContract.MainView>
        extends BasePresenter<V> implements MainContract.MainPresenter, MainContract.MainModel.CallBack {

    private MainContract.MainModel mainModel = new IMainModel();

    @Override
    public void http() {
        if (mView != null) {
            mainModel.getData(this);
        }
    }

    @Override
    public void showSuccess(List<ListData> listData) {
        mView.showSuccess(listData);
    }

    @Override
    public void showFail(String msg) {
        mView.showFail(msg);
    }
}
