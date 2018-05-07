package com.healthyfish.healthyfish.ui.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.healthyfish.healthyfish.MyApplication;
import com.healthyfish.healthyfish.R;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.http.engine.OnRegister;
import com.youzan.sdk.http.engine.QueryError;
import com.youzan.sdk.web.plugin.YouzanBrowser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthWorkshopFragment extends Fragment {

    Unbinder unbinder;
    private Context mContext;
    private View rootView;

    YouzanBrowser mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_health_workshop, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mWebView = (YouzanBrowser) rootView.findViewById(R.id.health_worker_webview);

        YouzanUser user = new YouzanUser();// 静默注册虚拟的有赞用户，必须要有一个ID
        if (!TextUtils.isEmpty(MyApplication.uid)) {
            user.setUserId(MyApplication.uid);// 唯一的用来标识用户在有赞中的数据，建议使用用户手机号，
        }

        YouzanSDK.asyncRegisterUser(user, new OnRegister() {
            @Override
            public void onFailed(QueryError queryError) {
                Toast.makeText(getActivity(), queryError.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                //打开店铺链接
                mWebView.loadUrl("https://h5.koudaitong.com/v2/showcase/homepage?alias=xgba5h9g");//健鱼主页
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
