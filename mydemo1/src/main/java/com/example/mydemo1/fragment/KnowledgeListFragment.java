package com.example.mydemo1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo1.R;
import com.example.mydemo1.activity.LoginActivity;
import com.example.mydemo1.adapter.RlvArticleAdapter;
import com.example.mydemo1.adapter.RlvKnowledgeListAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.KnowledgeActivityContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.presenter.KnowledgeActivityPresenter;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;

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
public class KnowledgeListFragment extends BaseFragment<KnowledgeActivityContract.KnowledgeActivityView, KnowledgeActivityPresenter<KnowledgeActivityContract.KnowledgeActivityView>>
        implements KnowledgeActivityContract.KnowledgeActivityView {

    private static int cid;
    @BindView(R.id.knowledge_list_recycle_view)
    RecyclerView knowledgeListRecycleView;
    Unbinder unbinder;
    private RlvArticleAdapter adapter;
    private ArrayList<ArticleItemBean> articleItemBeans;

    public static KnowledgeListFragment newInstance(Bundle bundle) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViewAndData() {
        assert getArguments() != null;
        cid = getArguments().getInt(Constants.KNOWLEDGE_CID);
        knowledgeListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleItemBeans = new ArrayList<>();
        adapter = new RlvArticleAdapter(R.layout.item_artice_list, articleItemBeans);
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
        knowledgeListRecycleView.setHasFixedSize(true);
        knowledgeListRecycleView.setAdapter(adapter);

        mPresenter.getKnowledgeActivityhttp(cid);
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

    private void collectClickEvent(final int position) {
        int id = articleItemBeans.get(position).getId();
        if (articleItemBeans.get(position).isCollect()) {
            HttpManager.getInstance().getApiService(MyService.class).getNoCollection("lg/uncollect_originId/" + id + "" + "/json")
                    .compose(RxUtils.<BaseResponse>rxScheduleThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse.getErrorMsg() == "") {
                                articleItemBeans.get(position).setCollect(false);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "已取消收藏", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            HttpManager.getInstance().getApiService(MyService.class).getCollectionItem("lg/collect/" + id + "" + "/json")
                    .compose(RxUtils.<BaseResponse>rxScheduleThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse.getErrorMsg() == "") {
                                articleItemBeans.get(position).setCollect(true);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(),LoginActivity.class));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }

    private void startArticleDetailPager(View view, int position) {
        if (adapter.getData().size() <= 0 || adapter.getData().size() < position) {
            return;
        }

        HomePagerUtils.startArticleDetailActivity(getActivity(),
                adapter.getData().get(position).getId(),
                adapter.getData().get(position).getTitle(),
                adapter.getData().get(position).getLink(),
                adapter.getData().get(position).isCollect(),
                true, position, "link");
    }

    @Override
    protected int createLayout() {
        return R.layout.fragment_knowledge_list;
    }

    @Override
    protected KnowledgeActivityPresenter<KnowledgeActivityContract.KnowledgeActivityView> createPresenter() {
        return new KnowledgeActivityPresenter<>();
    }

    @Override
    public void onKnowledgeItemSuccess(ArticleListData knowledgeItemBean1) {
        if (adapter == null) {
            return;
        }
        if (false) {
            adapter.replaceData(knowledgeItemBean1.getDatas());
        } else {
            adapter.addData(knowledgeItemBean1.getDatas());
        }

    }

    private static final String TAG = "KnowledgeListFragment";

    @Override
    public void onFail(String msg) {
        Log.e(TAG, "onFail: " + msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {

    }

    public void jumpToTheTop() {
        if (knowledgeListRecycleView != null) {
            knowledgeListRecycleView.smoothScrollToPosition(0);
        }
    }
}
