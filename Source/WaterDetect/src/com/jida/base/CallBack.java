package com.jida.base;


public class CallBack {

	
	public interface OnCompleteListener
	{
		public void onComplete();
	}
	
	public interface OnBasicListener
	{
		public void onConfirm();
		public void onCancel();
	}
}
