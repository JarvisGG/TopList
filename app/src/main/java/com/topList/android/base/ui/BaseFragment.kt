package com.topList.android.base.ui

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.topList.android.R

/**
 * @author yyf
 * @since 03-23-2020
 */
open class BaseFragment : Fragment() {
    fun findMainNavController() = requireActivity().findNavController(R.id.frag_nav_host)
}