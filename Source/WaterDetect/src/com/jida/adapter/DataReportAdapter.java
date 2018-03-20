package com.jida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jida.module.ReportData;
import com.jida.views.pinnedheaderlistview.SectionedBaseAdapter;
import com.jida.waterdetect.R;


public class DataReportAdapter extends SectionedBaseAdapter<ReportData> {


    public DataReportAdapter(Context context){
        super(context);
    }

    @Override
    public View onBindItemView(int position, ViewGroup parent) {
        View layout = null;
        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (View) inflator.inflate(R.layout.data_report_item, null);

        ViewHolder vh = new ViewHolder();
        vh.tv1 = (TextView) layout.findViewById(R.id.data_report_time);
        vh.tv2 = (TextView) layout.findViewById(R.id.data_report_temper);
        vh.tv3 = (TextView) layout.findViewById(R.id.data_report_ph);
        vh.tv4 = (TextView) layout.findViewById(R.id.data_report_nh4n2);
        vh.tv5 = (TextView) layout.findViewById(R.id.data_report_p);
        vh.tv6 = (TextView) layout.findViewById(R.id.data_report_n2);
        vh.tv7 = (TextView) layout.findViewById(R.id.data_report_codmn);
        layout.setTag(vh);
        return layout;
    }

    @Override
    public View onBindViewData(int position, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();
        ReportData data = getListData().get(position);
        vh.tv1.setText(data.getData());
        vh.tv2.setText(""+data.getTemper());
        vh.tv3.setText(""+data.getPh());
        vh.tv4.setText(""+data.getNh4n2());
        vh.tv5.setText(""+data.getP());
        vh.tv6.setText(""+data.getN2());
        vh.tv7.setText(""+data.getCodmn());
        return convertView;
    }
    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public View getSectionHeaderView(int position, ViewGroup parent) {
        LinearLayout layout = null;
        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) inflator.inflate(R.layout.data_report_item, null);
        return layout;
    }

    class ViewHolder{
        public TextView tv1, tv2, tv3, tv4,
                tv5, tv6, tv7;

        public ViewHolder(){
        }
    }
}
