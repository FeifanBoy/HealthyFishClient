package com.healthyfish.healthyfish;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.youzan.sdk.YouzanSDK;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：MyApplication初始化参数
 * 作者：Wayne on 2017/6/26 16:53
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class MyApplication extends Application{
    private static Context applicationContext;
    public static String uid = "";//暂时设置用
    //public static boolean isFirstAutoLogin = true;//用来标识是否是打开App登录后第一次进行自动登录
    public static boolean isFirstUpdateUsrPhy = true;//是否第一次更新用户体质
    public static boolean isIsFirstUpdatePersonalInfo = true;//是否第一次更新用户个人信息
    public static boolean isFirstUpdateMyConcern = true;//是否第一次更新用户关注列表
    public static boolean isFirstUpdateMyService = true;//是否第一次更新用户已购买服务列表
    public static boolean isFirstUpdateMyAppointment = true;//是否第一次更新用户挂号列表

    private List<Activity> oList;

    @Override
    public void onCreate() {
        super.onCreate();
        //自适应平屏幕
        AutoLayoutConifg.getInstance().useDeviceSize();
        applicationContext = getApplicationContext();
        LitePal.initialize(getApplicationContext());//初始化数据库

        YouzanSDK.init(this, "f734d9de4a7959d2941476408480462");// 初始化有赞

        oList = new ArrayList<Activity>();

    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }

    /**
     * 获取全局context方法
     *
     * @return contetxt
     */
    public static Context getContetxt() {
        return applicationContext;
    }

    public static Handler getApplicationHandler(){
        return applicationHandler;
    }


    /**
     * 服务上传图片成功或者失败Toast提醒用户
     */
    public static  Handler applicationHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x11:
                    Toast.makeText(getContetxt(),"图片上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0x12:
                    Toast.makeText(getContetxt(),"图片上传失败",Toast.LENGTH_SHORT).show();
                    break;
                case 0x13:
                    Toast.makeText(getContetxt(),"图片保存成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0x14:
                    Toast.makeText(getContetxt(),"图片上传中，请稍等...",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}
