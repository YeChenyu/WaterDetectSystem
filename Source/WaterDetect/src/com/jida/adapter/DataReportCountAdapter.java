package com.jida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jida.base.MyBaseAdapter;
import com.jida.module.ReportData;
import com.jida.waterdetect.R;


public class DataReportCountAdapter extends MyBaseAdapter<ReportData> {


    public DataReportCountAdapter(Context context){
        super(context);
    }

    @Override
    protected View onBindViewHolder(int position, ViewGroup parent) {
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
    public void onBindViewData(int position, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();
        ReportData data = getListData().get(position);
        switch (data.getReportType()){
            case ReportData.TYPE_COUNT_MIN:
                vh.tv1.setText("最小值");
                break;
            case ReportData.TYPE_COUNT_AVE:
                vh.tv1.setText("平均值");
                break;
            case ReportData.TYPE_COUNT_MAX:
                vh.tv1.setText("最大值");
                break;
            default:
                break;
        }
        vh.tv2.setText(""+data.getTemper());
        vh.tv3.setText(""+data.getPh());
        vh.tv4.setText(""+data.getNh4n2());
        vh.tv5.setText(""+data.getP());
        vh.tv6.setText(""+data.getN2());
        vh.tv7.setText(""+data.getCodmn());
    }

    class ViewHolder{
        public TextView tv1, tv2, tv3, tv4,
                tv5, tv6, tv7;

        public ViewHolder(){
        }
    }
}
