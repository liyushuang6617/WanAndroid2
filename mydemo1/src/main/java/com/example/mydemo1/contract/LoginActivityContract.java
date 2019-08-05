package com.example.mydemo1.contract;

import com.example.mydemo1.bean.LoginDataBean;

import okhttp3.ResponseBody;

public interface LoginActivityContract {

    interface LoginActivityView {
        void onLoginActivitySuccess(LoginDataBean responseBody);

        void onLoginActivityFail(String msg);
    }

    interface LoginActivityPresenter {
        void onLoginActivityhttp(String username, String password);
    }

    interface LoginActivityModel {
        interface CallBack {
            void onLoginActivitySuccess(LoginDataBean responseBody);

            void onLoginActivityFail(String msg);
        }

        void onLoginActivitygetData(CallBack callBack, String username, String password);
    }
}
