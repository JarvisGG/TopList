package com.topList.android.ui.account.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.topList.android.BuildConfig
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import com.topList.android.api.model.transform2People
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.common.CommonViewModel
import com.topList.android.utils.CommonToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.btnClose
import kotlinx.android.synthetic.main.fragment_login.etEmail
import kotlinx.android.synthetic.main.fragment_login.etPwd
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * @author yyf
 * @since 04-10-2020
 */
class LoginFragment : BaseFragment() {

    private val vm: LoginViewModel by viewModels({ this }, {
        LoginViewModelFactory(
            LoginUseCase(Apis.account)
        )
    })

    private val cvm: CommonViewModel by viewModels(ownerProducer = { requireActivity() })

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            loginStateUpdate()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

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
        if (BuildConfig.DEBUG) {
            injectDemo()
        }
    }

    private fun injectDemo() {
        etEmail.editText.setText("821388334@qq.com")
        etPwd.editText.setText("Yangyufei4130+qq")
        loginStateUpdate()
    }

    private fun initView() {
        btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
        tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
        }
        btnLogin.setOnClickListener {
            login()
        }
        etEmail.textWatcher = textWatcher
        etPwd.textWatcher = textWatcher
    }

    private fun loginStateUpdate() {
        btnLogin.isEnabled = etEmail.text.isNotEmpty()
                && etPwd.text.isNotEmpty()
    }

    private fun login() {
        vm.login(
            etEmail.text,
            etPwd.text
        ).observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetResult.Success<*> -> {
                    it.data as State<LoginResult>
                    cvm.accountState.postValue(it.data.data.transform2People())
                    Toast.makeText(requireActivity(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                is NetResult.Error -> {
                    val errorMsg = it.exception.message ?: "error"
                    CommonToast.Builder(CommonToast.PresetIcon.ALERT, errorMsg).build().show(requireActivity())
                }
            }
        })
    }

}