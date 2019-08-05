package com.example.mydemo1.fragment.mfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo1.R;
import com.example.mydemo1.activity.CollectionActivity;
import com.example.mydemo1.activity.ShouYeActivity;
import com.example.mydemo1.adapter.RlvArticleAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.BannerDataBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.HomePagerContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.presenter.HomePagerPresenter;
import com.example.mydemo1.utils.BannerGlideImageLoader;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePagerFragment extends BaseFragment<HomePagerContract.HomePagerView, HomePagerPresenter<HomePagerContract.HomePagerView>>
        implements HomePagerContract.HomePagerView {

    @BindView(R.id.home_pager_recycle_view)
    RecyclerView homePagerRecycleView;
    Unbinder unbinder;
    @BindView(R.id.home_smart_refresh)
    SmartRefreshLayout homeSmartRefresh;
    Unbinder unbinder1;
    private ArrayList<ArticleItemBean> articleListBeans;
    private RlvArticleAdapter adapter;
    private Banner mBanner;
    private ArrayList<BannerDataBean> listbanner;
    private int page = 0;

    public static HomePagerFragment newInstance() {
        return new HomePagerFragment();
    }

    @Override
    protected int createLayout() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected HomePagerPresenter<HomePagerContract.HomePagerView> createPresenter() {
        return new HomePagerPresenter<>();
    }

    @Override
    protected void initViewAndData() {

        homePagerRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleListBeans = new ArrayList<>();
        adapter = new RlvArticleAdapter(R.layout.item_artice_list, articleListBeans);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startArticleDetailPager(view, position);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickChildEvent(view, position);
            }
        });

        LinearLayout mHeadGroup = (LinearLayout) getLayoutInflater().inflate(R.layout.item_artice_banner, null);
        mBanner = mHeadGroup.findViewById(R.id.head_banner);
        mHeadGroup.removeView(mBanner);
        adapter.setHeaderView(mBanner);
        homePagerRecycleView.setHasFixedSize(true);

        homePagerRecycleView.setAdapter(adapter);

        listbanner = new ArrayList<>();
        mPresenter.HomePagerhttp(page);
        initSmartRefresh();
    }

    private void initSmartRefresh() {
        homeSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.HomePagerhttp(page);
            }
        });
        homeSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.HomePagerhttp(page);
            }
        });
    }

    //传值
    private void startArticleDetailPager(View view, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() < position) {
            return;
        }
        HomePagerUtils.startArticleDetailActivity(getActivity()
                , adapter.getData().get(position).getId()
                , adapter.getData().get(position).getTitle()
                , adapter.getData().get(position).getLink()
                , adapter.getData().get(position).isCollect()
                , true, position, "link");
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_article_chapterName:
                //todo chapter click
                break;
            case R.id.iv_article_like:
                collectClickEvent(position);
                break;
            case R.id.tv_article_tag:
                //todo tag click
                break;
            default:
                break;
        }
    }

    private void collectClickEvent(int position) {//8655
        int originId = articleListBeans.get(position).getOriginId();
        HttpManager.getInstance().getApiService(MyService.class).getCollectionItem("lg/collect/"+originId+""+"/json")
                .compose(RxUtils.<BaseResponse>rxScheduleThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getData() == null) {
                            Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSuccess(List<ArticleItemBean> articleListData) {
        articleListBeans.addAll(articleListData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBannerSuccess(List<BannerDataBean> bannerDataBeans) {
        listbanner.addAll(bannerDataBeans);
        showBanner(listbanner);
    }

    @Override
    public void onArtDataSuccess(ArticleListData datasBean) {
        if (adapter == null) {
            return;
        }
        if (false) {
            adapter.replaceData(datasBean.getDatas());
        } else {
            adapter.addData(datasBean.getDatas());
        }
    }

    private static final String TAG = "HomePagerFragment";

    @Override
    public void onFail(String msg) {
        Log.e(TAG, "onFail: " + msg);
    }


    private void showBanner(ArrayList<BannerDataBean> listbanner) {
        final ArrayList<String> bannerTitleList = new ArrayList<>();
        final ArrayList<String> bannerUrlList = new ArrayList<>();
        ArrayList<String> bannerImageList = new ArrayList<>();
        final ArrayList<Integer> bannerPositonList = new ArrayList<>();
        for (BannerDataBean bannerDataBean : listbanner) {
            bannerTitleList.add(bannerDataBean.getTitle());
            bannerUrlList.add(bannerDataBean.getUrl());
            bannerImageList.add(bannerDataBean.getImagePath());
            bannerPositonList.add(bannerDataBean.getId());
        }

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerGlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(bannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                HomePagerUtils.startArticleDetailActivity(getActivity(), bannerPositonList.get(i)
                        , bannerTitleList.get(i), bannerUrlList.get(i), false, false
                        , -1, Constants.TAG_DEFAULT);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {

    }

    public void jumpToTheTop() {
        if (homePagerRecycleView != null) {
            homePagerRecycleView.smoothScrollToPosition(0);
        }
    }
}

