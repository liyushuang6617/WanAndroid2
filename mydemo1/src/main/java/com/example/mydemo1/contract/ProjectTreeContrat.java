package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ProjectTreeDataBean;

import java.util.List;

public interface ProjectTreeContrat {

    interface ProjectTreeView {
        void onProjectSuccess(List<ProjectTreeDataBean> projectDataBean);

        void onProjectFail(String msg);
    }

    interface ProjectTreePresenter {
        void ProjectTreehttp();
    }

    interface ProjectTreeModel {
        interface CallBack {
            void onProjectSuccess(List<ProjectTreeDataBean> projectDataBean);

            void onProjectFail(String msg);
        }

        void getProjectTreeData(CallBack callBack);
    }
}
