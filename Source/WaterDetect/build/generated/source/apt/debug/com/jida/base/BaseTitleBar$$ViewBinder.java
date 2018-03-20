// Generated code from Butter Knife. Do not modify!
package com.jida.base;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BaseTitleBar$$ViewBinder<T extends com.jida.base.BaseTitleBar> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624018, "method 'onClickBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickBack();
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
