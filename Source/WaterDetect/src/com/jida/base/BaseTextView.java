package com.jida.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class BaseTextView extends TextView {

	public BaseTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "block_round.ttf");
		setTypeface(tf);
	}

	public BaseTextView(Context context, AttributeSet attrs){
		super(context, attrs);
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "block_round.ttf");
		setTypeface(tf);
	}
}
