package com.example.mydemo1.contract;

import com.example.mydemo1.bean.CommonDataBean;

import java.util.List;

public interface CommonContract {
    interface CommonView {
        void onCommonSuccess(List<CommonDataBean> commonDataBean);

        void onCommonFail(String msg);
    }

    interface CommonPresenter {
        void data();
    }

    interface CommonModel {
        interface CallBack {
            void onCommonSuccess(List<CommonDataBean> commonDataBean);

            void onCommonFail(String msg);
        }

        void getData(CallBack callBack);
    }
}
