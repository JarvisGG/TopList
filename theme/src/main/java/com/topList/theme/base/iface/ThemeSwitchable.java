package com.topList.theme.base.iface;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author 潘志会 @ Zhihu Inc.
 * @since 2018/07/04
 */
public interface ThemeSwitchable {
    void switchTheme(@AppCompatDelegate.NightMode int mode);
    void switchSilently(@AppCompatDelegate.NightMode int mode);
}
