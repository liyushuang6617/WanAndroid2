package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.AccountDataBean;
import com.example.mydemo1.contract.AccountContract;
import com.example.mydemo1.model.AccountModel;

public class AccountPresenter<V extends AccountContract.AccountView> extends BasePresenter<V> implements
        AccountContract.AccountPresenter, AccountContract.AccountModel.CallBack {

    public AccountContract.AccountModel accountModel = new AccountModel();

    @Override
    public void getAccounthttp(String username, String password, String repassword) {
        if (mView != null) {
            accountModel.getAccountData(this, username, password, repassword);
        }
    }

    @Override
    public void onAccountSuccess(AccountDataBean accountDataBean) {
        mView.onAccountSuccess(accountDataBean);
    }

    @Override
    public void onAccountFail(String msg) {
        mView.onAccountFail(msg);
    }
}
