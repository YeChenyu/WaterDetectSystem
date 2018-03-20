package com.jida.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jida.waterdetect.R;

import butterknife.OnClick;

public class BaseTitleBar extends RelativeLayout{

	private View mView;
	private TextView mTvTitle;
	private ImageButton mBtnBack;
	
	public BaseTitleBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mView = LayoutInflater.from(context).inflate(R.layout.common_title_bar, null);
		mTvTitle = (TextView) mView.findViewById(R.id.title_text_textview);
		mBtnBack = (ImageButton) mView.findViewById(R.id.title_back_imagebutton);
		addView(mView);
	}

	public BaseTitleBar(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		mView = LayoutInflater.from(context).inflate(R.layout.common_title_bar, null);
		mTvTitle = (TextView) mView.findViewById(R.id.title_text_textview);
		mBtnBack = (ImageButton) mView.findViewById(R.id.title_back_imagebutton);
		addView(mView);
	}
	
	public void setTitle(CharSequence title){
		mTvTitle.setText(title);
	}
	
	
	@OnClick(R.id.title_back_imagebutton)
	public void onClickBack(){
		
	}
}
