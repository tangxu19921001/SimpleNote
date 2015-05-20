package com.notedemo.activity;


import com.notedemo.config.AppConfig;
import com.notedemo.model.BaseAppData;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;


public class FirstGroupTab extends ActivityGroup {
        
        /**
         * 一个静态的ActivityGroup变量，用于管理本Group中的Activity
         */
        public static ActivityGroup group;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                // TODO Auto-generated method stub
                super.onCreate(savedInstanceState);
                
                group = this;
                Log.i("GroupTab","onCreate()");
                
        }

        @Override
        public void onBackPressed() {
                // TODO Auto-generated method stub
//                super.onBackPressed();
                //把后退事件交给子Activity处理
                group.getLocalActivityManager()
                        .getCurrentActivity().onBackPressed();
                Log.i("GroupTab","onBackPressed()");
        }

        @Override
        protected void onResume() {
                // TODO Auto-generated method stub
                super.onResume();
                //把界面切换放到onResume方法中是因为，从其他选项卡切换回来时，
                //调用搞得是onResume方法
                
                //要跳转的界面
                if(BaseAppData.getInstance().getLoginStatus()==0){
                	 Intent intent = new Intent(this, PasswordActivity.class);
                //把一个Activity转换成一个View
                Window w = group.getLocalActivityManager().startActivity("PasswordActivity",intent);
            View view = w.getDecorView();
            //把View添加大ActivityGroup中
            group.setContentView(view);
                }
                if(BaseAppData.getInstance().getLoginStatus()==1){
                	Intent intent = new Intent(this, ListActivity.class);
                    //把一个Activity转换成一个View
                	Bundle  bundle = new Bundle();
					bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
					intent.putExtras(bundle);
                    Window w = group.getLocalActivityManager().startActivity("ListActivity",intent);
                View view = w.getDecorView();
                //把View添加大ActivityGroup中
                group.setContentView(view);
                }
               
            Log.i("GroupTab","onResume()");
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==0){
                    ListActivity activity =(ListActivity)getLocalActivityManager().getCurrentActivity();
               //     activity.handleActivityResult(requestCode, resultCode, data);//把收到的消息发送给发起请求的Activity C
            }
    }
}