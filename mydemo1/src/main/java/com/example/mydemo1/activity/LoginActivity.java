package com.example.mydemo1.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.base.BaseActivity;
import com.example.mydemo1.base.SimpleActivity;
import com.example.mydemo1.contract.LoginActivityContract;
import com.example.mydemo1.fragment.loginfragment.AccountFragment;
import com.example.mydemo1.fragment.loginfragment.LoginFragment;
import com.example.mydemo1.presenter.LoginActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

public class LoginActivity extends SimpleActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.login_fragment)
    FrameLayout loginFragment;

    @Override
    protected int createLayout() {
        return R.layout.activity_login;
    }


    @Override
    protected void initViewAndData() {
        toolbarTitles();
        replaceFragment();
    }

    private void toolbarTitles() {
        setSupportActionBar(activityToolbar);
        toolbarTitle.setText(R.string.login);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void replaceFragment() {
        LoginFragment loginFragment = new LoginFragment();
        AccountFragment accountFragment = new AccountFragment();
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


}
