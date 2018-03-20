// Generated code from Butter Knife. Do not modify!
package com.jida.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DataReportActivity$$ViewBinder<T extends com.jida.activity.DataReportActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624020, "field 'mLvData'");
    target.mLvData = finder.castView(view, 2131624020, "field 'mLvData'");
    view = finder.findRequiredView(source, 2131624021, "field 'mLvCount'");
    target.mLvCount = finder.castView(view, 2131624021, "field 'mLvCount'");
  }

  @Override public void unbind(T target) {
    target.mLvData = null;
    target.mLvCount = null;
  }
}
