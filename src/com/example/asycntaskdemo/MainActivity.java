package com.example.asycntaskdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btn;
	Button btn2;
	ProgressDialog dialog;
	Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initProgress();
		mHandler=new Handler(){
			
			public void handleMessage(Message msg){
				switch (msg.what) {
				case 1:
					dialog.setProgress((int)msg.obj);
					break;

				default:
					break;
				}
			}
		};
	}
	
	private void initView(){
		btn=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new MyTestTask().execute();
			}
		});
		

		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for(int i=0;i<1000001;i++){
							if(i%10000==0){
								try {
									Thread.sleep(1000);
									Message msgMessage=new Message();
									msgMessage.obj=i;
									msgMessage.what=1;
									mHandler.sendMessage(msgMessage);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}																								
							}
							
						}
					}
				}).start();;
			}
		});
	}
	private void initProgress(){
		dialog = new ProgressDialog(this);  
	    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条  
	    dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
	    dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
	    dialog.setIcon(R.drawable.ic_launcher);// 设置提示的title的图标，默认是没有的  
	    dialog.setTitle("提示");  
	    dialog.setMax(1000000);  
	    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",  
	            new DialogInterface.OnClickListener() {  
	  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    // TODO Auto-generated method stub  
	  
	                }  
	            });  
	    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",  
	            new DialogInterface.OnClickListener() {  
	  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    // TODO Auto-generated method stub  
	  
	                }  
	            });  
	   //dialog.show();  
	}
	
	class MyTestTask extends AsyncTask<Void, Integer, Boolean>{

		protected void onPreExecute() {
			dialog.show();
			}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			for(int i=0;i<1000001;i++){
				if(i%10000==0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					publishProgress(i);
					
					
				}
				
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			Log.e("Task", ""+values[0]);
			dialog.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Boolean result){
			if(result==true){
				Log.e("Task", "finish");
				dialog.dismiss();
			}
			
			
		}
	}
}
