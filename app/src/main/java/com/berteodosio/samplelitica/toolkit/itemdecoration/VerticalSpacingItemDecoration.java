package com.berteodosio.samplelitica.toolkit.itemdecoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class VerticalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private float mSpacing;
    private Resources mResources;

    public VerticalSpacingItemDecoration(final float spacing, @NonNull final Resources resources) {
        mSpacing = spacing;
        mResources = resources;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        outRect.bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSpacing, mResources.getDisplayMetrics());
    }
}
