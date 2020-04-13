package com.topList.android.ui.host

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.topList.android.navigator.BottomSheetFragmentNavigator

/**
 * @author yyf
 * @since 03-27-2020
 * @desc 不优雅的 OverLay
 */
class HostNavFragment : NavHostFragment() {

    companion object {
        fun create(@NavigationRes graphResId: Int): HostNavFragment {
            return create(
                graphResId,
                null
            )
        }

        fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle?
        ): HostNavFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt("android-support-nav:fragment:graphId", graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle("android-support-nav:fragment:startDestinationArgs", startDestinationArgs)
            }

            val result = HostNavFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(BottomSheetFragmentNavigator(requireContext(), childFragmentManager))
    }

}