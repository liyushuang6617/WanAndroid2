package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.AccountDataBean;
import com.example.mydemo1.contract.AccountContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

public class AccountModel implements AccountContract.AccountModel {
    @Override
    public void getAccountData(final CallBack callBack, String username, String password, String repassword) {
        HttpManager.getInstance().getApiService(MyService.class).getAccOunt("user/register", username, password, repassword)
                .compose(RxUtils.<BaseResponse<AccountDataBean>>rxScheduleThread())
                .compose(RxUtils.<AccountDataBean>changeResult())
                .subscribe(new BaseObsever<AccountDataBean>() {
                    @Override
                    public void onSuccess(AccountDataBean data) {
                        callBack.onAccountSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onAccountFail(error);
                    }
                });
    }
}
