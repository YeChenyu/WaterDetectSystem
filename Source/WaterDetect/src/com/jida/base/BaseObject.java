package com.jida.base;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.Toast;

import com.jida.views.dialog.sweetalert.SweetAlertDialog;
import com.jida.waterdetect.R;

public class BaseObject{
 
	private boolean allowFullScreen = true;

	private boolean allowDestroy = true;

	private View view;
	    
	private Toast mToast;
	
	private SweetAlertDialog mProgress;
	
	private CountDownTimer mCountDownTimer;
	
	private  static final int HANDLER_BASE = 8091;
	private static final int HANDLER_TOAST = HANDLER_BASE + 1;
	
	private static final int HANDLER_DIALOG_BASIC = HANDLER_BASE + 2;
	
	private static final int HANDLER_DIALOG_SUCC = HANDLER_BASE + 3;
	
	private static final int HANDLER_DIALOG_ERR = HANDLER_BASE + 4;
	
	private static final int HANDLER_DIALOG_WARNING = HANDLER_BASE + 5;
	
	private static final int HANDLER_DIALOG_PROGRESS = HANDLER_BASE + 6;
	
	private static final int HANDLER_DIALOG_CONFIRM = HANDLER_BASE + 7;
	
	private static final int HANDLER_DIALOG_PROGRESS_DISMISS = HANDLER_BASE +  8;
	
	private static final int HANDLER_DIALOG_COMPLETE = HANDLER_BASE +  9;
	int mCount = 0;
	
	private CallBack.OnBasicListener mBasicListener;
	
	private CallBack.OnCompleteListener mCompleteListener;
	
    private Context mContext;
    
    @CallSuper
    public void initBaseObject(Context context){
    	
    	mContext = context;
    	mToast = Toast.makeText(mContext, null,
                Toast.LENGTH_SHORT);
    	
    	mProgress = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
    }

    MyHandler unUiHandler = new MyHandler(){
        public void handleMessage(Message msg) {
        	
        	switch(msg.what)
        	{
        	case HANDLER_TOAST:
        		mToast.setText((String)msg.obj );
        		mToast.show();
        		break;
        		
        	case HANDLER_DIALOG_BASIC:	
        		new SweetAlertDialog(mContext)
                .setTitleText((String)msg.obj )
                .setConfirmText("ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                    	sDialog.dismiss();
                    	unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
                    	
                    }
                })
                .show();
        		break;
        		
        	case HANDLER_DIALOG_SUCC:	
        		new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText((String)msg.obj )
                .setConfirmText("ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                    	sDialog.dismiss();
                    	unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
                    }
                })
                .show();
        		break;
        		
        	case HANDLER_DIALOG_ERR:	
        		new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText((String)msg.obj )
                .setConfirmText("ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                    	sDialog.dismiss();
                    	unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
                    }
                })
                .show();
        		break;
        		
        	case HANDLER_DIALOG_WARNING:
           		new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText((String)msg.obj )
                .setConfirmText("ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                    	sDialog.dismiss();
                    	unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
                    }
                })
                .show();
        		break;
        		
        	case HANDLER_DIALOG_PROGRESS:
        
        		
        		 mProgress.setTitleText((String)msg.obj );
        		 mProgress.show();
        		 mProgress.setCancelable(false);
        		 
        		 if(mCountDownTimer!=null)
        			 mCountDownTimer.cancel();
        		 mCountDownTimer = new CountDownTimer(msg.arg1, 800) {
	                 public void onTick(long millisUntilFinished) {
	                      // you can change the progress bar color by ProgressHelper every 800 millis
	                	  mCount++;
	                      switch (mCount){
	                          case 0:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.sweet_dialog_button));
	                              break;
	                          case 1:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_50));
	                              break;
	                          case 2:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.sweet_dialog_success_stroke_color));
	                              break;
	                          case 3:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_20));
	                              break;
	                          case 4:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_blue_grey_80));
	                              break;
	                          case 5:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.sweet_dialog_warning_stroke_color));
	                              break;
	                          case 6:
	                        	  mProgress.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.sweet_dialog_success_stroke_color));
	                              break;
	                      }
	                  }

	                  public void onFinish() {
	                	  mCount = -1;
	                      mProgress.dismiss();
	                  }
	              }.start();
        		break;
    		case HANDLER_DIALOG_CONFIRM:
    			
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText((String)msg.obj)
//                .setContentText("Won't be able to recover this file!")
                .setCancelText("Cancel")
                .setConfirmText("Confirm")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                    	sDialog.dismiss();
                    	mBasicListener.onCancel();
                    	
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                    	sDialog.dismiss();
                    	mBasicListener.onConfirm();
                    }
                })
                .show();
    			break;
    		case HANDLER_DIALOG_COMPLETE:
				mCompleteListener.onComplete();
				break;
				
    		default:
    			break;
        	}
        }       
    };	 
    
    
	public void displayToast(String str) {
		
		unUiHandler.obtainMessage(HANDLER_TOAST, -1, -1, str).sendToTarget();
		
	}
    
	public void displayDialogBasic(String content,CallBack.OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_BASIC, -1, -1, content).sendToTarget();
	}
	
	public void displayDialogSucc(String content,CallBack.OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_SUCC, -1, -1, content).sendToTarget();
	}
	
	public void displayDialogErr(String content,CallBack.OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_ERR, -1, -1, content).sendToTarget();
	}
	
	public void displayDialogWarning(String content,CallBack.OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_WARNING, -1, -1, content).sendToTarget();
	}
	
	public void displayDialogProgress(final String content,final long ms)
	{
		unUiHandler.obtainMessage(HANDLER_DIALOG_PROGRESS, (int)ms, -1, content).sendToTarget();

	}
	
	public void dismissDisplayDialogProgress()
	{

		if(mProgress!=null)
			mProgress.dismiss();
		
		
		if(mCountDownTimer!=null)
			mCountDownTimer.cancel();

	}
	
	public void displayDialogConfirm(String content,CallBack.OnBasicListener listener)
	{
		mBasicListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_CONFIRM, -1, -1, content).sendToTarget();
	}
	
	public class MyHandler extends Handler {
		 
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
       }
	        
	 }
}


