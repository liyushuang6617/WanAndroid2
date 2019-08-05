package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.CollectionDataBean;
import com.example.mydemo1.contract.CollectionContract;
import com.example.mydemo1.model.CollectionModel;

public class CollectionPresenter<V extends CollectionContract.CollectionView> extends BasePresenter<V>
        implements CollectionContract.CollectionPresenter, CollectionContract.CollectionModel.CallBack {


    public CollectionContract.CollectionModel collectionModel = new CollectionModel();


    @Override
    public void onCollectionhttp() {
        if (mView != null) {
            collectionModel.getCollectionData(this);
        }
    }

    @Override
    public void onCollectionSuccess(CollectionDataBean collectionDataBean) {
        mView.onCollectionSuccess(collectionDataBean);
    }

    @Override
    public void onCollectionFail(String msg) {
        mView.onCollectionFail(msg);
    }

}
