package com.topList.android.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.topList.android.R
import com.topList.android.ui.host.IMainDelegate
import com.topList.android.ui.host.MainDelegate

/**
 * @author yyf
 * @since 08-13-2019
 */
class HostFragment : Fragment() {

    private val delagate: IMainDelegate by lazy {
        MainDelegate(fragment = this@HostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delagate.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delagate.onViewCreated(view, savedInstanceState)
    }

//    override fun onCreateNavController(navController: NavController) {
//        super.onCreateNavController(navController)
//        navController.navigatorProvider += MainPageFragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)
//    }

//    override fun createFragmentNavigator() = FragmentNavigator(requireContext(), childFragmentManager, R.id.hostPlace)


}