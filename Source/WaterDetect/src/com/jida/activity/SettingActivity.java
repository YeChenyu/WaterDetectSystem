package com.jida.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jida.base.BaseActivity;
import com.jida.base.CallBack;
import com.jida.waterdetect.R;

import butterknife.Bind;


public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = SettingActivity.class.getSimpleName();
    private Context mContext = SettingActivity.this;

    @Bind(R.id.setting_1)
    View v1;

    @Override
    protected void onClickEsc() {
        displayDialogConfirm("退出系统管理并回到实时监测模式", new CallBack.OnBasicListener() {
            @Override
            public void onConfirm() {
                finish();
            }
            @Override
            public void onCancel() {}
        });
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.setting_activity);
    }

    @Override
    protected void initView() {
        ((TextView)v1.findViewById(R.id.text)).setText("setting1");
        v1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: v.id="+ v.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
