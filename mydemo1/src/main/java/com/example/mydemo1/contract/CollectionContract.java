package com.example.mydemo1.contract;

import com.example.mydemo1.bean.CollectionDataBean;

public interface CollectionContract {

    interface CollectionView {
        void onCollectionSuccess(CollectionDataBean collectionDataBean);

        void onCollectionFail(String msg);
    }

    interface CollectionPresenter {
        void onCollectionhttp();
    }

    interface CollectionModel {
        interface CallBack {
            void onCollectionSuccess(CollectionDataBean collectionDataBean);

            void onCollectionFail(String msg);
        }

        void getCollectionData(CallBack callBack);
    }
}
