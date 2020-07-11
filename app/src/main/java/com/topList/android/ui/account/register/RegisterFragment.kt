package com.topList.android.ui.account.register

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.topList.android.BuildConfig
import com.topList.android.R
import com.topList.android.api.Apis
import com.topList.android.api.NetResult
import com.topList.android.api.model.LoginResult
import com.topList.android.api.model.State
import com.topList.android.api.model.transform2People
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.account.register.domain.RegisterUseCase
import com.topList.android.ui.account.register.domain.VerifyUseCase
import com.topList.android.ui.account.verify.VerifyCode
import com.topList.android.ui.common.CommonViewModel
import com.topList.android.utils.CommonToast
import com.topList.android.utils.FormatChecker
import com.topList.android.utils.RxBus
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.widget_title_edit_text.view.*

class RegisterFragment : BaseFragment() {

    private val vm: RegisterViewModel by viewModels({this}, {
        RegisterViewModelFactory(
            VerifyUseCase(Apis.account),
            RegisterUseCase(Apis.account)
        )
    })

    private val cvm: CommonViewModel by viewModels(ownerProducer = { requireActivity() })

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            registerStateUpdate()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private var validPassword = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        if (BuildConfig.DEBUG) {
            injectDemo()
        }
    }

    private fun initView() {
        etPwd.inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        etEmail.inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        etEmail.textWatcher = textWatcher
        etPwd.textWatcher = textWatcher
        etNick.textWatcher = textWatcher
        val originalListener = etPwd.etContent.onFocusChangeListener
        etPwd.etContent.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            originalListener.onFocusChange(v, hasFocus)
            if (!hasFocus) {
                if (etPwd.text.isNotEmpty()) {
                    validatePassword()
                }



            }
        }
        btnRegister.setOnClickListener {
            vm.sendCode(etEmail.text).observe(viewLifecycleOwner, Observer {  })
            findNavController().navigate(RegisterFragmentDirections.actionRegisterToEmailCheck())
        }

        registerStateUpdate()
    }

    private fun initData() {
        RxBus.instance.toObservable(VerifyCode::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(this))
            .subscribe {
                register(it.code)
            }
    }

    private fun injectDemo() {
        etEmail.editText.setText("821388334@qq.com")
        etPwd.editText.setText("Yangyufei4130+qq")
        etNick.editText.setText("Jarvis")
        validatePassword()
        registerStateUpdate()
    }

    private fun register(code: String) {
        vm.register(
            etNick.text,
            etEmail.text,
            "2141",
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


    private fun registerStateUpdate() {
        btnRegister.isEnabled = etEmail.text.isNotEmpty()
                && etPwd.text.isNotEmpty() && validPassword
                && etNick.text.isNotEmpty()

    }

    private fun validatePassword() {
        validPassword = FormatChecker.checkPasswordFormat(etPwd.text)
        if (validPassword) {
            etPwd.warning(false)
        } else {
            etPwd.warning(true)
            activity?.let {
                val errorMsg = getString(R.string.register_page_password_requirement)
                CommonToast.Builder(CommonToast.PresetIcon.ALERT, errorMsg).build().show(it)
            }
        }
    }

}


