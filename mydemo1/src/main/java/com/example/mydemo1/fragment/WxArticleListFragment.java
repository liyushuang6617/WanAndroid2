package com.example.mydemo1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.mydemo1.adapter.RlvWxArticleItemAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.WxArticleListBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WxArticleListFragment extends Fragment {

    private View view;
    private RecyclerView mWxArticleListRecycleView;
    private String cid;
    private ArrayList<ArticleItemBean> wxArticleListBeans;
    private RlvWxArticleItemAdapter rlvWxArticleItemAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_wx_article_list, null);

        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        HttpManager.getInstance().getApiService(MyService.class).getWxArticleList(Integer.parseInt(cid))
                .compose(RxUtils.<BaseResponse<WxArticleListBean>>rxScheduleThread())
                .compose(RxUtils.<WxArticleListBean>changeResult())
                .subscribe(new Observer<WxArticleListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WxArticleListBean wxArticleListBean) {
                        wxArticleListBeans.addAll(wxArticleListBean.getDatas());
                        rlvWxArticleItemAdapter.notifyDataSetChanged();
                    }

                    private static final String TAG = "WxArticleListFragment";

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View inflate) {
        cid = getArguments().getString(Constants.ARTICLE_ID);
        mWxArticleListRecycleView = (RecyclerView) inflate.findViewById(R.id.wx_article_list_recycle_view);
        mWxArticleListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wxArticleListBeans = new ArrayList<>();
        rlvWxArticleItemAdapter = new RlvWxArticleItemAdapter(R.layout.item_artice_list, wxArticleListBeans);
        rlvWxArticleItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startArticleActivity(view, position);
            }
        });

        rlvWxArticleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                collectClickEvent(position);
            }
        });
        mWxArticleListRecycleView.setHasFixedSize(true);
        mWxArticleListRecycleView.setAdapter(rlvWxArticleItemAdapter);
    }

    private void startArticleActivity(View view, int position) {
        if (rlvWxArticleItemAdapter.getData().size() <= 0 || rlvWxArticleItemAdapter.getData().size() < position) {
            return;
        }
        HomePagerUtils.startArticleDetailActivity(getActivity()
                , rlvWxArticleItemAdapter.getData().get(position).getId()
                , rlvWxArticleItemAdapter.getData().get(position).getTitle()
                , rlvWxArticleItemAdapter.getData().get(position).getLink()
                , rlvWxArticleItemAdapter.getData().get(position).isCollect()
                , true, position, "link");
    }

    public void jumpToTheTop() {
        if (mWxArticleListRecycleView != null) {
            mWxArticleListRecycleView.smoothScrollToPosition(0);
        }
    }

    private static final String TAG = "WxArticleListFragment";

    private void collectClickEvent(final int position) {//8655
        int id = wxArticleListBeans.get(position).getId();
        if (wxArticleListBeans.get(position).isCollect()) {
            HttpManager.getInstance().getApiService(MyService.class).getNoCollection("lg/uncollect_originId/" + id + "" + "/json")
                    .compose(RxUtils.<BaseResponse>rxScheduleThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse.getErrorMsg() == "") {
                                wxArticleListBeans.get(position).setCollect(false);
                                rlvWxArticleItemAdapter.notifyDataSetChanged();
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
                                wxArticleListBeans.get(position).setCollect(true);
                                rlvWxArticleItemAdapter.notifyDataSetChanged();
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
}
