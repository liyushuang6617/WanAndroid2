package com.example.mydemo1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.base.SimpleActivity;
import com.example.mydemo1.constant.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends SimpleActivity {


    @BindView(R.id.article_web_view)
    WebView articleWebView;
    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.activity_toolbar)
    Toolbar activityToolbar;
    private String webUrl;
    private String title;
    private MenuItem mCollectItem;
    @Override
    protected void initViewAndData() {
        webUrl = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra(Constants.ARTICLE_TITLE);
        articleWebView.setWebViewClient(new WebViewClient());
        articleWebView.loadUrl(webUrl);
        onBackToolbar();
    }

    private void onBackToolbar() {
        setSupportActionBar(activityToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            toolbarTitle.setText(title);
        }

        activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int createLayout() {
        return R.layout.activity_article_detail;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_start, menu);
        mCollectItem = menu.findItem(R.id.article_collect);
        onPrepareOptionsMenu(menu);
        mCollectItem.setVisible(true);
        mCollectItem.setIcon(false ? R.drawable.ic_like_white : R.drawable.ic_like_not_white);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.article_share:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){}
                catch(Exception e){}
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
