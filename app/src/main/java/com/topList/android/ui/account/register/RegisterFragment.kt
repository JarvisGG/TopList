package com.topList.android.ui.account.register

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import com.topList.android.ui.widget.TitleEditText
import com.topList.android.utils.CommonToast
import com.topList.android.utils.FormatChecker
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.widget_title_edit_text.view.*

class RegisterFragment : BaseFragment() {

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
                if (v is TitleEditText && v.title.isNotEmpty()) {
                    validatePassword()
                }
            }
        }
        btnRegister.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterToEmailCheck())
        }
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
