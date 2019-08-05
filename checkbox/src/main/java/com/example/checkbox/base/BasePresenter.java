package com.example.checkbox.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V> {

    private WeakReference<V> weakReference;

    public V myView;

    public void attach(V v) {
        weakReference = new WeakReference<>(v);
        v = weakReference.get();
    }

    public void detachView() {
        if (weakReference != null) {
            weakReference.clear();
        }
    }


}
