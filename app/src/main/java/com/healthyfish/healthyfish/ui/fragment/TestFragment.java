package com.healthyfish.healthyfish.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.healthyfish.healthyfish.POJO.BeanItemNewsAbstract;
import com.healthyfish.healthyfish.POJO.BeanListReq;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.HomePageHealthInfoAdapter;
import com.healthyfish.healthyfish.ui.activity.HealthNews;
import com.healthyfish.healthyfish.utils.MyRecyclerViewOnItemListener;
import com.healthyfish.healthyfish.utils.MyToast;
import com.healthyfish.healthyfish.utils.OkHttpUtils;
import com.healthyfish.healthyfish.utils.RetrofitManagerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
    Unbinder unbinder;

    @BindView(R.id.ryv_more_health_news)
    RecyclerView ryvMoreHealthNews;

    private Context mContext;
    private HomePageHealthInfoAdapter healthInfoAdapter;
    final List<BeanItemNewsAbstract> newsList = new ArrayList<>();
    private TextView footTextView;
    private int to = 15;//起始加载的资讯条数
    private boolean isNotMore = false;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_healthy_top_line, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        //initRecyclerView();
        initListView();
        return view;
    }

//    private void initRecyclerView() {
//        List<Test> mList = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0 ; i <30 ;i++){
//            int type = random.nextInt(3)+1;
//            mList.add(new Test(type));
//        }
//        LinearLayoutManager lmg = new LinearLayoutManager(getActivity());
//        recyclerview.setLayoutManager(lmg);
//        TestFragmentAdapter adapter = new TestFragmentAdapter(mList,getActivity());
//        recyclerview.setAdapter(adapter);
//    }

    private void initListView() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        ryvMoreHealthNews.setLayoutManager(lm);
        final View footView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, ryvMoreHealthNews, false);
        footTextView = (TextView) footView.findViewById(R.id.tv_load_more);
        healthInfoAdapter = new HomePageHealthInfoAdapter(mContext, newsList);
        ryvMoreHealthNews.setAdapter(healthInfoAdapter);
        healthInfoAdapter.addFootView(footView);
        createRequest(0, to, false);

        //Item的点击监听
        ryvMoreHealthNews.addOnItemTouchListener(new MyRecyclerViewOnItemListener(mContext, ryvMoreHealthNews,
                new MyRecyclerViewOnItemListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position <= newsList.size() - 1) {
                            Intent intent = new Intent(mContext, HealthNews.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("HEALTH_NEWS_URL", newsList.get(position).getUrl());
                            bundle.putString("HEALTH_NEWS_TITLE", newsList.get(position).getTitle());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (position == newsList.size()) {
                            if (!footTextView.getText().toString().equals("到底啦！")) {
                                footTextView.setText("加载中...");
                                createRequest(to + 1, to + 10, true);
                                healthInfoAdapter.notifyDataSetChanged();
                                to = to + 10;
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));
    }

    private void createRequest(int from, int to, final boolean isLoad) {
        BeanListReq beanListReq = new BeanListReq();
        beanListReq.setPrefix("news_");
        beanListReq.setFrom(from);
        beanListReq.setTo(to);
        beanListReq.setNum(to - from + 1);
        RetrofitManagerUtils.getInstance(mContext, null)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanListReq),
                        new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                healthInfoAdapter.notifyDataSetChanged();//由于加载需要时间，故加载完成重新刷新适配器，防止FootView位置出错
                                if (isLoad && !isNotMore) {
                                    footTextView.setText("加载更多");
                                } else if (isNotMore) {
                                    footTextView.setText("到底啦！");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                MyToast.showToast(mContext,"加载出错");
                                footTextView.setText("加载更多");
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                if (responseBody != null) {
                                    String jsonNews = null;
                                    try {
                                        jsonNews = responseBody.string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    List<String> strJsonNewsList = JSONArray.parseObject(jsonNews, List.class);
                                    for (String strJsonNews : strJsonNewsList) {
                                        BeanItemNewsAbstract bean = JSON.parseObject(strJsonNews, BeanItemNewsAbstract.class);
                                        newsList.add(bean);
                                    }
                                    if (strJsonNewsList.size() <= 9) {
                                        isNotMore = true;
                                    }
                                }
                            }
                        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
