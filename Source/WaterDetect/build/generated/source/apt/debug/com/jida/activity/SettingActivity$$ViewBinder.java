// Generated code from Butter Knife. Do not modify!
package com.jida.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingActivity$$ViewBinder<T extends com.jida.activity.SettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624045, "field 'v1'");
    target.v1 = view;
  }

  @Override public void unbind(T target) {
    target.v1 = null;
  }
}
