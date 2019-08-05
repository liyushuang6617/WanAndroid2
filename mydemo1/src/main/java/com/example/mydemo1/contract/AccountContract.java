package com.example.mydemo1.contract;

import com.example.mydemo1.bean.AccountDataBean;

public interface AccountContract {
    interface AccountView {
        void onAccountSuccess(AccountDataBean accountDataBean);

        void onAccountFail(String msg);
    }

    interface AccountPresenter {
        void getAccounthttp(String username,String password,String repassword);
    }

    interface AccountModel {
        interface CallBack {
            void onAccountSuccess(AccountDataBean accountDataBean);

            void onAccountFail(String msg);
        }

        void getAccountData(CallBack callBack,String username,String password,String repassword);
    }
}
