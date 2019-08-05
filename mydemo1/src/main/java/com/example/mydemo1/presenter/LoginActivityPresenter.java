package com.example.mydemo1.presenter;

import com.example.mydemo1.activity.LoginActivity;
import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.LoginDataBean;
import com.example.mydemo1.contract.LoginActivityContract;
import com.example.mydemo1.model.LoginActivityModel;

import okhttp3.ResponseBody;

public class LoginActivityPresenter<V extends LoginActivityContract.LoginActivityView> extends BasePresenter<V>
        implements LoginActivityContract.LoginActivityPresenter, LoginActivityContract.LoginActivityModel.CallBack {

    public LoginActivityContract.LoginActivityModel loginActivityModel = new LoginActivityModel();

    @Override
    public void onLoginActivityhttp(String username, String password) {
        if (mView != null) {
            loginActivityModel.onLoginActivitygetData(this, username, password);
        }
    }

    @Override
    public void onLoginActivitySuccess(LoginDataBean responseBody) {
        mView.onLoginActivitySuccess(responseBody);
    }

    @Override
    public void onLoginActivityFail(String msg) {
        mView.onLoginActivityFail(msg);
    }
}
