package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.SearchDataBean;

import java.util.List;

public interface SearchContract {

    interface SearchView {
        void onSearchSuccess(List<SearchDataBean> searchDataBeans);

        void onSearchFail(String msg);
    }

    interface SearchPresenter {
        void onSearchhttp(String key);
    }

    interface SearchModel {
        interface CallBack {
            void onSearchSuccess(List<SearchDataBean> searchDataBeans);


            void onSearchFail(String msg);
        }

        void getSearchData(CallBack callBack, String key);
    }
}
