package com.example.wanandroid2.utils;

import android.content.Context;
import android.content.Intent;

import com.example.wanandroid2.activity.ArticleDetailActivity;

public class MainUtils {
    public static void startArticleDetailActivity(Context context, int articleId, String articleTitle,
                                                  String articleLink, boolean isCollected,
                                                  boolean isShowCollectIcon, int articleItemPosition,
                                                  String eventBusTag) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, articleId);
        intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
        intent.putExtra(Constants.ARTICLE_LINK, articleLink);
        intent.putExtra(Constants.IS_COLLECTED, isCollected);
        intent.putExtra(Constants.IS_SHOW_COLLECT_ICON, isShowCollectIcon);
        intent.putExtra(Constants.ARTICLE_ITEM_POSITION, articleItemPosition);
        intent.putExtra(Constants.EVENT_BUS_TAG, eventBusTag);
        context.startActivity(intent);
    }
}
