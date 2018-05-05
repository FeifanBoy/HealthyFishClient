package com.healthyfish.healthyfish.ui.activity.personal_center;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：个人中心意见反馈页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class Feedback extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.bt_commit)
    Button btCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"意见反馈");
    }

    @OnClick(R.id.bt_commit)
    public void onViewClicked() {
        if (!etFeedback.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();

            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            finish();
        } else {
            Toast.makeText(this, "请填写您的意见", Toast.LENGTH_SHORT).show();
        }
    }
}
