package com.topList.android.ui.account.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @author yyf
 * @since 04-10-2020
 */
class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}