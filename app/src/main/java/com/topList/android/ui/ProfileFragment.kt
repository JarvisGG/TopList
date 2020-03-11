package com.topList.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.topList.android.R
import com.topList.theme.ThemeManager
import com.topList.theme.ThemeManager.DARK
import com.topList.theme.ThemeManager.LIGHT
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * @author yyf
 * @since 03-09-2020
 */
class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch1.isChecked = ThemeManager.isDark()
        switch1.setOnClickListener {
            if (switch1.isChecked) {
                ThemeManager.switchThemeTo(DARK)
            } else {
                ThemeManager.switchThemeTo(LIGHT)
            }
        }
    }

}