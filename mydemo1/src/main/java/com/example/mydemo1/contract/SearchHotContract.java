package com.example.mydemo1.contract;

import com.example.mydemo1.bean.SearchHotBean;

import java.util.List;

public interface SearchHotContract {

    interface SearchHotView {
        void onSearchHotSucces(List<SearchHotBean> searchHotBeans);

        void onSearchHotFail(String msg);
    }

    interface SearchHotPresenter {
        void onSearchHothttp();
    }

    interface SearchHotModel {
        interface CallBack {
            void onSearchHotSucces(List<SearchHotBean> searchHotBeans);

            void onSearchHotFail(String msg);
        }

        void getSearchHotData(CallBack callBack);
    }
}
