package com.example.mydemo1.contract;

import android.view.View;

public interface ShouYeContract {

    interface ShouYePresenter {
        void setNightMode(boolean isNightMode);
        boolean isNightMode();
    }

}
