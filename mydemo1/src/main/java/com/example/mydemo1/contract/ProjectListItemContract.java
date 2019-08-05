package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ProjectListDataBean;

public interface ProjectListItemContract {

    interface ProjectListItemView {
        void onProjectListItemSuccess(ProjectListDataBean projectListItemBean);

        void onProjectListItemFail(String msg);
    }

    interface ProjectListItemPresenter {
        void onProjectListItemhttp(int page,int cid);
    }

    interface ProjectListItemModel {
        interface CallBack {
            void onProjectListItemSuccess(ProjectListDataBean projectListItemBean);

            void onProjectListItemFail(String msg);
        }

        void getProjectListItemData(CallBack callBack,int page,int cid);
    }
}
