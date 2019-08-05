package com.example.mydemo1.contract;

import com.example.mydemo1.bean.NavigtionDataBean;

import java.util.List;

public interface NavigationContract {

    interface NavigationView {
        void onNavigationSuccess(List<NavigtionDataBean> navigtionDataBean);

        void onNavigationFail(String msg);
    }

    interface NavigationPresenter {
        void Navigationhttp();
    }

    interface NavigationModel {
        interface CallBack {
            void onNavigationSuccess(List<NavigtionDataBean> navigtionDataBean);

            void onNavigationFail(String msg);
        }

        void getNavigationData(CallBack callBack);
    }
}
