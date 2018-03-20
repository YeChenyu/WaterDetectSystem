package com.jida.base;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jida.base.CallBack.OnBasicListener;
import com.jida.base.CallBack.OnCompleteListener;
import com.jida.views.dialog.sweetalert.SweetAlertDialog;
import com.jida.waterdetect.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

	private static final String TAG = BaseActivity.class.getSimpleName();
	private Context mContext = BaseActivity.this;

	private boolean allowFullScreen = true;
	private boolean allowDestroy = true;

	private View view;
	private View actionbarLayout;


	private SweetAlertDialog mDialog;

	private CountDownTimer mCountDownTimer;

	private static final int HANDLER_TOAST = 1;

	private static final int HANDLER_DIALOG_BASIC = 2;

	private static final int HANDLER_DIALOG_SUCC = 3;

	private static final int HANDLER_DIALOG_ERR = 4;

	private static final int HANDLER_DIALOG_WARNING = 5;

	private static final int HANDLER_DIALOG_PROGRESS = 6;

	private static final int HANDLER_DIALOG_CONFIRM = 7;

	private static final int HANDLER_DIALOG_PROGRESS_DISMISS = 8;

	private static final int HANDLER_DIALOG_COMPLETE = 9;

	private static final int HANDLER_DIALOG_CUSTOM = 10;

	private OnBasicListener mBasicListener;
	private OnCompleteListener mCompleteListener;

	private class DialogData{
		public String mTitle;
		public String mContent;
		public String mTextConfirm;
		public String mTextCancel;
		public View mCusView;
	}
	private int mCount = 0;
	private boolean mIsCusActionBar = false;

	protected abstract void onClickEsc();
	protected abstract void addContentView();
	protected abstract void initView();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		addContentView();

		allowFullScreen = true;
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		//绑定activity
		ButterKnife.bind(this);

		initView();
	}

	private void initActionBar() {
		// TODO Auto-generated method stub
		//得到actionBar，注意我的是V7包，使用getSupportActionBar()
		ActionBar actionBar = getSupportActionBar();
		//在使用v7包的时候显示icon和标题需指定一下属性。
		actionBar.setDisplayShowHomeEnabled(true);
		// 应用图标
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setLogo(R.drawable.login_witsi);
		// 返回箭头（默认不显示）
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
		// 左侧图标点击事件使能
		actionBar.setHomeButtonEnabled(true);
		// 显示标题
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle("");

		//显示自定义的actionBar
		actionBar.setDisplayShowCustomEnabled(false);
	}

	/**
	 * 设置自定义标题栏
	 * @param enable
	 */
	protected void setCustomViewEnable(boolean enable){
		if(enable){
			mIsCusActionBar = true;
			//得到actionBar，注意我的是V7包，使用getSupportActionBar()
			ActionBar actionBar = getSupportActionBar();
			actionbarLayout = LayoutInflater.from(this).inflate(R.layout.common_title_bar, null);
			actionbarLayout.findViewById(R.id.title_back_imagebutton).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onClickEsc();
				}
			});
			ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
					ActionBar.LayoutParams.MATCH_PARENT,
					ActionBar.LayoutParams.WRAP_CONTENT
			);
			actionBar.setCustomView(actionbarLayout, lp);
			//显示自定义的actionBar
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setDisplayShowCustomEnabled(mIsCusActionBar);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			Toolbar parent = (Toolbar) actionbarLayout.getParent();
			parent.setContentInsetsAbsolute(0, 0);//设置两边填充部分为0
		}else{
			mIsCusActionBar = false;
		}
	}


	public void setTitle(CharSequence title){
		if(title != null){
			if(mIsCusActionBar){
				if(actionbarLayout != null)
					((TextView)actionbarLayout.findViewById(R.id.title_text_textview)).setText(title);
			}else{
				// 显示标题
				ActionBar actionBar = getSupportActionBar();
				actionBar.setDisplayShowTitleEnabled(true);
				actionBar.setTitle(title);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case android.R.id.home:
				onClickEsc();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);

		dismissDisplayDialogProgress();

		mDialog = null;
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	public void setAllowFullScreen(boolean allowFullScreen) {
		this.allowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private MyHandler unUiHandler = new MyHandler(){
		public void handleMessage(Message msg) {

			switch(msg.what)
			{
				case HANDLER_TOAST:
					Toast.makeText(BaseActivity.this, (String)msg.obj,
							Toast.LENGTH_SHORT).show();
					break;

				case HANDLER_DIALOG_BASIC:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this);
					mDialog.setTitleText("提示!");
					mDialog.setContentText((String)msg.obj);
					mDialog.setConfirmText("确认");
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();

						}
					});
					mDialog.show();
					break;

				case HANDLER_DIALOG_SUCC:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.SUCCESS_TYPE);
					mDialog.setTitle("操作成功!");
					mDialog.setContentText((String)msg.obj );
					mDialog.setConfirmText("确认");
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
						}
					});
					mDialog.show();
					break;

				case HANDLER_DIALOG_ERR:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.ERROR_TYPE);
					mDialog.setTitleText("错误提示!");
					mDialog.setContentText((String)msg.obj );
					mDialog.setConfirmText("确认");
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
						}
					});
					mDialog.show();
					break;

				case HANDLER_DIALOG_WARNING:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.WARNING_TYPE);
					mDialog.setTitleText("警示提醒，请注意！");
					mDialog.setContentText((String)msg.obj);
					mDialog.setConfirmText("ok");
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							unUiHandler.obtainMessage(HANDLER_DIALOG_COMPLETE, -1, -1, null).sendToTarget();
						}
					});
					mDialog.show();
					break;

				case HANDLER_DIALOG_PROGRESS:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.PROGRESS_TYPE);
					mDialog.setTitleText((String)msg.obj);
					mDialog.show();
					mDialog.setCancelable(false);

					if(mCountDownTimer!=null)
						mCountDownTimer.cancel();
					mCountDownTimer = new CountDownTimer(msg.arg1, 800) {
						public void onTick(long millisUntilFinished) {
							// you can change the progress bar color by ProgressHelper every 800 millis
							mCount++;
							switch (mCount){
								case 0:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.sweet_dialog_button));
									break;
								case 1:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
									break;
								case 2:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.sweet_dialog_success_stroke_color));
									break;
								case 3:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
									break;
								case 4:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
									break;
								case 5:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.sweet_dialog_warning_stroke_color));
									break;
								case 6:
									mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.sweet_dialog_success_stroke_color));
									break;
							}
						}

						public void onFinish() {
							mCount = -1;
							mDialog.dismiss();
						}
					}.start();
					break;
				case HANDLER_DIALOG_CONFIRM:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.WARNING_TYPE);
					mDialog.setTitleText("请确认！");
                  mDialog.setContentText((String)msg.obj);
					mDialog.setCancelText("Cancel");
					mDialog.setConfirmText("Confirm");
					mDialog.showCancelButton(true);
					mDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							// reuse previous dialog instance, keep widget user state, reset them if you need
							sDialog.dismiss();
							if(mBasicListener != null)
								mBasicListener.onCancel();

						}
					});
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							if(mBasicListener != null) mBasicListener.onConfirm();
						}
					});
					mDialog.show();
					break;
				case HANDLER_DIALOG_COMPLETE:
					if(mCompleteListener != null)
						mCompleteListener.onComplete();
					break;
				case HANDLER_DIALOG_CUSTOM:
					if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();

					mDialog = new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.NORMAL_TYPE);
					DialogData data = (DialogData)msg.obj;
					if(data.mTitle != null) mDialog.setTitleText(data.mTitle);
					if(data.mContent != null) mDialog.setContentText(data.mContent);
					if(data.mTextCancel != null) mDialog.setCancelText(data.mTextCancel);
					else mDialog.setCancelText("Cancel");
					if(data.mTextConfirm != null) mDialog.setConfirmText(data.mTextConfirm);
					else mDialog.setConfirmText("Confirm");
					if(data.mCusView != null) mDialog.setViews(data.mCusView);
					mDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							// reuse previous dialog instance, keep widget user state, reset them if you need
							sDialog.dismiss();
							if(mBasicListener != null) mBasicListener.onCancel();

						}
					});
					mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							if(mBasicListener != null) mBasicListener.onConfirm();
						}
					});
					mDialog.show();
					break;
				default:
					break;
			}
		}
	};


	public void displayToast(String str) {

		unUiHandler.obtainMessage(HANDLER_TOAST, -1, -1, str).sendToTarget();
	}

	public void displayDialogBasic(String content,OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_BASIC, -1, -1, content).sendToTarget();
	}

	public void displayDialogSucc(String content,OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_SUCC, -1, -1, content).sendToTarget();
	}

	public void displayDialogErr(String content,OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_ERR, -1, -1, content).sendToTarget();
	}

	public void displayDialogWarning(String content,OnCompleteListener listener)
	{
		mCompleteListener = listener;
		unUiHandler.obtainMessage(HANDLER_DIALOG_WARNING, -1, -1, content).sendToTarget();
	}

	public void displayDialogProgress(final String content,final long ms)
	{
		unUiHandler.obtainMessage(HANDLER_DIALOG_PROGRESS, (int)ms, -1, content).sendToTarget();
	}

	public void displayDialogCustom(String title, String content, String cancel, String confirm, OnBasicListener listener)
	{
		mBasicListener = listener;
		DialogData data = new DialogData();
		data.mTitle = title;
		data.mContent  = content;
		data.mTextCancel = cancel;
		data.mTextConfirm = confirm;
		unUiHandler.obtainMessage(HANDLER_DIALOG_CUSTOM, -1, -1, data).sendToTarget();

	}

	public void displayDialogCustom(String title, View view, String content, String cancel, String confirm, OnBasicListener listener)
	{
		mBasicListener = listener;
		DialogData data = new DialogData();
		data.mTitle = title;
		data.mContent  = content;
		data.mTextCancel = cancel;
		data.mTextConfirm = confirm;
		data.mCusView = view;
		unUiHandler.obtainMessage(HANDLER_DIALOG_CUSTOM, -1, -1, data).sendToTarget();

	}
	public void displayDialogCustom(String title, View view, String content, OnBasicListener listener)
	{
		mBasicListener = listener;
		DialogData data = new DialogData();
		data.mTitle = title;
		data.mContent  = content;
		data.mCusView = view;
		unUiHandler.obtainMessage(HANDLER_DIALOG_CUSTOM, -1, -1, data).sendToTarget();

	}

	public void dismissDisplayDialogProgress()
	{

		if(mDialog!=null)
			mDialog.dismiss();


		if(mCountDownTimer!=null)
			mCountDownTimer.cancel();

	}

	public void displayDialogConfirm(String content,OnBasicListener listener)
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


