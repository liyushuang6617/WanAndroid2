package com.example.checkbox.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends SimpleActivity {

    public P myPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        if (myPresenter != null) {
            myPresenter.attach((V) this);
        }
    }

    public void showProgressBar() {

    }


    public void hideProgressBar() {

    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myPresenter != null) {
            myPresenter.detachView();
        }
    }
}
