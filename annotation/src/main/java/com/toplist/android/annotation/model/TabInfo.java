package com.toplist.android.annotation.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * @author yyf
 * @since 03-26-2020
 */
public class TabInfo {
    public int position = -1;
    @StringRes
    public int titleRes = -1;
    @DrawableRes
    public int iconRes = -1;

    public TabInfo(@StringRes int titleRes, @DrawableRes int iconRes, int position) {
        this.titleRes = titleRes;
        this.iconRes = iconRes;
        this.position = position;
    }

}
