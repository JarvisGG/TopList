package com.topList.android.ui.account.verify

import android.os.Bundle
import android.view.View
import com.poovam.pinedittextfield.PinField
import com.topList.android.R
import com.topList.android.bottomsheet.ui.BottomSheetFragment
import com.topList.android.utils.RxBus
import kotlinx.android.synthetic.main.fragment_email_check.*

class CodeCheckFragment : BottomSheetFragment() {

    override fun getContainerId() = R.layout.fragment_email_check

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pfCode.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                RxBus.instance.post(VerifyCode(enteredText))
                this@CodeCheckFragment.hidden()
                return true
            }
        }

    }

}
