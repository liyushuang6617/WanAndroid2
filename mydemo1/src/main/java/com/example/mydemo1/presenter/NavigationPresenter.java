package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.NavigtionDataBean;
import com.example.mydemo1.contract.NavigationContract;
import com.example.mydemo1.model.NavigationModel;

import java.util.List;

public class NavigationPresenter<V extends NavigationContract.NavigationView> extends BasePresenter<V>
        implements NavigationContract.NavigationPresenter, NavigationContract.NavigationModel.CallBack {

    public NavigationContract.NavigationModel navigationModel = new NavigationModel();

    @Override
    public void Navigationhttp() {
        if (mView != null) {
            navigationModel.getNavigationData(this);
        }
    }

    @Override
    public void onNavigationSuccess(List<NavigtionDataBean> navigtionDataBean) {
        mView.onNavigationSuccess(navigtionDataBean);
    }

    @Override
    public void onNavigationFail(String msg) {
        mView.onNavigationFail(msg);
    }
}
