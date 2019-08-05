package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.contract.CommonContract;
import com.example.mydemo1.model.CommonModel;

import java.util.List;

public class CommonPresenter<V extends CommonContract.CommonView> extends BasePresenter<V>
        implements CommonContract.CommonPresenter, CommonContract.CommonModel.CallBack {

    public CommonContract.CommonModel commonModel = new CommonModel();

    @Override
    public void data() {
        if (mView != null) {
            commonModel.getData(this);
        }
    }

    @Override
    public void onCommonSuccess(List<CommonDataBean> commonDataBean) {
        mView.onCommonSuccess(commonDataBean);
    }

    @Override
    public void onCommonFail(String msg) {
        mView.onCommonFail(msg);
    }
}
