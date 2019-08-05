package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.LoginDataBean;
import com.example.mydemo1.contract.LoginActivityContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import okhttp3.ResponseBody;

public class LoginActivityModel implements LoginActivityContract.LoginActivityModel {
    @Override
    public void onLoginActivitygetData(final CallBack callBack, String username, String password) {
        HttpManager.getInstance().getApiService(MyService.class).getLoginData("user/login", username, password)
                .compose(RxUtils.<BaseResponse<LoginDataBean>>rxScheduleThread())
                .compose(RxUtils.<LoginDataBean>changeResult())
                .subscribe(new BaseObsever<LoginDataBean>() {
                    @Override
                    public void onSuccess(LoginDataBean data) {
                        callBack.onLoginActivitySuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onLoginActivityFail(error);
                    }
                });
    }
}
