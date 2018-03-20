package com.jida.activity;

import android.content.Context;
import android.widget.ListView;

import com.jida.adapter.DataReportAdapter;
import com.jida.adapter.DataReportCountAdapter;
import com.jida.base.BaseActivity;
import com.jida.module.ReportData;
import com.jida.views.pinnedheaderlistview.PinnedHeaderListView;
import com.jida.waterdetect.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by chenyuye on 17/12/1.
 */

public class DataReportActivity extends BaseActivity{

    private static final String TAG = DataReportActivity.class.getSimpleName();
    private Context mContext = DataReportActivity.this;

    @Bind(R.id.data_report_table)
    PinnedHeaderListView mLvData;
    @Bind(R.id.data_report_count_table)
    ListView mLvCount;

    private DataReportAdapter mAdapter = null;
    private DataReportCountAdapter mCountAdapter = null;


    @Override
    protected void addContentView() {
        setContentView(R.layout.data_report_activity);
    }

    @Override
    protected void initView() {
        List<ReportData> mList = new ArrayList<>();
        for (int i=0 ; i<30 ; i++){
            mList.add(new ReportData(ReportData.TYPE_DAY, ReportData.TYPE_COUNT_NORMAL,
                    new Date(System.currentTimeMillis()), 1, 1, 1, 1, 1, 1));
        }
        mAdapter = new DataReportAdapter(mContext);
        mAdapter.setListData(mList);
        mLvData.setAdapter(mAdapter);

        List<ReportData> mLstCount = new ArrayList<>();
        mLstCount.add(new ReportData(ReportData.TYPE_COUNT_MIN, 1, 1, 1, 1, 1, 1));
        mLstCount.add(new ReportData(ReportData.TYPE_COUNT_AVE, 1, 1, 1, 1, 1, 1));
        mLstCount.add(new ReportData(ReportData.TYPE_COUNT_MAX, 1, 1, 1, 1, 1, 1));
        mCountAdapter = new DataReportCountAdapter(mContext);
        mCountAdapter.setListData(mLstCount);
        mLvCount.setAdapter(mCountAdapter);
    }

    @Override
    protected void onClickEsc() {

    }
}
