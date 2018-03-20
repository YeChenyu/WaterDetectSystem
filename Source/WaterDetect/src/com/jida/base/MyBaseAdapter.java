package com.jida.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter{

	protected Context mContext;
	private List<T> mList;

	public MyBaseAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mList = new ArrayList<T>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = onBindViewHolder(position, parent);

		onBindViewData(position, convertView);
		return convertView;
	}


	protected abstract View onBindViewHolder(int position, ViewGroup parent);
	protected abstract void onBindViewData(int position, View convertView);
	/**
	 * 重新设置数据：先清楚旧数据，再添加新数据,最后刷新页面
	 * @param list 新数据
	 */
	public void setListData(@NonNull List<T> list){
		if(list == null)
			return;
		if(mList != null){
			mList.clear();
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 在已有数据后面添加新数据：在尾部添加新数据,再刷新页面
	 * @param list 新数据
	 */
	public void addListData(@NonNull List<T> list){
		if(list == null)
			return;
		if(mList != null){
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void addData(T data){
		if(mList != null){
			mList.add(data);
			notifyDataSetChanged();
		}
	}

	public void removeData(int position){
		if(mList == null)
			return;
		if(position>0 && position<mList.size()){
			mList.remove(position);
		}

	}
	/**
	 * 清除数据
	 */
	public void clearData(){
		if(mList != null)
			mList.clear();
		notifyDataSetChanged();
	}

	public List<T> getListData() {
		return mList;
	}
}
