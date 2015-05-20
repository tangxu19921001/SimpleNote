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
         * һ����̬��ActivityGroup���������ڹ���Group�е�Activity
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
                //�Ѻ����¼�������Activity����
                group.getLocalActivityManager()
                        .getCurrentActivity().onBackPressed();
                Log.i("GroupTab","onBackPressed()");
        }

        @Override
        protected void onResume() {
                // TODO Auto-generated method stub
                super.onResume();
                //�ѽ����л��ŵ�onResume����������Ϊ��������ѡ��л�����ʱ��
                //���ø����onResume����
                
                //Ҫ��ת�Ľ���
                if(BaseAppData.getInstance().getLoginStatus()==0){
                	 Intent intent = new Intent(this, PasswordActivity.class);
                //��һ��Activityת����һ��View
                Window w = group.getLocalActivityManager().startActivity("PasswordActivity",intent);
            View view = w.getDecorView();
            //��View��Ӵ�ActivityGroup��
            group.setContentView(view);
                }
                if(BaseAppData.getInstance().getLoginStatus()==1){
                	Intent intent = new Intent(this, ListActivity.class);
                    //��һ��Activityת����һ��View
                	Bundle  bundle = new Bundle();
					bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
					intent.putExtras(bundle);
                    Window w = group.getLocalActivityManager().startActivity("ListActivity",intent);
                View view = w.getDecorView();
                //��View��Ӵ�ActivityGroup��
                group.setContentView(view);
                }
               
            Log.i("GroupTab","onResume()");
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==0){
                    ListActivity activity =(ListActivity)getLocalActivityManager().getCurrentActivity();
               //     activity.handleActivityResult(requestCode, resultCode, data);//���յ�����Ϣ���͸����������Activity C
            }
    }
}