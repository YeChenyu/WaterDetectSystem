package com.jida.views.pinnedheaderlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class SectionedBaseAdapter<T> extends BaseAdapter
        implements PinnedHeaderListView.PinnedSectionedHeaderAdapter {

    private static int HEADER_VIEW_TYPE = 0;
    private static int ITEM_VIEW_TYPE = 0;

    protected Context mContext;
    protected List<T> mList;

    public SectionedBaseAdapter(Context context) {
        super();
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public final int getCount() {
        return mList.size();
    }

    @Override
    public final Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    public abstract View onBindItemView(int position, ViewGroup parent);

    public abstract View getSectionHeaderView(int position, ViewGroup parent);

    public abstract View onBindViewData(int position, View convertView);


    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            if (isSectionHeader(position))
                convertView = getSectionHeaderView(getSectionForPosition(position), parent);
            else
                convertView = onBindItemView(getSectionForPosition(position), parent);
        }
        if(!isSectionHeader(position))
            onBindViewData(position, convertView);
        return (LinearLayout)convertView;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isSectionHeader(position))
            return getItemViewTypeCount() + getSectionHeaderViewType(getSectionForPosition(position));
        else
            return getItemViewType(getSectionForPosition(position),
                    position);
    }

    @Override
    public final int getViewTypeCount() {
        return getItemViewTypeCount() + getSectionHeaderViewTypeCount();
    }

    public final int getPositionInSectionForPosition(int position){
        return position;
    }

    /**
     * 判断是不是表头
     * @param position
     * @return
     */
    public final boolean isSectionHeader(int position) {
        if (position == 0)
            return true;
        else
            return false;
    }

    public int getItemViewType(int section, int position) {
        return ITEM_VIEW_TYPE;
    }

    public int getItemViewTypeCount() {
        return 1;
    }

    public int getSectionHeaderViewType(int section) {
        return HEADER_VIEW_TYPE;
    }

    public int getSectionHeaderViewTypeCount() {
        return 1;
    }

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
