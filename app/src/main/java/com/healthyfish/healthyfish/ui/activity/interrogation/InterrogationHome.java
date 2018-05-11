package com.healthyfish.healthyfish.ui.activity.interrogation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfish.POJO.BeanHospDeptListRespItem;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.InterrogationRvAdapter;
import com.healthyfish.healthyfish.ui.activity.BaseActivity;
import com.healthyfish.healthyfish.ui.activity.SearchResult;
import com.healthyfish.healthyfish.ui.activity.appointment.ChooseHospital;
import com.healthyfish.healthyfish.ui.activity.appointment.SearchDoctor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * 作者：LYQ on 2017/7/31.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class InterrogationHome extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
//    @BindView(R.id.rv_choice_department)
//    RecyclerView rvChoiceDepartment;

    @BindView(R.id.choose_hospital)
    Button chooseHospital;
    @BindView(R.id.choose_department)
    Button chooseDepartment;

    private Context mContext;
    private InterrogationRvAdapter mRvAdapter;
    private List<BeanHospDeptListRespItem> DeptList = new ArrayList<>();

//    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interrogation_home);
        ButterKnife.bind(this);
        mContext = this;
        initToolBar(toolbar, toolbarTitle, "在线咨询");

//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("加载中...");
//            progressDialog.setCanceledOnTouchOutside(true);
//        }
//        progressDialog.show();

//        initRecycleView();
//        rvListener();
        searchListener();
    }

    /**
     * 搜索功能
     */
    private void searchListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(InterrogationHome.this, SearchResult.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("SEARCH_KEY", etSearch.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    /**
     * 按钮点击监听
     *
     * @param view
     */
    @OnClick({R.id.choose_hospital, R.id.choose_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_hospital://点击选择医院按钮
                Intent toChooseHospital = new Intent(this, ChooseHospital.class);
                toChooseHospital.putExtra("TYPE", 1);
                startActivity(toChooseHospital);
                break;
            case R.id.choose_department://点击选择科室按钮
                Intent test = new Intent(this, ChoiceDepartment.class);
                startActivity(test);
                break;
        }
    }

    /**
     * RecyclerView的监听
     */
//    private void rvListener() {
//        rvChoiceDepartment.addOnItemTouchListener(new MyRecyclerViewOnItemListener(mContext, rvChoiceDepartment, new MyRecyclerViewOnItemListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //跳转到该科室的医生列表，需要发送科室信息到后台获取科室医生列表信息，传入下一个页面
//                Intent intent = new Intent(mContext, ChoiceDoctor.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("DepartmentName", DeptList.get(position).getDEPT_NAME());
//                bundle.putString("DepartmentCode", DeptList.get(position).getDEPT_CODE());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                //MyToast.showToast(mContext,"长按"+String.valueOf(position));
//            }
//        }));
//
//
//    }

    /**
     * 初始化科室数据
     */
//    private void initRecycleView() {
//        final List<String> mDepartments = new ArrayList<>();
//        final List<Integer> mDepartmentIcons = new ArrayList<>();
//        final int[] icons = new int[]{R.mipmap.ic_chinese_medicine};
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
//        rvChoiceDepartment.setLayoutManager(gridLayoutManager);
//        mRvAdapter = new InterrogationRvAdapter(mContext, mDepartments, mDepartmentIcons);
//        rvChoiceDepartment.setAdapter(mRvAdapter);
//        rvChoiceDepartment.addItemDecoration(new DividerGridItemDecoration(mContext));
//
//        BeanHospDeptListReq beanHospDeptListReq = new BeanHospDeptListReq();
//        beanHospDeptListReq.setHosp("lzzyy");
//
//        RetrofitManagerUtils.getInstance(this, null)
//                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanHospDeptListReq), new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                        progressDialog.hide();
//                        mRvAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        progressDialog.hide();
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        String jsonStr = null;
//                        try {
//                            jsonStr = responseBody.string();
//                            Log.e("LYQ", "所有科室信息：" + jsonStr);
//                            List<JSONObject> beanHospDeptListResp = JSONArray.parseObject(jsonStr, List.class);
//                            for (JSONObject object : beanHospDeptListResp) {
//                                String jsonString = object.toJSONString();
//                                BeanHospDeptListRespItem beanHospDeptListRespItem = JSON.parseObject(jsonString, BeanHospDeptListRespItem.class);
//                                DeptList.add(beanHospDeptListRespItem);
//                                mDepartments.add(beanHospDeptListRespItem.getDEPT_NAME());
//                                mDepartmentIcons.add(icons[0]);
//                                //将科室信息保存到本地数据库
//                                BeanDepartmentInfo beanDepartmentInfo = new BeanDepartmentInfo();
//                                beanDepartmentInfo.setKey("lzzyy_" + beanHospDeptListRespItem.getDEPT_CODE());
//                                beanDepartmentInfo.setDepartmentName(beanHospDeptListRespItem.getDEPT_NAME());
//                                UpdateDepartmentInfoUtils.saveDepartmentInfo(beanDepartmentInfo);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//    }
}
