package com.example.wanandroid2.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.wanandroid2.R;
import com.example.wanandroid2.base.BaseActivtity;
import com.example.wanandroid2.fragment.KapFragment;
import com.example.wanandroid2.fragment.NavigFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class LoginActivity extends BaseActivtity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.login_fragment)
    FrameLayout loginFragment;

    private void toolbarTitles() {
        setSupportActionBar(activityToolbar);
        toolbarTitle.setText(R.string.login);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void replaceFragment() {
        KapFragment loginFragment = new KapFragment();
        NavigFragment accountFragment = new NavigFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(R.id.login_fragment, loginFragment);
        transaction.add(R.id.login_fragment, accountFragment);
        transaction.show(loginFragment);
        transaction.hide(accountFragment);
        transaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String title) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
